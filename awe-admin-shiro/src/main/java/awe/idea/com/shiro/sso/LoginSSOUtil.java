package awe.idea.com.shiro.sso;

import awe.idea.com.common.models.AdminUserSSOInfo;
import awe.idea.com.shiro.shiro.ShiroConfig;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component("loginSSOUtil")
public class LoginSSOUtil extends AbstrLoginSSOUtil {

    private static final Map<String,Object> SESSION_SSOINFO_MAP = new ConcurrentHashMap<>();

    public synchronized void setSsoToken(String sessionId,String username){
        Map<String,AdminUserSSOInfo> userSSOInfoMap = null;
        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + ShiroConfig.DEFAUL_EXPIRE_TIME);

        if(SESSION_SSOINFO_MAP.containsKey(username)){
            userSSOInfoMap = (Map<String,AdminUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);

            if(userSSOInfoMap == null){
                userSSOInfoMap = new ConcurrentHashMap<>();
            }

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
            }else{
                //新增sso
                AdminUserSSOInfo ssoInfo = AdminUserSSOInfo.builder()
                        .sessionId(sessionId).username(username)
                        .createTime(now).expireTime(expiredTime).build();
                userSSOInfoMap.put(sessionId,ssoInfo);
            }
        }else{
            userSSOInfoMap = new ConcurrentHashMap<>();
            AdminUserSSOInfo ssoInfo = AdminUserSSOInfo.builder()
                    .sessionId(sessionId).username(username)
                    .createTime(now).expireTime(expiredTime).build();
            userSSOInfoMap.put(sessionId,ssoInfo);
            SESSION_SSOINFO_MAP.put(username,userSSOInfoMap);
        }
    }

    public synchronized AdminUserSSOInfo getSsoInfo(String sessionId, String username){
        if(SESSION_SSOINFO_MAP.containsKey(username)){
            Map<String,AdminUserSSOInfo> userSSOInfoMap = (Map<String,AdminUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);
            return userSSOInfoMap.get(sessionId);
        }
        return  null;
    }

    /**
     * 删除sso信息
     * @param sessionId
     * @param username
     */
    public synchronized void removeSsoInfo(String sessionId,String username){
        if(SESSION_SSOINFO_MAP.containsKey(username)){
            Map<String,AdminUserSSOInfo> userSSOInfoMap = (Map<String,AdminUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);
            if(userSSOInfoMap.containsKey(sessionId)){
                userSSOInfoMap.remove(sessionId);
            }
        }
    }

    /**
     * 判断多点登录
     * @param username
     * @param sessionId
     * @return
     */
    public synchronized boolean checkMultiLogin(String sessionId,String username){
        removeExpiredSso(username);
        if(!MULTI_LOGIN){
            Map<String,AdminUserSSOInfo> userSSOInfoMap = (Map<String,AdminUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);
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
        Map<String,AdminUserSSOInfo>  userSSOInfoMap = (Map<String,AdminUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);
        if(userSSOInfoMap != null && !userSSOInfoMap.isEmpty()) {
            for (Iterator<Map.Entry<String, AdminUserSSOInfo>> it = userSSOInfoMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, AdminUserSSOInfo> inner = it.next();
                if (inner.getValue().isExpired()) {
                    it.remove();
                }
            }
        }
    }

}
