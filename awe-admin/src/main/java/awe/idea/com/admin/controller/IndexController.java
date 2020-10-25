package awe.idea.com.admin.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController extends AbstractController {
    @RequestMapping("/hello")
    @ResponseBody
    @ApiOperation(value = "/hello",httpMethod = "GET")
    public String hello(){
        return  "hello-awe-admin";
    }
}
