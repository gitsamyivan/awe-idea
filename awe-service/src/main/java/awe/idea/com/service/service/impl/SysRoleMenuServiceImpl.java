package awe.idea.com.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import awe.idea.com.service.dao.SysRoleMenuDao;
import awe.idea.com.service.entity.SysRoleMenuEntity;
import awe.idea.com.service.service.SysRoleMenuService;
import org.springframework.transaction.annotation.Transactional;


@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Override
	public SysRoleMenuEntity queryObject(Long id){
		return sysRoleMenuDao.queryObject(id);
	}
	
	@Override
	public List<SysRoleMenuEntity> queryList(Map<String, Object> map){
		return sysRoleMenuDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysRoleMenuDao.queryTotal(map);
	}
	
	@Override
	public void save(SysRoleMenuEntity sysRoleMenu){
		sysRoleMenuDao.save(sysRoleMenu);
	}
	
	@Override
	public void update(SysRoleMenuEntity sysRoleMenu){
		sysRoleMenuDao.update(sysRoleMenu);
	}
	
	@Override
	public void delete(Long id){
		sysRoleMenuDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysRoleMenuDao.deleteBatch(ids);
	}

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return sysRoleMenuDao.queryMenuIdList(roleId);
	}

	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		if(menuIdList.size() == 0){
			return ;
		}
		//先删除角色与菜单关系
		sysRoleMenuDao.delete(roleId);

		//保存角色与菜单关系
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("menuIdList", menuIdList);
		sysRoleMenuDao.save(map);
	}
	
}
