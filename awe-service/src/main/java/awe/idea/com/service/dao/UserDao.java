package awe.idea.com.service.dao;

import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.service.entity.UserEntity;
import awe.idea.com.service.dao.BaseDao;

/**
 * 用户
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-07 12:49:25
 */
@MapperScan
public interface UserDao extends BaseDao<UserEntity> {

    UserEntity queryByUserName(String username);
}
