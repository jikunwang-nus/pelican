package com.charsmart.data.account.repository;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class BdAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ACCOUNT_ID", type = IdType.INPUT)
    private Long accountId;

    /**
     * 用户名
     */
    @TableField("ACCOUNT_NAME")
    private String accountName;

    /**
     * 余额
     */
    @TableField("BALANCE")
    private BigDecimal balance;


    public static final String ACCOUNT_ID = "ACCOUNT_ID";

    public static final String ACCOUNT_NAME = "ACCOUNT_NAME";

    public static final String BALANCE = "BALANCE";

}
