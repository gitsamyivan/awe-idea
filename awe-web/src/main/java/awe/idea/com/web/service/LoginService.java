package awe.idea.com.web.service;

import awe.idea.com.common.models.api.UserLoginReq;
import awe.idea.com.common.utils.R;
import awe.idea.com.service.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;


public interface LoginService {
    R loginapi(UserLoginReq userLoginReq) throws Exception;
}
