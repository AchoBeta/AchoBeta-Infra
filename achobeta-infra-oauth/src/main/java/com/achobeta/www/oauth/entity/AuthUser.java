package com.achobeta.www.oauth.entity;

import com.achobeta.www.common.entity.BaseIncrIDEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <span>
 *  auth user model
 * </span>
 *
 * @author jettcc in 2023/10/17
 * @version 1.0
 */
@TableName(value = "t_ab_auth_user", autoResultMap = true)
@Getter
@Setter
@Builder
public class AuthUser extends BaseIncrIDEntity  {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableField(value = "c_uuid", fill = FieldFill.INSERT)
    private String uuid;

    @TableField("c_username")
    private String username;

    @TableField("c_password")
    private String password;

    @TableField("c_account")
    private String account;

    @TableField("c_phone")
    private String phone;

    @TableField("c_email")
    private String email;

    @TableField("c_openid")
    private String openid;

    @TableField("c_enabled")
    private Boolean enabled;

    @TableField("c_is_expired")
    private Boolean expired;

    @TableField("c_is_locked")
    private Boolean locked;

}
