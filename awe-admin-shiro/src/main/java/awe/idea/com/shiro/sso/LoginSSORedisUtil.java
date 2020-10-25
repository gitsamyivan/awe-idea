package awe.idea.com.shiro.sso;

import awe.idea.com.shiro.shiro.ShiroConfig;
import awe.idea.com.shiro.utils.RedisUtil;
import awe.idea.com.common.models.AdminUserSSOInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static awe.idea.com.common.utils.CacheKey.RedisCacheKey.ADMIN_SSO_REDIS_KEY;

@Component("loginSSORedisUtil")
public class LoginSSORedisUtil extends AbstrLoginSSOUtil {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void setSsoToken(String sessionId, String username) {
        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + ShiroConfig.DEFAUL_EXPIRE_TIME);

        Map<String,AdminUserSSOInfo>  userSSOInfoMap = (Map<String,AdminUserSSOInfo>)  redisUtil.getHashKey(ADMIN_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null){
            //不允许多点登录，删除其他sso
            if(!userSSOInfoMap.isEmpty() && !MULTI_LOGIN){
                for (Iterator<Map.Entry<String, AdminUserSSOInfo>> it = userSSOInfoMap.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, AdminUserSSOInfo> inner = it.next();
                    if (!inner.getKey().equals(sessionId)) {
                        it.remove();
                    }
                }
            }

            if(!userSSOInfoMap.isEmpty()){
                if(userSSOInfoMap.size() >= 3){
                    //大于3个sso，删除一个最老的
                    List<AdminUserSSOInfo> ssoInfoList =new ArrayList<>(userSSOInfoMap.values());
                    Collections.sort(ssoInfoList, new Comparator<AdminUserSSOInfo>() {
                        @Override
                        public int compare(AdminUserSSOInfo o1, AdminUserSSOInfo o2) {
                            return o1.getExpireTime().compareTo(o2.getExpireTime());
                        }
                    });
                    userSSOInfoMap.remove(ssoInfoList.get(0).getSessionId());
                }
                if(userSSOInfoMap.containsKey(sessionId)){
                    //更新
                    AdminUserSSOInfo ssoInfo = userSSOInfoMap.get(sessionId);
                    ssoInfo.setSessionId(sessionId);
                    ssoInfo.setCreateTime(now);
                    ssoInfo.setExpireTime(expiredTime);
                }else{
                    //不同sessionid登录，新增sso
                    AdminUserSSOInfo ssoInfo = AdminUserSSOInfo.builder()
                            .sessionId(sessionId).username(username)
                            .createTime(now).expireTime(expiredTime).build();
                    userSSOInfoMap.put(sessionId,ssoInfo);
                }
                redisUtil.putHash(ADMIN_SSO_REDIS_KEY,username,userSSOInfoMap);
            }else{
                //新增sso
                AdminUserSSOInfo ssoInfo = AdminUserSSOInfo.builder()
                        .sessionId(sessionId).username(username)
                        .createTime(now).expireTime(expiredTime).build();
                userSSOInfoMap.put(sessionId,ssoInfo);
                redisUtil.putHash(ADMIN_SSO_REDIS_KEY,username,userSSOInfoMap);
            }
        }else{
            userSSOInfoMap = new ConcurrentHashMap<>();
            AdminUserSSOInfo ssoInfo = AdminUserSSOInfo.builder()
                    .sessionId(sessionId).username(username)
                    .createTime(now).expireTime(expiredTime).build();
            userSSOInfoMap.put(sessionId,ssoInfo);
            redisUtil.putHash(ADMIN_SSO_REDIS_KEY,username,userSSOInfoMap);
        }
    }

    @Override
    public AdminUserSSOInfo getSsoInfo(String sessionId, String username) {
        Map<String,AdminUserSSOInfo>  userSSOInfoMap = (Map<String,AdminUserSSOInfo>)  redisUtil.getHashKey(ADMIN_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null && userSSOInfoMap.containsKey(sessionId)){
            return userSSOInfoMap.get(sessionId);
        }
        return null;
    }

    @Override
    public void removeSsoInfo(String sessionId,String username) {
        Map<String,AdminUserSSOInfo>  userSSOInfoMap = (Map<String,AdminUserSSOInfo>)  redisUtil.getHashKey(ADMIN_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null && !userSSOInfoMap.isEmpty()){
            if(userSSOInfoMap.containsKey(sessionId)){
                userSSOInfoMap.remove(sessionId);
                redisUtil.putHash(ADMIN_SSO_REDIS_KEY,username,userSSOInfoMap);
            }
        }
    }

    @Override
    public boolean checkMultiLogin(String sessionId,String username) {
        removeExpiredSso(username);
        if(!MULTI_LOGIN){
            Map<String,AdminUserSSOInfo>  userSSOInfoMap = (Map<String,AdminUserSSOInfo>)  redisUtil.getHashKey(ADMIN_SSO_REDIS_KEY,username);
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
        Map<String,AdminUserSSOInfo>  userSSOInfoMap = (Map<String,AdminUserSSOInfo>)  redisUtil.getHashKey(ADMIN_SSO_REDIS_KEY,username);
        if(userSSOInfoMap != null && !userSSOInfoMap.isEmpty()) {
            for (Iterator<Map.Entry<String, AdminUserSSOInfo>> it = userSSOInfoMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, AdminUserSSOInfo> inner = it.next();
                if (inner.getValue().isExpired()) {
                    it.remove();
                }
            }
            redisUtil.putHash(ADMIN_SSO_REDIS_KEY,username,userSSOInfoMap);
        }
    }
}
