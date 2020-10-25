package awe.idea.com.web.shiro;

import awe.idea.com.common.models.ApiUserSSOInfo;
import awe.idea.com.common.models.WebUserSSOInfo;
import awe.idea.com.common.models.api.UserLoginReq;
import awe.idea.com.common.utils.CacheKey;
import awe.idea.com.common.utils.GenerateUtil;
import awe.idea.com.service.entity.SysUserEntity;
import awe.idea.com.service.entity.UserEntity;
import awe.idea.com.service.service.UserService;
import awe.idea.com.web.sso.ILoginSSOUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * 认证
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 上午11:55:49
 *
 * 此类内部注入Service需要加@Lazy，不然导致事务失效
 */
@Component
public class UserRealm extends AuthorizingRealm {
	@Autowired
	@Lazy
	private UserService userService;
	@Resource(name = "loginSSOInstance")
	@Lazy
	private ILoginSSOUtil loginSSOUtil;
	@Autowired
	@Lazy
	private HttpSession httpSession;

	@Override
	protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
		return super.isPermitted(permission, info);
	}

	/**
     * 授权(验证权限时调用)
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
		return user.getAuthorizationInfo();
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
		UserLoginReq userLoginReq = UserLoginReq.builder()
				.username(username)
				.password(password)
				.client("web")
				.version("1.0")
				.platform("awe")
				.build();
		//查询用户信息
		UserEntity user = userService.queryByUserName(username);

		//账号不存在
		if(user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		//密码错误
		if(!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		String sessionId = httpSession.getId();
		if(loginSSOUtil.checkMultiLogin(sessionId,user.getUsername())){
			throw new IncorrectCredentialsException("账号不允许多点登录,请联系管理员");
		}
		//也可以使用redis存sessionId-username
		httpSession.setAttribute(CacheKey.SessionKey.WEB_SESSION_USER,user.getUsername());

		WebUserSSOInfo userSSOInfo = WebUserSSOInfo.builder()
				.sessionId(sessionId)
				.client(userLoginReq.getClient())
				.version(userLoginReq.getVersion())
				.platform(userLoginReq.getPlatform())
				.username(user.getUsername())
				.mobile(user.getMobile())
				.build();
		loginSSOUtil.setSsoToken(sessionId,userSSOInfo);

		//权限角色
		Set<String> userRoleIds = new HashSet<>();

		SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
		authInfo.setRoles(userRoleIds);
		user.setAuthorizationInfo(authInfo);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
	}

}
