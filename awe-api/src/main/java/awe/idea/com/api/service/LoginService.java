package awe.idea.com.api.service;

import awe.idea.com.common.models.api.UserLoginReq;
import awe.idea.com.service.entity.UserEntity;

public interface LoginService {
    UserEntity login(UserLoginReq userLoginReq) throws Exception;

    UserEntity login(String username, String password,String client) throws Exception;

}
