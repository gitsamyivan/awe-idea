package awe.idea.com.api.sso;

import awe.idea.com.api.config.Constants;
import awe.idea.com.api.utils.RedisUtil;
import awe.idea.com.common.models.ApiUserSSOInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static awe.idea.com.common.utils.CacheKey.RedisCacheKey.API_SSO_REDIS_KEY;

@Component("loginSSORedisUtil")
public class LoginSSORedisUtil extends AbstrLoginSSOUtil {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void setSsoToken(ApiUserSSOInfo userSSOInfo) {
        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + Constants.TOKEN_EXPIRED_TIME);
        String username = userSSOInfo.getUsername();
        String token = userSSOInfo.getToken();

        Map<String,ApiUserSSOInfo>  userSSOInfoMap = (Map<String,ApiUserSSOInfo>)  redisUtil.getHashKey(API_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null){
            //不允许多点登录，删除其他sso
            if(!userSSOInfoMap.isEmpty() && !MULTI_LOGIN){
                for (Iterator<Map.Entry<String, ApiUserSSOInfo>> it = userSSOInfoMap.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, ApiUserSSOInfo> inner = it.next();
                    if (!inner.getKey().equals(token)) {
                        it.remove();
                    }
                }
            }

            if(!userSSOInfoMap.isEmpty()){
                if(userSSOInfoMap.size() >= 3){
                    //大于3个sso，删除一个最老的
                    List<ApiUserSSOInfo> ssoInfoList =new ArrayList<>(userSSOInfoMap.values());
                    Collections.sort(ssoInfoList, new Comparator<ApiUserSSOInfo>() {
                        @Override
                        public int compare(ApiUserSSOInfo o1, ApiUserSSOInfo o2) {
                            return o1.getExpireTime().compareTo(o2.getExpireTime());
                        }
                    });
                    userSSOInfoMap.remove(ssoInfoList.get(0).getToken());
                }
                if(userSSOInfoMap.containsKey(token)) {
                    //更新
                    userSSOInfo = userSSOInfoMap.get(token);
                }
                userSSOInfo.setCreateTime(now);
                userSSOInfo.setExpireTime(expiredTime);
                userSSOInfoMap.put(token,userSSOInfo);
                redisUtil.putHash(API_SSO_REDIS_KEY,username,userSSOInfoMap);
            }else{
                //新增sso
                userSSOInfo.setCreateTime(now);
                userSSOInfo.setExpireTime(expiredTime);
                userSSOInfoMap.put(token,userSSOInfo);
                redisUtil.putHash(API_SSO_REDIS_KEY,username,userSSOInfoMap);
            }
        }else{
            userSSOInfoMap = new ConcurrentHashMap<>();
            userSSOInfo.setCreateTime(now);
            userSSOInfo.setExpireTime(expiredTime);
            userSSOInfoMap.put(token,userSSOInfo);
            redisUtil.putHash(API_SSO_REDIS_KEY,username,userSSOInfoMap);
        }
    }

    @Override
    public ApiUserSSOInfo getSsoInfo(String username, String token) {
        Map<String,ApiUserSSOInfo>  userSSOInfoMap = (Map<String,ApiUserSSOInfo>)  redisUtil.getHashKey(API_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null && userSSOInfoMap.containsKey(token)){
            return userSSOInfoMap.get(token);
        }
        return null;
    }

    @Override
    public void removeSsoInfo(String username,String token) {
        Map<String,ApiUserSSOInfo>  userSSOInfoMap = (Map<String,ApiUserSSOInfo>)  redisUtil.getHashKey(API_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null && !userSSOInfoMap.isEmpty()){
            if(userSSOInfoMap.containsKey(token)){
                userSSOInfoMap.remove(token);
                redisUtil.putHash(API_SSO_REDIS_KEY,username,userSSOInfoMap);
            }
        }
    }

    @Override
    public boolean checkMultiLogin(String username, String token) {
        if(!MULTI_LOGIN){
            Map<String,ApiUserSSOInfo>  userSSOInfoMap = (Map<String,ApiUserSSOInfo>)  redisUtil.getHashKey(API_SSO_REDIS_KEY,username);
            if(userSSOInfoMap != null){
                    if(!userSSOInfoMap.isEmpty()) {
                        if (userSSOInfoMap.size() > 1) {
                            return true;
                        }
                        if (!userSSOInfoMap.containsKey(token)) {
                            return true;
                        }
                    }
            }
        }
        return  false;
    }

    @Override
    public boolean checkMultiLogin(String username){
        removeExpiredSso(username);
        if(!MULTI_LOGIN){
            Map<String,ApiUserSSOInfo>  userSSOInfoMap = (Map<String,ApiUserSSOInfo>)  redisUtil.getHashKey(API_SSO_REDIS_KEY,username);
            if(userSSOInfoMap != null){
                if(!userSSOInfoMap.isEmpty()) {
                    return true;
                }
            }
        }
        return  false;
    }

    //删除过期sso
    public void removeExpiredSso(String username){
        Map<String,ApiUserSSOInfo>  userSSOInfoMap = (Map<String,ApiUserSSOInfo>)  redisUtil.getHashKey(API_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null && !userSSOInfoMap.isEmpty()) {
            for (Iterator<Map.Entry<String, ApiUserSSOInfo>> it = userSSOInfoMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, ApiUserSSOInfo> inner = it.next();
                if (inner.getValue().isExpired()) {
                    it.remove();
                }
            }
            redisUtil.putHash(API_SSO_REDIS_KEY,username,userSSOInfoMap);
        }
    }
}
