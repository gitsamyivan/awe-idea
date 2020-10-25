package awe.idea.com.shiro.sso;

import awe.idea.com.common.models.AdminUserSSOInfo;

public interface ILoginSSOUtil {
    void setSsoToken(String sessionId, String username);

    AdminUserSSOInfo getSsoInfo(String sessionId, String username);

    void removeSsoInfo(String sessionId, String username);

    boolean checkMultiLogin(String sessionId, String username);

    void removeExpiredSso(String username);
}
