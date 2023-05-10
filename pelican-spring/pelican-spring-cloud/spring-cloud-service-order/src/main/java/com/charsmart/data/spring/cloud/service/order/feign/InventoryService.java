package com.charsmart.data.spring.cloud.service.order.feign;

import com.charsmart.data.spring.cloud.service.order.fallback.InventoryServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/30 22:18
 */
@FeignClient(value = "inventory-service", fallback = InventoryServiceFallback.class)
public interface InventoryService {
    @PutMapping("/inv/reduce")
    Integer reduce(String id);
}
