package awe.idea.com.web.controller;

import awe.idea.com.common.utils.R;
import awe.idea.com.web.config.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/hello.json")
    public String hello(){
        return  "hello-awe-web";
    }

    @RequestMapping("/hello.html")
    public String hello1(){
        return  "hello-awe-web";
    }

    @RequestMapping("/hello.xml")
    public String hello2(){
        return  "<xml>hello-awe-web</xml>";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    //切换输出结果解密
    @RequestMapping("/encryswith")
    public R encryswithOn(){
        Constants.API_ENCRYPT_ENABLE = !Constants.API_ENCRYPT_ENABLE;
        return R.ok().put("data",Constants.API_ENCRYPT_ENABLE);
    }

}
