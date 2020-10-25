package awe.idea.com.service.service;

import awe.idea.com.service.entity.SysRoleMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:32
 */
public interface SysRoleMenuService {
	
	SysRoleMenuEntity queryObject(Long id);
	
	List<SysRoleMenuEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleMenuEntity sysRoleMenu);
	
	void update(SysRoleMenuEntity sysRoleMenu);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

    void saveOrUpdate(Long roleId, List<Long> menuIdList);
}
