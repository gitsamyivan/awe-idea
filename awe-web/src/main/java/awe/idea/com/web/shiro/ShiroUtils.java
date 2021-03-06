package awe.idea.com.web.shiro;

import awe.idea.com.common.utils.RRException;
import awe.idea.com.service.entity.SysUserEntity;
import awe.idea.com.service.entity.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月12日 上午9:49:19
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static UserEntity getUserEntity() {
		return (UserEntity)SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUserEntity().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	public static String getKaptcha(String key) {
		Object kaptchaObject = getSessionAttribute(key);
		if(kaptchaObject == null){
			throw new RRException("验证码错误，请刷新重试。");
		}
		String kaptcha = kaptchaObject.toString();
		getSession().removeAttribute(key);
		return kaptcha;
	}

}
