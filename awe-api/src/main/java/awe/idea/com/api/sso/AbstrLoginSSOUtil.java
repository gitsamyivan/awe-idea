package awe.idea.com.api.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstrLoginSSOUtil implements ILoginSSOUtil {
    //允许多点登录
    public static Boolean MULTI_LOGIN = true;

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
