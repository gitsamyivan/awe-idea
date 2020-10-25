package awe.idea.com.web.sso;

import awe.idea.com.common.models.WebUserSSOInfo;

public interface ILoginSSOUtil {
    void setSsoToken(String sessionId,WebUserSSOInfo userSSOInfo);

    WebUserSSOInfo getSsoInfo(String sessionId, String username);

    void removeSsoInfo(String sessionId, String username);

    boolean checkMultiLogin(String sessionId, String username);

    void removeExpiredSso(String username);
}
