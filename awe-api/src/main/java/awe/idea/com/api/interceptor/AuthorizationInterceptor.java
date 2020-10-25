package awe.idea.com.api.interceptor;

import awe.idea.com.api.annotation.IgnoreAuth;
import awe.idea.com.api.jwt.JwtTokenUtil;
import awe.idea.com.common.models.ApiUserSSOInfo;
import awe.idea.com.common.utils.CacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IgnoreAuth annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        }else{
            return true;
        }
        //如果有@IgnoreAuth注解，则不验证token
        if(annotation != null){
            return true;
        }
        ApiUserSSOInfo userSSOInfo = jwtTokenUtil.getRequestAuthToken();

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(CacheKey.SessionKey.API_REQUEST_ATTR_USER,userSSOInfo);
        return true;
    }


}
