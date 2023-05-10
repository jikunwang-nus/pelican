package com.charsmart.data.storage.domain;

import com.charsmart.data.api.layer.StorageService;
import com.charsmart.data.common.Result;
import com.charsmart.data.storage.repository.BdStorage;
import com.charsmart.data.storage.repository.BdStorageService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/6 5:30 PM
 */
@DubboService
@Service
public class StorageServiceImpl implements StorageService {
    private final BdStorageService bdStorageService;

    public StorageServiceImpl(BdStorageService bdStorageService) {
        this.bdStorageService = bdStorageService;
    }

    @Override
    public Result reduce(String itemCode, Integer count) {
        try {
            Optional<BdStorage> bdStorageOptional = bdStorageService.query()
                    .eq(BdStorage.ITEM_CODE, itemCode).oneOpt();
            if (!bdStorageOptional.isPresent()) return Result.fail("商品不存在");
            BdStorage entity = bdStorageOptional.get();
            if (entity.getInventory() < count) return Result.fail("库存不足");
            synchronized (this) {
                entity.setInventory(entity.getInventory() - count);
                bdStorageService.updateById(entity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.fail("服务异常");
        }

        return Result.success();
    }

    @Override
    public Result increase(String itemCode, Integer count) {
        return null;
    }
}
