package awe.idea.com.api.controller;

import awe.idea.com.api.config.Constants;
import awe.idea.com.api.models.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping(value = "/index",method = {RequestMethod.GET,RequestMethod.POST})
    public String index(){
        return  "index ok";
    }

    //切换输入参数加密
    @RequestMapping("/inswith")
    public R inswithOn(){
        Constants.INPUT_ENCRYPT_ENABLE = !Constants.INPUT_ENCRYPT_ENABLE;
        return R.ok().put("data",Constants.INPUT_ENCRYPT_ENABLE);
    }
    //切换输出结果加密
    @RequestMapping("/outswith")
    public R outswithOn(){
        Constants.OUT_ENCRYPT_ENABLE = !Constants.OUT_ENCRYPT_ENABLE;
        return R.ok().put("data",Constants.OUT_ENCRYPT_ENABLE);
    }
}
