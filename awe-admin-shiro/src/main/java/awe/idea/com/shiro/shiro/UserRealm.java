package awe.idea.com.shiro.shiro;

import awe.idea.com.shiro.sso.ILoginSSOUtil;
import awe.idea.com.common.utils.CacheKey;
import awe.idea.com.service.constants.Constants;
import awe.idea.com.service.entity.SysMenuEntity;
import awe.idea.com.service.entity.SysUserEntity;
import awe.idea.com.service.service.SysMenuService;
import awe.idea.com.service.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    private SysUserService sysUserService;
    @Autowired
	@Lazy
    private SysMenuService sysMenuService;
    @Autowired
	@Lazy
	private HttpSession httpSession;
	@Resource(name = "loginSSOInstance")
	@Lazy
	private ILoginSSOUtil loginSSOUtil;

	@Override
	protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
		return super.isPermitted(permission, info);
	}

	@Override
	protected boolean hasRole(String roleIdentifier, AuthorizationInfo info) {
		return super.hasRole(roleIdentifier, info);
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

        //查询用户信息
        SysUserEntity user = sysUserService.queryByUserName(username);
        
        //账号不存在
        if(user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        
        //密码错误
        if(!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }
        
        //账号锁定
        if(user.getStatus() == 0){
        	throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

		String sessionId = httpSession.getId();
        if(loginSSOUtil.checkMultiLogin(sessionId,user.getUsername())){
			throw new IncorrectCredentialsException("账号不允许多点登录,请联系管理员");
		}
		loginSSOUtil.setSsoToken(sessionId,user.getUsername());
        //也可以使用redis存sessionId-username
        httpSession.setAttribute(CacheKey.SessionKey.ADMIN_SESSION_USER,user.getUsername());

		List<String> permsList = null;
		//系统管理员，拥有最高权限
		if(user.getUserId() == Constants.SUPER_ADMIN){
			List<SysMenuEntity> menuList = sysMenuService.queryList(new HashMap<String, Object>());
			permsList = new ArrayList<>();
			for(SysMenuEntity menu : menuList){
				permsList.add(menu.getPerms());
			}
		}else{
			permsList = sysUserService.queryAllPerms(user.getUserId());
		}
		//用户权限列表
		Set<String> permsSet = new HashSet<String>();
		for(String perms : permsList){
			if(StringUtils.isBlank(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
		authInfo.setStringPermissions(permsSet);
		user.setAuthorizationInfo(authInfo);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
	}

}
