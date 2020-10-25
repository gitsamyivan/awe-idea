package awe.idea.com.api.config;

public class Constants {
	//输入参数加密
	public static volatile Boolean INPUT_ENCRYPT_ENABLE = true;
	//输出结果加密
	public static volatile Boolean OUT_ENCRYPT_ENABLE = false;
	//token过期时间默认7天
	public static final long TOKEN_EXPIRED_TIME = 7*24*3600*1000;
	//验证码过期时间默认5min
	public static final long CAPTCHA_EXPIRED_TIME = 5*60*1000;

}
