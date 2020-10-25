package awe.idea.com.service.service;

import awe.idea.com.service.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:32
 */
public interface SysRoleService {
	
	SysRoleEntity queryObject(Long roleId);
	
	List<SysRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleEntity sysRole);
	
	void update(SysRoleEntity sysRole);
	
	void delete(Long roleId);
	
	void deleteBatch(Long[] roleIds);
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
