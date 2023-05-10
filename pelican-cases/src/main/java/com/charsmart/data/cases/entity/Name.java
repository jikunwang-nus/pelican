package com.charsmart.data.cases.entity;

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
 * @since 2022-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Name implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("PARENT")
    private String parent;

    @TableField("NAME")
    private String name;

    @TableField("PARENT_ID")
    private Long parentId;

    @TableField("SEQUENCE")
    private Integer sequence;


    public static final String ID = "ID";

    public static final String PARENT = "PARENT";

    public static final String NAME = "NAME";

    public static final String PARENT_ID = "PARENT_ID";

    public static final String SEQUENCE = "SEQUENCE";

}
