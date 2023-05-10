package com.charsmart.data.hc.domain;


import com.charsmart.data.api.layer.StorageService;
import com.charsmart.data.common.Result;
import com.charsmart.data.hc.cache.KeyUtils;
import com.charsmart.data.hc.lock.DistributedLock;
import com.charsmart.data.hc.lock.LockFactory;
import com.charsmart.data.hc.repository.BdStorage;
import com.charsmart.data.hc.repository.BdStorageService;
import com.charsmart.data.hc.repository.mapper.SQLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/6 5:30 PM
 */
@Service
public class StorageServiceImpl implements StorageService {
    private final BdStorageService bdStorageService;
    private final SQLMapper sqlMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final long DEFAULT_TTL = 60 * 60;
    private final LockFactory lockFactory;
    private final String LUA = "if redis.call('exists', KEYS[1]) == 1 then\n" +
            "local stock = tonumber(redis.call('get', KEYS[1]));\n" +
            "local num = tonumber(ARGV[1]);\n" +
            "if (stock == -1) then\n" +
            "return -1;\n" +
            "end;\n" +
            "if (stock >= num) then\n" +
            "return redis.call('incrby', KEYS[1], 0 - num);\n" +
            "end;\n" +
            "return -2;\n" +
            "end;\n" +
            "return -3;";

    public StorageServiceImpl(BdStorageService bdStorageService, SQLMapper sqlMapper, LockFactory lockFactory) {
        this.bdStorageService = bdStorageService;
        this.sqlMapper = sqlMapper;
        this.lockFactory = lockFactory;
    }

    private long getStorage(String itemId) {
        Optional<BdStorage> bdStorageOptional = bdStorageService.query()
                .eq(BdStorage.ITEM_ID, itemId).oneOpt();
        if (!bdStorageOptional.isPresent()) return 0;
        Integer inventory = bdStorageOptional.get().getInventory();
        if (inventory == null) inventory = 0;
        return inventory;
    }

    @Override
    public Result reduce(String itemId, Integer count) {
//        sqlMapper.reduce(itemId, count);
        try {
            String key = KeyUtils.cacheKey(itemId);

            String o = redisTemplate.opsForValue().get(key);
            if (o == null) {
                /*获取分布式锁*/
                DistributedLock lock = lockFactory.newLock(KeyUtils.LockKey(itemId), 60);
                try {
                    lock.lock();
                    long storage = getStorage(itemId);
                    redisTemplate.opsForValue().set(key, String.valueOf(storage), DEFAULT_TTL, TimeUnit.SECONDS);
                    Thread.sleep(10 * 1000L);
                } finally {
                    lock.unlock();
                }
            }
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setResultType(Long.class);
            redisScript.setScriptText(LUA);
            Long res = redisTemplate.execute(redisScript, Arrays.asList(key), String.valueOf(count));
            if (res != null && res >= 0) return Result.success(res);
            else return Result.fail();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.fail("服务异常");
        }
    }

    @Override
    public Result increase(String itemCode, Integer count) {
        return null;
    }
}
