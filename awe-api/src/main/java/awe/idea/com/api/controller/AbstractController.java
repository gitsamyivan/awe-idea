package awe.idea.com.api.controller;

import awe.idea.com.api.config.ErrorCode;
import awe.idea.com.api.jwt.JwtToken;
import awe.idea.com.api.sso.ILoginSSOUtil;
import awe.idea.com.api.jwt.JwtUtils;
import awe.idea.com.common.models.ApiUserSSOInfo;
import awe.idea.com.common.utils.CacheKey;
import awe.idea.com.common.utils.RRException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected HttpServletRequest servletRequest;
    @Resource(name = "loginSSOInstance")
    protected ILoginSSOUtil loginSSOUtil;

    protected <T> T readValueFromJson(String json,Class<T> clazz) throws Exception{
        try {
            return mapper.readValue(json,clazz);
        }catch (Exception e){
            logger.error("readValueFromJson error.",e);
            throw new RRException("参数错误,请稍后再试。", ErrorCode.FAIL.getCode());
        }
    }

    protected JwtToken getUserJwtToken()throws Exception{
        String AuthToken = servletRequest.getHeader("Authorization");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(AuthToken)){
            AuthToken = servletRequest.getParameter("Authorization");
        }

        //token为空
        if(StringUtils.isBlank(AuthToken)){
            throw new RRException("身份凭证不能为空。", ErrorCode.TOKEN_FAIL.getCode());
        }
        JwtToken jwtToken = JwtUtils.getJwtToken(AuthToken);
        return jwtToken;
    }

    protected ApiUserSSOInfo getUserSSOInfo()throws Exception{
        JwtToken jwtToken = getUserJwtToken();
        String username = jwtToken.getUsername();
        String token = jwtToken.getToken();

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(token) ){
            throw new RRException("身份凭证错误。", ErrorCode.TOKEN_FAIL.getCode());
        }
        ApiUserSSOInfo userSSOInfo = loginSSOUtil.getSsoInfo(username,token);
        if(userSSOInfo == null || userSSOInfo.isExpired()){
            throw new RRException("token失效，请重新登录。", ErrorCode.TOKEN_FAIL.getCode());
        }
        return userSSOInfo;
    }

    protected ApiUserSSOInfo getRequestAttrUserSSO(){
       return (ApiUserSSOInfo) servletRequest.getAttribute(CacheKey.SessionKey.API_REQUEST_ATTR_USER);
    }

}
