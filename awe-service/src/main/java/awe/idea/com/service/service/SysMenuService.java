package awe.idea.com.service.service;

import awe.idea.com.service.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:32
 */
public interface SysMenuService {
	
	SysMenuEntity queryObject(Long menuId);
	
	List<SysMenuEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysMenuEntity sysMenu);
	
	void update(SysMenuEntity sysMenu);
	
	void delete(Long menuId);
	
	void deleteBatch(Long[] menuIds);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();

	/**
	 * 获取用户菜单列表
	 */
	List<SysMenuEntity> getUserMenuList(Long userId);
	/**
	 * 查询用户的权限列表
	 */
	List<SysMenuEntity> queryUserList(Long userId);
}
