package awe.idea.com.api.resolver;

import awe.idea.com.api.config.Constants;
import awe.idea.com.api.config.ErrorCode;
import awe.idea.com.common.security.AESUtil;
import awe.idea.com.common.utils.RRException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EncryptJackson2HttpConverter extends MappingJackson2HttpMessageConverter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public EncryptJackson2HttpConverter(){
    }

    public EncryptJackson2HttpConverter(ObjectMapper objectMapper) {
        super(objectMapper);
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        super.setSupportedMediaTypes(supportedMediaTypes);
    }

    /**
     * 输出json加密
     * @param object
     * @param type
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            String json = objectMapper.writeValueAsString(object);
            //加密
            if(Constants.OUT_ENCRYPT_ENABLE) {
                json = AESUtil.encrypt2hex(json);
            }
            //设置contenttype
            outputMessage.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            outputMessage.getBody().write(json.getBytes("utf-8"));
        } catch (Exception e) {
            logger.error("EncryptJackson2HttpConverter error",e);
            throw new RRException("系统错误,请稍后再试。", ErrorCode.ERROR.getCode());
        }
    }
}
