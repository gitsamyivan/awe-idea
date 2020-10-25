package awe.idea.com.admin.aop;

import awe.idea.com.common.annotation.SysLog;
import awe.idea.com.common.utils.HttpContextUtils;
import awe.idea.com.common.utils.IPUtils;
import awe.idea.com.service.entity.SysLogEntity;
import awe.idea.com.service.entity.SysUserEntity;
import awe.idea.com.service.service.SysLogService;
import awe.idea.com.shiro.shiro.ShiroUtils;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年3月8日 上午11:07:35
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;
	
	@Pointcut("@annotation(awe.idea.com.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}
	
	@Before("logPointCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		SysLogEntity sysLog = new SysLogEntity();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述 
			sysLog.setOperation(syslog.value());
		}
		
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		
		//请求的参数
		Object[] args = joinPoint.getArgs();
		if(args.length > 0) {
			String params = JSON.toJSONString(args[0]);
			sysLog.setParams(params);
		}
		
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		
		//用户名
		SysUserEntity userEntity = ShiroUtils.getUserEntity();
		String username = userEntity == null ? "system" : userEntity.getUsername();
		sysLog.setUsername(username);
		
		sysLog.setCreateDate(new Date());
		//保存系统日志
		sysLogService.save(sysLog);
	}
	
}
