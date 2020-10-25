package awe.idea.com.service.service;

import awe.idea.com.service.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:33
 */
public interface SysUserService {
	
	SysUserEntity queryObject(Long userId);
	
	List<SysUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysUserEntity sysUser);
	
	void update(SysUserEntity sysUser);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	int updatePassword(Long userId, String password, String newPassword);
}
