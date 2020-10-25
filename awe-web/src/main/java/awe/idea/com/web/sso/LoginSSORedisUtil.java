package awe.idea.com.web.sso;

import awe.idea.com.common.models.WebUserSSOInfo;
import awe.idea.com.web.shiro.ShiroConfig;
import awe.idea.com.web.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static awe.idea.com.common.utils.CacheKey.RedisCacheKey.WEB_SSO_REDIS_KEY;

@Component("loginSSORedisUtil")
public class LoginSSORedisUtil extends AbstrLoginSSOUtil {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void setSsoToken(String sessionId,WebUserSSOInfo userSSOInfo) {
        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + ShiroConfig.DEFAUL_EXPIRE_TIME);
        String username = userSSOInfo.getUsername();

        Map<String,WebUserSSOInfo>  userSSOInfoMap = (Map<String,WebUserSSOInfo>)  redisUtil.getHashKey(WEB_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null){
            //不允许多点登录，删除其他sso
            if(!userSSOInfoMap.isEmpty() && !MULTI_LOGIN){
                for (Iterator<Map.Entry<String, WebUserSSOInfo>> it = userSSOInfoMap.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, WebUserSSOInfo> inner = it.next();
                    if (!inner.getKey().equals(sessionId)) {
                        it.remove();
                    }
                }
            }

            if(!userSSOInfoMap.isEmpty()){
                if(userSSOInfoMap.size() >= 3){
                    //大于3个sso，删除一个最老的
                    List<WebUserSSOInfo> ssoInfoList =new ArrayList<>(userSSOInfoMap.values());
                    Collections.sort(ssoInfoList, new Comparator<WebUserSSOInfo>() {
                        @Override
                        public int compare(WebUserSSOInfo o1, WebUserSSOInfo o2) {
                            return o1.getExpireTime().compareTo(o2.getExpireTime());
                        }
                    });
                    userSSOInfoMap.remove(ssoInfoList.get(0).getSessionId());
                }
                if(userSSOInfoMap.containsKey(sessionId)){
                    //更新
                    userSSOInfo = userSSOInfoMap.get(sessionId);
                }
                userSSOInfo.setCreateTime(now);
                userSSOInfo.setExpireTime(expiredTime);
                userSSOInfoMap.put(sessionId,userSSOInfo);
                redisUtil.putHash(WEB_SSO_REDIS_KEY,username,userSSOInfoMap);
            }else{
                //新增sso
                userSSOInfo.setCreateTime(now);
                userSSOInfo.setExpireTime(expiredTime);
                userSSOInfoMap.put(sessionId,userSSOInfo);
                redisUtil.putHash(WEB_SSO_REDIS_KEY,username,userSSOInfoMap);
            }
        }else{
            userSSOInfoMap = new ConcurrentHashMap<>();
            userSSOInfo.setCreateTime(now);
            userSSOInfo.setExpireTime(expiredTime);
            userSSOInfoMap.put(sessionId,userSSOInfo);
            redisUtil.putHash(WEB_SSO_REDIS_KEY,username,userSSOInfoMap);
        }
    }

    @Override
    public WebUserSSOInfo getSsoInfo(String sessionId, String username) {
        Map<String,WebUserSSOInfo>  userSSOInfoMap = (Map<String,WebUserSSOInfo>)  redisUtil.getHashKey(WEB_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null && userSSOInfoMap.containsKey(sessionId)){
            return userSSOInfoMap.get(sessionId);
        }
        return null;
    }

    @Override
    public void removeSsoInfo(String sessionId,String username) {
        Map<String,WebUserSSOInfo>  userSSOInfoMap = (Map<String,WebUserSSOInfo>)  redisUtil.getHashKey(WEB_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null && !userSSOInfoMap.isEmpty()){
            if(userSSOInfoMap.containsKey(sessionId)){
                userSSOInfoMap.remove(sessionId);
                redisUtil.putHash(WEB_SSO_REDIS_KEY,username,userSSOInfoMap);
            }
        }
    }

    @Override
    public boolean checkMultiLogin(String sessionId,String username) {
        removeExpiredSso(username);
        if(!MULTI_LOGIN){
            Map<String,WebUserSSOInfo>  userSSOInfoMap = (Map<String,WebUserSSOInfo>)  redisUtil.getHashKey(WEB_SSO_REDIS_KEY,username);
            if(userSSOInfoMap != null){
                    if(!userSSOInfoMap.isEmpty()) {
                        if (userSSOInfoMap.size() > 1) {
                            return true;
                        }
                        if (!userSSOInfoMap.containsKey(sessionId)) {
                            return true;
                        }
                    }
            }
        }
        return  false;
    }

    //删除过期sso
    public void removeExpiredSso(String username){
        Map<String,WebUserSSOInfo>  userSSOInfoMap = (Map<String,WebUserSSOInfo>)  redisUtil.getHashKey(WEB_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null && !userSSOInfoMap.isEmpty()) {
            for (Iterator<Map.Entry<String, WebUserSSOInfo>> it = userSSOInfoMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, WebUserSSOInfo> inner = it.next();
                if (inner.getValue().isExpired()) {
                    it.remove();
                }
            }
            redisUtil.putHash(WEB_SSO_REDIS_KEY,username,userSSOInfoMap);
        }
    }
}
