package awe.idea.com.api.controller;

import awe.idea.com.api.annotation.EncryptParam;
import awe.idea.com.api.jwt.JwtToken;
import awe.idea.com.api.models.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController extends AbstractController{
    @RequestMapping("/hello")
    public String hello(){
        return  "hello-awe-api";
    }

    @RequestMapping("/json")
    @ResponseBody
    public String encrypt(String reqParam){
        return reqParam;
    }

    @RequestMapping("/encrydata")
    @ResponseBody
    public R encrydata(@EncryptParam String username,@EncryptParam String password,String client){
        Map<String,Object> data =new HashMap<>();
        data.put("username",username);
        data.put("password",password);
        data.put("client",client);
        return R.ok().put("data",data);
    }

    @RequestMapping("/encrydata2")
    @ResponseBody
    public R encrydata2(@EncryptParam String baseParam){
        Map<String,Object> data =new HashMap<>();
        data.put("baseParam",baseParam);
        return R.ok().put("data",data);
    }

    @RequestMapping("/encrydata3")
    @ResponseBody
    public R demo(@EncryptParam String baseParam) throws Exception{
        JwtToken param = readValueFromJson(baseParam,JwtToken.class);
        return R.ok().put("data",param);
    }
}
