package awe.idea.com.api.exception;

import awe.idea.com.api.config.Constants;
import awe.idea.com.api.config.ErrorCode;
import awe.idea.com.common.security.AESUtil;
import awe.idea.com.api.models.R;
import awe.idea.com.common.utils.RRException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:16:19
 */
@Component
public class RRExceptionHandler implements HandlerExceptionResolver {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper mapper;

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		R r = new R();
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			
			if (ex instanceof RRException) {
				r.put("code", ((RRException) ex).getCode());
				r.put("msg", ((RRException) ex).getMessage());
			}else{
				r = R.error();
				r.put("code", ErrorCode.ERROR.getCode());
				r.put("msg", ErrorCode.ERROR.getMsg());
			}
			
			//记录异常日志
			logger.error(ex.getMessage(), ex);

			String json = mapper.writeValueAsString(r);
			//加密
			if(Constants.OUT_ENCRYPT_ENABLE) {
				json = AESUtil.encrypt2hex(json);
			}
			response.getWriter().print(json);
		} catch (Exception e) {
			logger.error("RRExceptionHandler 异常处理失败", e);
		}
		return new ModelAndView();
	}
}
