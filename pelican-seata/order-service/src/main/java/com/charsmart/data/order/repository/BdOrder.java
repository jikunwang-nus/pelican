package com.charsmart.data.order.repository;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wonder@charsmart.com
 * @since 2022-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BdOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "ORDER_ID", type = IdType.INPUT)
    private Long orderId;

    /**
     * 订单号
     */
    @TableField("ORDER_NUM")
    private String orderNum;

    /**
     * 商品编码
     */
    @TableField("ITEM_CODE")
    private String itemCode;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 订单总价
     */
    @TableField("TOTAL")
    private BigDecimal total;


    public static final String ORDER_ID = "ORDER_ID";

    public static final String ORDER_NUM = "ORDER_NUM";

    public static final String ITEM_CODE = "ITEM_CODE";

    public static final String CREATE_DATE = "CREATE_DATE";

    public static final String STATUS = "STATUS";

    public static final String TOTAL = "TOTAL";

}
