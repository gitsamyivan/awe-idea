package awe.idea.com.service.service;

import awe.idea.com.service.entity.SysUserRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:33
 */
public interface SysUserRoleService {
	
	SysUserRoleEntity queryObject(Long id);
	
	List<SysUserRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysUserRoleEntity sysUserRole);
	
	void update(SysUserRoleEntity sysUserRole);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	void saveOrUpdate(Long userId, List<Long> roleIdList);

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
}
