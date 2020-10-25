package awe.idea.com.api.jwt;

import awe.idea.com.api.config.ErrorCode;
import awe.idea.com.api.sso.ILoginSSOUtil;
import awe.idea.com.common.models.ApiUserSSOInfo;
import awe.idea.com.common.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtTokenUtil {
    @Resource(name = "loginSSOInstance")
    private ILoginSSOUtil loginSSOUtil;
    @Autowired
    private HttpServletRequest request;

    public ApiUserSSOInfo getRequestAuthToken() throws Exception{
        //从header中获取token
        String AuthToken = request.getHeader("Authorization");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(AuthToken)){
            AuthToken = request.getParameter("Authorization");
        }
        //token为空
        if(StringUtils.isBlank(AuthToken)){
            throw new RRException("身份凭证不能为空。", ErrorCode.TOKEN_FAIL.getCode());
        }
        JwtToken jwtToken = JwtUtils.getJwtToken(AuthToken);
        String username = jwtToken.getUsername();
        String token = jwtToken.getToken();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(token) ){
            throw new RRException("身份凭证错误,请刷新重试。", ErrorCode.TOKEN_FAIL.getCode());
        }
        ApiUserSSOInfo userSSOInfo = loginSSOUtil.getSsoInfo(username,token);
        if(userSSOInfo == null || userSSOInfo.isExpired()){
            throw new RRException("身份凭证失效，请重新登录。", ErrorCode.TOKEN_FAIL.getCode());
        }
        return userSSOInfo;
    }

}
