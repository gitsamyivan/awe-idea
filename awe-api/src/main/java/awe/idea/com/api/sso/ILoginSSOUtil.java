package awe.idea.com.api.sso;

import awe.idea.com.common.models.ApiUserSSOInfo;

public interface ILoginSSOUtil {
    void setSsoToken(ApiUserSSOInfo userSSOInfo);

    ApiUserSSOInfo getSsoInfo(String username, String token);

    void removeSsoInfo(String username,String token);

    boolean checkMultiLogin(String username,String token);

    boolean checkMultiLogin(String username);
}
