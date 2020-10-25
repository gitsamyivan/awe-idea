package awe.idea.com.service.service.impl;

import awe.idea.com.service.constants.enums.MenuType;
import awe.idea.com.service.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import awe.idea.com.service.dao.SysMenuDao;
import awe.idea.com.service.entity.SysMenuEntity;
import awe.idea.com.service.service.SysMenuService;



@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public SysMenuEntity queryObject(Long menuId){
		return sysMenuDao.queryObject(menuId);
	}
	
	@Override
	public List<SysMenuEntity> queryList(Map<String, Object> map){
		return sysMenuDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysMenuDao.queryTotal(map);
	}
	
	@Override
	public void save(SysMenuEntity sysMenu){
		sysMenuDao.save(sysMenu);
	}
	
	@Override
	public void update(SysMenuEntity sysMenu){
		sysMenuDao.update(sysMenu);
	}
	
	@Override
	public void delete(Long menuId){
		sysMenuDao.delete(menuId);
	}
	
	@Override
	public void deleteBatch(Long[] menuIds){
		sysMenuDao.deleteBatch(menuIds);
	}


	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = sysMenuDao.queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}

		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return sysMenuDao.queryNotButtonList();
	}

	@Override
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == 1){
			return getAllMenuList(null);
		}

		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

		for(SysMenuEntity entity : menuList){
			if(entity.getType() == MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}

		return subMenuList;
	}

	@Override
	public List<SysMenuEntity> queryUserList(Long userId) {
		return sysMenuDao.queryUserList(userId);
	}

}
