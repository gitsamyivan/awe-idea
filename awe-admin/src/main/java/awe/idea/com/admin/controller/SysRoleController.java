package awe.idea.com.admin.controller;

import awe.idea.com.common.utils.Constant;
import awe.idea.com.common.utils.PageUtils;
import awe.idea.com.common.utils.Query;
import awe.idea.com.common.utils.R;
import awe.idea.com.common.validators.ValidatorUtils;
import awe.idea.com.service.constants.Constants;
import awe.idea.com.service.entity.SysRoleEntity;
import awe.idea.com.service.service.SysRoleMenuService;
import awe.idea.com.service.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:32
 */
@RestController
@RequestMapping("sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysrole:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysRoleEntity> sysRoleList = sysRoleService.queryList(query);
		int total = sysRoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysRoleList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sysrole:info")
	public R select(){
		Map<String, Object> map = new HashMap<>();

		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constants.SUPER_ADMIN){
			map.put("createUserId", getUserId());
		}
		List<SysRoleEntity> list = sysRoleService.queryList(map);

		return R.ok().put("list", list);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sysrole:info")
	public R info(@PathVariable("roleId") Long roleId){
		SysRoleEntity sysRole = sysRoleService.queryObject(roleId);

		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		sysRole.setMenuIdList(menuIdList);

		return R.ok().put("role", sysRole);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysrole:save")
	public R save(@RequestBody SysRoleEntity sysRole) {
		ValidatorUtils.validateEntity(sysRole);

		sysRole.setCreateUserId(getUserId());
		sysRoleService.save(sysRole);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysrole:update")
	public R update(@RequestBody SysRoleEntity sysRole) {
		ValidatorUtils.validateEntity(sysRole);

		sysRole.setCreateUserId(getUserId());
		sysRoleService.update(sysRole);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysrole:delete")
	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return R.ok();
	}
	
}
