package awe.idea.com.web.shiro;

import awe.idea.com.common.utils.CacheKey;
import awe.idea.com.web.sso.ILoginSSOUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 非redis的sessionManager管理
 */
@Component
public class ShiroSessionListener implements SessionListener {
	@Resource(name = "loginSSOInstance")
	private ILoginSSOUtil loginSSOUtil;

	@Override
    public void onStart(Session session) {
	}
 
	@Override
	public void onStop(Session session) {
		System.out.println("onStop==="+session.getId());
		removeSession(session);
	}
 
	@Override
	public void onExpiration(Session session) {
		System.out.println("onExpiration==="+session.getId());
		removeSession(session);
	}

	public void removeSession(Session session){
		String username = (String) session.getAttribute(CacheKey.SessionKey.WEB_SESSION_USER);
		String sessionId = session.getId().toString();
		if(StringUtils.isNotEmpty(username)){
			loginSSOUtil.removeSsoInfo(sessionId,username);
		}
	}

 
}
