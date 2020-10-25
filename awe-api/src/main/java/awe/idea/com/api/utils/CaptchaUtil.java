package awe.idea.com.api.utils;

import awe.idea.com.api.config.Constants;
import awe.idea.com.api.config.ErrorCode;
import awe.idea.com.api.utils.RedisUtil;
import awe.idea.com.common.models.ApiCaptchaInfo;
import awe.idea.com.common.utils.CacheKey;
import awe.idea.com.common.utils.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 验证码模块
 */
@Component
public class CaptchaUtil {
    @Value("${awe.idea.captch.redis:false}")
    private Boolean captchaRedisEnable;
    @Autowired
    private RedisUtil redisUtil;

    private static final Map<String,ApiCaptchaInfo> USER_CAPTCHA_MAP = new ConcurrentHashMap<>();

    //ConcurrentHashMap put方法有加锁，get没有锁
    public void setCaptcha(String username,String code){
        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + Constants.CAPTCHA_EXPIRED_TIME);
        ApiCaptchaInfo captchaInfo = ApiCaptchaInfo.builder()
                .username(username)
                .captcha(code)
                .createTime(now)
                .expireTime(expiredTime)
                .build();
        if(captchaRedisEnable){
            redisUtil.putHash(CacheKey.RedisCacheKey.API_CAPTCHA_REDIS_KEY,username,captchaInfo);
        }else {
            USER_CAPTCHA_MAP.put(username, captchaInfo);
        }
    }

    public synchronized String getCaptcha(String username){
        ApiCaptchaInfo captchaInfo = null;
        if(captchaRedisEnable){
            captchaInfo = (ApiCaptchaInfo) redisUtil.getHashKey(CacheKey.RedisCacheKey.API_CAPTCHA_REDIS_KEY,username);
        }else {
            captchaInfo = USER_CAPTCHA_MAP.get(username);
        }
        if(captchaInfo == null || captchaInfo.isExpired()){
            throw new RRException("验证码已过期，请刷新重试。", ErrorCode.LOGIN_CAPTCHA_FAIL.getCode());
        }
        return captchaInfo.getCaptcha();
    }

    public synchronized void removeCaptcha(String username){
        if(captchaRedisEnable){
            redisUtil.deleteHash(CacheKey.RedisCacheKey.API_CAPTCHA_REDIS_KEY,username);
        }else {
            USER_CAPTCHA_MAP.remove(username);
        }
    }
}
