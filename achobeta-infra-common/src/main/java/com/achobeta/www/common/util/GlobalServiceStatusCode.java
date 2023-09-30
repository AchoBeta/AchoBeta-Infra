package com.achobeta.www.common.util;

import lombok.Getter;

/**
 * <p>
 *     global service status code,
 *     Please note that status code definitions are module-specific
 *     and do not occupy other business modules when defining them.
 * </p>
 * @version 1.0
 * @author jettcc in 2023/9/30
 */
@Getter
public enum GlobalServiceStatusCode {
    /* 成功, 默认200 */
    SYSTEM_SUCCESS(200, "操作成功"),

    /* 系统错误 500 - 1000 */
    SYSTEM_SERVICE_FAIL(-4396, "操作失败"),
    SYSTEM_SERVICE_ERROR(-500, "系统异常"),
    SYSTEM_TIME_OUT(-1, "请求超时"),

    /* 参数错误：1001～2000 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 2001-3000 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    USER_NO_PERMISSION(2403, "用户无权限"),
    USER_NO_PHONE_CODE(2500, "验证码错误"),

    /* -------------- */
    ;

    private final Integer code;
    private final String message;

    GlobalServiceStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    /**
     * 根据code获取message
     *
     * @param code 状态码的code
     * @return 状态码的状态信息
     */
    public static String GetStatusMsgByCode(Integer code) {
        for (GlobalServiceStatusCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }
}
