package awe.idea.com.web.service.impl;

import awe.idea.com.common.models.api.UserLoginReq;
import awe.idea.com.common.security.AESUtil;
import awe.idea.com.common.utils.HttpClientUtils;
import awe.idea.com.common.utils.R;
import awe.idea.com.service.service.UserService;
import awe.idea.com.web.config.Constants;
import awe.idea.com.web.service.LoginService;
import awe.idea.com.web.sso.ILoginSSOUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserService userService;
    @Resource(name = "loginSSOInstance")
    private ILoginSSOUtil loginSSOUtil;

    /**
     * api项目login调用解密数据
     * @param userLoginReq
     * @return
     * @throws Exception
     */
    public R loginapi(UserLoginReq userLoginReq) throws Exception{
        String url = Constants.API_BASE_URL + "/v1/login";
        String json = mapper.writeValueAsString(userLoginReq);
        String encryptJson = AESUtil.encrypt2hex(json);

        String params = "requestData="+encryptJson;
        String postResult = HttpClientUtils.postParameters(url,params);
        if(Constants.API_ENCRYPT_ENABLE){
            postResult = AESUtil.hex2decrypt(postResult);
        }
        R result = mapper.readValue(postResult.getBytes("utf-8"), new TypeReference<R>(){});
        return result;
    }
}
