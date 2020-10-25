package awe.idea.com.api.controller;


import awe.idea.com.api.jwt.JwtToken;
import awe.idea.com.common.models.ApiUserSSOInfo;
import awe.idea.com.api.models.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags ="用户相关")
@RestController
@RequestMapping("/v1")
public class UserController extends AbstractController{

    @RequestMapping("/getUserInfo")
    public R getUserInfo()throws Exception{
        ApiUserSSOInfo userSSOInfo = getRequestAttrUserSSO();
        return R.ok().put("data",userSSOInfo);
    }

    @RequestMapping("/getUserInfo2")
    public R getUserInfo2()throws Exception{
        ApiUserSSOInfo userSSOInfo = getRequestAttrUserSSO();
        return R.ok().put("data",userSSOInfo);
    }

    @RequestMapping("/getJwtToken")
    public R getJwtToken() throws Exception{
        JwtToken userSSOInfo = getUserJwtToken();
        return R.ok().put("data",userSSOInfo);
    }



}
