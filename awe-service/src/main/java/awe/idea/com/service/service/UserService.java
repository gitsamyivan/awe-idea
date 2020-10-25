package awe.idea.com.service.service;

import awe.idea.com.service.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-07 12:49:25
 */
public interface UserService {
	
	UserEntity queryObject(Long userId);
	
	List<UserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserEntity user);
	
	void update(UserEntity user);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);

	UserEntity queryByUserName(String username);
}
