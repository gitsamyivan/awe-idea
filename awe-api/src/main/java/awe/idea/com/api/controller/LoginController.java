package awe.idea.com.api.controller;

import awe.idea.com.api.annotation.EncryptParam;
import awe.idea.com.api.annotation.IgnoreAuth;
import awe.idea.com.api.utils.CaptchaUtil;
import awe.idea.com.api.config.ErrorCode;
import awe.idea.com.api.jwt.JwtToken;
import awe.idea.com.api.jwt.JwtUtils;
import awe.idea.com.common.models.api.UserLoginReq;
import awe.idea.com.api.service.LoginService;
import awe.idea.com.common.models.ApiUserSSOInfo;
import awe.idea.com.api.models.R;
import awe.idea.com.common.utils.RRException;
import awe.idea.com.common.validators.ValidatorUtils;
import awe.idea.com.service.entity.UserEntity;
import awe.idea.com.service.service.UserService;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录相关
 */
@Api(tags ="登录相关")
@RestController
@RequestMapping("/v1")
public class LoginController extends AbstractController{
	@Autowired
	private LoginService loginService;
	@Autowired
	private Producer producer;
	@Autowired
	private CaptchaUtil captchaUtil;
	@Autowired
	private UserService userService;

	@IgnoreAuth
	@ApiOperation(value="验证码")
	@RequestMapping("captcha.jpg")
	public void captcha(@EncryptParam String username,HttpServletResponse response)throws ServletException, IOException {
		if(StringUtils.isBlank(username)){
			throw new RRException("获取验证码错误",ErrorCode.LOGIN_CAPTCHA_FAIL.getCode());
		}
		UserEntity user = userService.queryByUserName(username);
		if(user == null){
			throw new RRException("获取验证码错误",ErrorCode.LOGIN_CAPTCHA_FAIL.getCode());
		}
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存验证码
		captchaUtil.setCaptcha(username,text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}

	@IgnoreAuth
	@ApiOperation(value="校验验证码")
	@RequestMapping("verifyCaptcha")
	public R verifyCaptcha(@EncryptParam String username,@EncryptParam String captcha){
		if(StringUtils.isBlank(username) || StringUtils.isBlank(captcha)){
			throw new RRException("参数错误，请检查",ErrorCode.LOGIN_CAPTCHA_FAIL.getCode());
		}
		String captchaCode = captchaUtil.getCaptcha(username);
		if(!StringUtils.equalsIgnoreCase(captcha,captchaCode)){
			throw new RRException("验证码错误,请刷新重试。",ErrorCode.LOGIN_CAPTCHA_FAIL.getCode());
		}
		captchaUtil.removeCaptcha(username);
		return R.ok("校验成功");
	}

	/**
	 * 登录
	 */
	@IgnoreAuth
	@ApiOperation(value="登录")
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public R login(@EncryptParam String requestData)throws Exception {
		Map<String,Object> dataMap = new HashMap<>();
		try{
			UserLoginReq userLoginReq = readValueFromJson(requestData,UserLoginReq.class);
			ValidatorUtils.validateEntity(userLoginReq);

			UserEntity user = loginService.login(userLoginReq);
			JwtToken userToken = JwtToken.builder()
					.platform(userLoginReq.getPlatform())
					.client(userLoginReq.getClient())
					.version(userLoginReq.getVersion())
					.username(user.getUsername())
					.token(user.getToken())
					.build();
			String jwtToken = JwtUtils.signJwtToken(userToken);
			dataMap.put("token",jwtToken);
		}catch (UnknownAccountException | LockedAccountException | IncorrectCredentialsException e) {
			return R.error(ErrorCode.LOGIN_FAIL.getCode(),e.getMessage());
		}catch (RRException e){
			return R.error(e.getCode(),e.getMsg());
		}catch (AuthenticationException e) {
			return R.error(ErrorCode.LOGIN_FAIL.getCode(),"账户验证失败");
		}catch (Exception e){
			return R.error(ErrorCode.LOGIN_FAIL.getCode(),"系统错误");
		}
		return R.ok().put("data",dataMap);
	}
	
	/**
	 * 退出
	 */
	@ApiOperation(value="登出")
	@RequestMapping(value = "/logout",  method = {RequestMethod.GET,RequestMethod.POST})
	public R logout() {
		ApiUserSSOInfo apiUserSSOInfo = getRequestAttrUserSSO();
		loginSSOUtil.removeSsoInfo(apiUserSSOInfo.getUsername(),apiUserSSOInfo.getToken());
		return R.ok();
	}
	
}
