package com.charsmart.data.order.domain;

import com.charsmart.data.api.layer.AccountService;
import com.charsmart.data.api.layer.OrderService;
import com.charsmart.data.api.layer.StorageService;
import com.charsmart.data.common.Result;
import com.charsmart.data.common.global.SnowId;
import com.charsmart.data.order.repository.BdOrder;
import com.charsmart.data.order.repository.BdOrderMapper;
import com.charsmart.data.order.repository.BdOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/6 5:40 PM
 */
@Service
@DubboService(version = "1.0.0", protocol = {"dubbo,rest"})
public class OrderServiceImpl implements OrderService {

    private final BdOrderService bdOrderService;
    private final BdOrderMapper bdOrderMapper;
    @DubboReference(timeout = 3000)
    private StorageService storageService;
    @DubboReference
    private AccountService accountService;
    private final SnowId snowId;
    private final BigDecimal PRICE = BigDecimal.valueOf(10.0);

    public OrderServiceImpl(BdOrderService bdOrderService, BdOrderMapper bdOrderMapper, SnowId snowId) {
        this.bdOrderService = bdOrderService;
        this.bdOrderMapper = bdOrderMapper;
        this.snowId = snowId;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public Result createOrder(String itemCode, Integer count) {
        if (count <= 0) return Result.fail("数量应该大于0");
        if (itemCode == null || "".equals(itemCode)) return Result.fail("商品不能为空");
        /*判断库存*/
        /*创建订单*/
        String orderNum = "P202206060001";
        BigDecimal total = PRICE.multiply(BigDecimal.valueOf(count));
        BdOrder order = new BdOrder().setOrderId(snowId.nextId())
                .setOrderNum(orderNum)
                .setItemCode(itemCode)
                .setStatus("I")
                .setCreateDate(LocalDateTime.now())
                .setTotal(total);
        try {
            bdOrderMapper.insert(order);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("执行异常");
        }
        /*扣减库存*/
        try {
            Result result = storageService.reduce(itemCode, count);
            if (!result.isSuccess()) {
                throw new RuntimeException(result.getMessage());
            }
            /*扣减余额*/
            result = accountService.reduce(total);
            if (!result.isSuccess()) {
                throw new RuntimeException(result.getMessage());
            }
        } catch (Exception ex) {
            throw new RuntimeException("执行异常");
        }
        return Result.success(orderNum);
    }

    @Override
    public Result cancelOrder(String orderNum) {
        return null;
    }
}
