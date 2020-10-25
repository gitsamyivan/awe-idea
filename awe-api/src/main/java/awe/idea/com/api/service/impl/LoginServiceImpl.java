package awe.idea.com.api.service.impl;

import awe.idea.com.common.models.api.UserLoginReq;
import awe.idea.com.api.service.LoginService;
import awe.idea.com.api.sso.ILoginSSOUtil;
import awe.idea.com.common.models.ApiUserSSOInfo;
import awe.idea.com.common.utils.GenerateUtil;
import awe.idea.com.service.entity.UserEntity;
import awe.idea.com.service.service.UserService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;
    @Resource(name = "loginSSOInstance")
    private ILoginSSOUtil loginSSOUtil;

    public UserEntity login(UserLoginReq userLoginReq) throws Exception{
        String username = userLoginReq.getUsername();
        String password = userLoginReq.getPassword();
        String client = userLoginReq.getClient();
        String platform = userLoginReq.getPlatform();
        String version  = userLoginReq.getVersion();

        password = new Sha256Hash(password).toHex();
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

        if(loginSSOUtil.checkMultiLogin(user.getUsername())){
            throw new IncorrectCredentialsException("账号不允许多点登录,请联系管理员");
        }

        String userToken = GenerateUtil.randomUUID();
        user.setToken(userToken);

        ApiUserSSOInfo userSSOInfo = ApiUserSSOInfo.builder()
                .platform(platform)
                .client(client)
                .version(version)
                .username(user.getUsername())
                .mobile(user.getMobile())
                .token(user.getToken())
                .build();
        loginSSOUtil.setSsoToken(userSSOInfo);
        return user;
    }


    public UserEntity login(String username,String password,String client) throws Exception{
        password = new Sha256Hash(password).toHex();

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

        if(loginSSOUtil.checkMultiLogin(user.getUsername())){
            throw new IncorrectCredentialsException("账号不允许多点登录,请联系管理员");
        }

        String userToken = GenerateUtil.randomUUID();
        user.setToken(userToken);

        ApiUserSSOInfo userSSOInfo = ApiUserSSOInfo.builder()
                .client(client)
                .username(user.getUsername())
                .mobile(user.getMobile())
                .token(user.getToken())
                .build();
        loginSSOUtil.setSsoToken(userSSOInfo);
        return user;
    }
}
