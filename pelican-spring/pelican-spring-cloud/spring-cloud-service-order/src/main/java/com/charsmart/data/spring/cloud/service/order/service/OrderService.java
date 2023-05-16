package com.charsmart.data.spring.cloud.service.order.service;

import com.charsmart.data.distributed.log.TraceLogger;
import com.charsmart.data.spring.cloud.service.order.feign.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/30 22:43
 */
@RestController

public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final InventoryService inventoryServiceFeign;

    public OrderService(InventoryService inventoryServiceFeign) {
        this.inventoryServiceFeign = inventoryServiceFeign;
    }

    @RequestMapping("/order/get")
    public String getOrder() {
        return "order";
    }

    @PostMapping("/order/create")
    public String createOrder() {
        TraceLogger.trace("create order already !");
        TraceLogger.call("reduce inv qty by 1");
        Integer reduce = inventoryServiceFeign.reduce("1");
        TraceLogger.trace("reduce complete!");
        if (reduce > 0) return "ok";
        else return "nck";
    }
}
