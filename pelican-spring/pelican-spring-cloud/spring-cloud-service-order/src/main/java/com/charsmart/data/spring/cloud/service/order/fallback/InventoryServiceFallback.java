package com.charsmart.data.spring.cloud.service.order.fallback;

import com.charsmart.data.spring.cloud.service.order.feign.InventoryService;
import org.springframework.stereotype.Component;


/**
 * @Author: Wonder
 * @Date: Created on 2023/3/30 22:21
 */
@Component
public class InventoryServiceFallback implements InventoryService {
    @Override
    public Integer reduce(String id) {
        return -99999;
    }
}
