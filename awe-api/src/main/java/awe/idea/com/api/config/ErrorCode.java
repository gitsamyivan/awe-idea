package awe.idea.com.api.config;

public enum ErrorCode {
    //1~500 基础错误代码
    ERROR(-1,"系统错误，稍后再试。"),
    SUCCESS(0,"操作成功。"),
    FAIL(1,"主动终止,操作失败。"),
    DATAERROR(2,"数据解析错误"),

    LOGIN_FAIL(100,"登录失败。"),
    LOGIN_FAIL_3TIME(101,"登录失败超过3次。"),
    TOKEN_FAIL(102,"身份信息验证失败。"),
    LOGIN_CAPTCHA_FAIL(103,"验证码错误。"),


    //1000～9999 业务失败代码
    B1000(1000,"订单失败，请稍后再试。")
    ;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
