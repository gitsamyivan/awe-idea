package awe.idea.com.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import awe.idea.com.service.dao.SysUserRoleDao;
import awe.idea.com.service.entity.SysUserRoleEntity;
import awe.idea.com.service.service.SysUserRoleService;



@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public SysUserRoleEntity queryObject(Long id){
		return sysUserRoleDao.queryObject(id);
	}
	
	@Override
	public List<SysUserRoleEntity> queryList(Map<String, Object> map){
		return sysUserRoleDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysUserRoleDao.queryTotal(map);
	}
	
	@Override
	public void save(SysUserRoleEntity sysUserRole){
		sysUserRoleDao.save(sysUserRole);
	}
	
	@Override
	public void update(SysUserRoleEntity sysUserRole){
		sysUserRoleDao.update(sysUserRole);
	}
	
	@Override
	public void delete(Long id){
		sysUserRoleDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysUserRoleDao.deleteBatch(ids);
	}

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		if(roleIdList.size() == 0){
			return ;
		}

		//先删除用户与角色关系
		sysUserRoleDao.delete(userId);

		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("roleIdList", roleIdList);
		sysUserRoleDao.save(map);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleDao.queryRoleIdList(userId);
	}
}
