package com.achobeta.www.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <span>
 * classes that inherit this entity all have auto-incrementing ID.
 * </span>
 *
 * @author jettcc in 2023/10/1
 * @version 1.0
 */
@Getter
@Setter
public class BaseIncrIDEntity implements Serializable {
    /**
     * id, incr
     */
    @TableId(type = IdType.AUTO, value = "c_id")
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "c_create_time", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "c_update_time", fill = FieldFill.UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 乐观锁
     */
    @TableField(value = "c_version", fill = FieldFill.INSERT)
    @Version
    private Integer version;

    @TableField(value = "c_is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;
}
