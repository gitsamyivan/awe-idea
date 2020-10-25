package awe.idea.com.api.resolver;

import awe.idea.com.api.annotation.EncryptParam;
import awe.idea.com.api.config.Constants;
import awe.idea.com.api.config.ErrorCode;
import awe.idea.com.common.security.AESUtil;
import awe.idea.com.common.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 22:02
 */
@Component
public class EncryptParamArgumentResolver implements HandlerMethodArgumentResolver {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return  parameter.hasParameterAnnotation(EncryptParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        Class<?> parameterType = parameter.getParameterType();
        if(parameterType.equals(String.class)){
            ServletWebRequest servletWebRequest = (ServletWebRequest)request;
            String paramName = parameter.getParameterName();
            String reqParam = servletWebRequest.getParameter(paramName);
            if(Constants.INPUT_ENCRYPT_ENABLE) {
                if(StringUtils.isBlank(reqParam)){
                    return reqParam;
                }else {
                    try {
                        reqParam = AESUtil.hex2decrypt(reqParam);
                    } catch (Exception e) {
                        logger.error("解密数据错误:{}",reqParam,e);
                        throw new RRException("数据错误，请刷新重试。", ErrorCode.DATAERROR.getCode());
                    }
                }
            }
            return reqParam;
        }
        return null;
    }
}
