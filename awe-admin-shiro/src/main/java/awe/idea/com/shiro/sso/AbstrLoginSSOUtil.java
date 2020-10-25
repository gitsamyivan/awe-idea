package awe.idea.com.shiro.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstrLoginSSOUtil implements ILoginSSOUtil {
    //允许多点登录
    public static Boolean MULTI_LOGIN = false;

    @Value("${awe.idea.sso.redis:false}")
    private Boolean ssoRedisEnable;
    @Autowired
    private LoginSSOUtil loginSSOUtil;
    @Autowired
    private LoginSSORedisUtil loginSSORedisUtil;

    @Bean(name = "loginSSOInstance")
    @Scope("prototype")
    public ILoginSSOUtil loginSSOUtilInstance(){
        if(ssoRedisEnable!= null && ssoRedisEnable){
            return loginSSORedisUtil;
        }else{
            return loginSSOUtil;
        }
    }
  
}
