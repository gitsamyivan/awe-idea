package awe.idea.com.service.service.impl;

import awe.idea.com.common.utils.RRException;
import awe.idea.com.service.constants.Constants;
import awe.idea.com.service.service.SysRoleMenuService;
import awe.idea.com.service.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import awe.idea.com.service.dao.SysRoleDao;
import awe.idea.com.service.entity.SysRoleEntity;
import awe.idea.com.service.service.SysRoleService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public SysRoleEntity queryObject(Long roleId){
		return sysRoleDao.queryObject(roleId);
	}
	
	@Override
	public List<SysRoleEntity> queryList(Map<String, Object> map){
		return sysRoleDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysRoleDao.queryTotal(map);
	}

	@Transactional
	@Override
	public void save(SysRoleEntity sysRole){
		sysRole.setCreateTime(new Date());
		sysRoleDao.save(sysRole);

		//检查权限是否越权
		checkPrems(sysRole);

		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(), sysRole.getMenuIdList());
	}

	@Transactional
	@Override
	public void update(SysRoleEntity sysRole){
		sysRoleDao.update(sysRole);

		//检查权限是否越权
		checkPrems(sysRole);

		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(), sysRole.getMenuIdList());
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleEntity role){
		//如果不是超级管理员，则需要判断角色的权限是否超自己的权限
		if(role.getCreateUserId() == Constants.SUPER_ADMIN){
			return ;
		}

		//查询用户所拥有的菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());

		//判断是否越权
		if(!menuIdList.containsAll(role.getMenuIdList())){
			throw new RRException("新增角色的权限，已超出你的权限范围");
		}
	}
	
	@Override
	public void delete(Long roleId){
		sysRoleDao.delete(roleId);
	}
	
	@Override
	public void deleteBatch(Long[] roleIds){
		sysRoleDao.deleteBatch(roleIds);
	}

	@Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return sysRoleDao.queryRoleIdList(createUserId);
	}
}
