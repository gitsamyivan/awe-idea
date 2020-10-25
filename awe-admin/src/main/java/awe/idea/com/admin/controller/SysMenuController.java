package awe.idea.com.admin.controller;

import awe.idea.com.common.utils.*;
import awe.idea.com.service.constants.Constants;
import awe.idea.com.service.constants.enums.MenuType;
import awe.idea.com.service.entity.SysMenuEntity;
import awe.idea.com.service.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 菜单管理
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:32
 */
@RestController
@RequestMapping("sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysmenu:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysMenuEntity> sysMenuList = sysMenuService.queryList(query);
		int total = sysMenuService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysMenuList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sysmenu:select")
	public R select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);

		return R.ok().put("menuList", menuList);
	}

	/**
	 * 角色授权菜单
	 */
	@RequestMapping("/perms")
	@RequiresPermissions("sysmenu:perms")
	public R perms(){
		//查询列表数据
		List<SysMenuEntity> menuList = null;

		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() == Constants.SUPER_ADMIN){
			menuList = sysMenuService.queryList(new HashMap<String, Object>());
		}else{
			menuList = sysMenuService.queryUserList(getUserId());
		}

		return R.ok().put("menuList", menuList);
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("sysmenu:info")
	public R info(@PathVariable("menuId") Long menuId){
		SysMenuEntity sysMenu = sysMenuService.queryObject(menuId);
		
		return R.ok().put("menu", sysMenu);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysmenu:save")
	public R save(@RequestBody SysMenuEntity sysMenu){
		sysMenuService.save(sysMenu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysmenu:update")
	public R update(@RequestBody SysMenuEntity sysMenu){
		sysMenuService.update(sysMenu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysmenu:delete")
	public R delete(@RequestBody Long[] menuIds){
		sysMenuService.deleteBatch(menuIds);
		
		return R.ok();
	}


	/**
	 * 用户菜单列表
	 */
	@RequestMapping("/user")
	public R user(){
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());

		return R.ok().put("menuList", menuList);
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}

		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}

		//菜单
		if(menu.getType() == MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}

		//上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.queryObject(menu.getParentId());
			parentType = parentMenu.getType();
		}

		//目录、菜单
		if(menu.getType() == MenuType.CATALOG.getValue() ||
				menu.getType() == MenuType.MENU.getValue()){
			if(parentType != MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}

		//按钮
		if(menu.getType() == MenuType.BUTTON.getValue()){
			if(parentType != MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
