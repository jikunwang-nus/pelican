package com.charsmart.pelican.lake.redis;

import io.netty.util.HashedWheelTimer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/16 19:45
 */
@Component
public class PLockRegister {
    protected final RedisTemplate<String, String> redisTemplate;
    protected final HashedWheelTimer timer = new HashedWheelTimer();

    protected Map<String, RedisScript<Long>> luaScripts = new LinkedHashMap<>();

    public PLockRegister(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public PLock getLock(String lockName) {
        return new PelicanLock(this, lockName);
    }

    public PLock getLock(String lockName, long ttl) {
        return new PelicanLock(this, lockName, ttl);
    }

    public PLock getLock(String lockName, long ttl, boolean refresh) {
        return new PelicanLock(this, lockName, ttl, refresh);
    }

    /**
     * all lua files are located in scripts/*.lua
     * when lua file is not initialized,it will be wrapper into script instance and will be cached
     * in this register
     *
     * @param luaKey different keys to execute seperated task
     * @return redis lua script instance
     */
    public RedisScript<Long> getLuaScript(String luaKey) {
        DefaultRedisScript<Long> redisScript = (DefaultRedisScript<Long>) this.luaScripts.get(luaKey);
        if (redisScript == null) {
            redisScript = new DefaultRedisScript<>();
            redisScript.setResultType(Long.class);
            String path = "scripts/" + luaKey + ".lua";
            redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
            luaScripts.put("try_lock", redisScript);
        }
        return redisScript;
    }
}
