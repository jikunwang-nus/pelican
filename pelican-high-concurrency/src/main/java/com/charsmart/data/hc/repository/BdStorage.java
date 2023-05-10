package com.charsmart.data.hc.repository;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class BdStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId("ITEM_ID")
    private Long itemId;

    /**
     * 商品编码
     */
    @TableField("ITEM_CODE")
    private String itemCode;

    /**
     * 商品库存
     */
    @TableField("INVENTORY")
    private Integer inventory;


    public static final String ITEM_ID = "ITEM_ID";

    public static final String ITEM_CODE = "ITEM_CODE";

    public static final String INVENTORY = "INVENTORY";

}
