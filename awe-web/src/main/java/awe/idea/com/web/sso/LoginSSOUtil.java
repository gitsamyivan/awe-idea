package awe.idea.com.web.sso;

import awe.idea.com.common.models.WebUserSSOInfo;
import awe.idea.com.web.shiro.ShiroConfig;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component("loginSSOUtil")
public class LoginSSOUtil extends AbstrLoginSSOUtil {

    private static final Map<String,Object> SESSION_SSOINFO_MAP = new ConcurrentHashMap<>();

    public synchronized void setSsoToken(String sessionId,WebUserSSOInfo userSSOInfo){
        Map<String,WebUserSSOInfo> userSSOInfoMap = null;
        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + ShiroConfig.DEFAUL_EXPIRE_TIME);
        String username = userSSOInfo.getUsername();

        if(SESSION_SSOINFO_MAP.containsKey(username)){
            userSSOInfoMap = (Map<String,WebUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);

            if(userSSOInfoMap == null){
                userSSOInfoMap = new ConcurrentHashMap<>();
            }

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
                //不同sessionid登录，新增sso
                userSSOInfo.setCreateTime(now);
                userSSOInfo.setExpireTime(expiredTime);
                userSSOInfoMap.put(sessionId,userSSOInfo);
            }else{
                //新增sso
                userSSOInfo.setCreateTime(now);
                userSSOInfo.setExpireTime(expiredTime);
                userSSOInfoMap.put(sessionId,userSSOInfo);
            }
        }else{
            userSSOInfoMap = new ConcurrentHashMap<>();
            userSSOInfo.setCreateTime(now);
            userSSOInfo.setExpireTime(expiredTime);
            userSSOInfoMap.put(sessionId,userSSOInfo);
            SESSION_SSOINFO_MAP.put(username,userSSOInfoMap);
        }
    }

    public synchronized WebUserSSOInfo getSsoInfo(String sessionId, String username){
        if(SESSION_SSOINFO_MAP.containsKey(username)){
            Map<String,WebUserSSOInfo> userSSOInfoMap = (Map<String,WebUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);
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
            Map<String,WebUserSSOInfo> userSSOInfoMap = (Map<String,WebUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);
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
            Map<String,WebUserSSOInfo> userSSOInfoMap = (Map<String,WebUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);
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
        Map<String,WebUserSSOInfo>  userSSOInfoMap = (Map<String,WebUserSSOInfo>) SESSION_SSOINFO_MAP.get(username);
        if(userSSOInfoMap != null && !userSSOInfoMap.isEmpty()) {
            for (Iterator<Map.Entry<String, WebUserSSOInfo>> it = userSSOInfoMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, WebUserSSOInfo> inner = it.next();
                if (inner.getValue().isExpired()) {
                    it.remove();
                }
            }
        }
    }

}
