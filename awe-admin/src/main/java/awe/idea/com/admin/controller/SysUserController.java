package awe.idea.com.admin.controller;

import awe.idea.com.common.validators.ValidatorUtils;
import awe.idea.com.common.validators.group.AddGroup;
import awe.idea.com.common.validators.group.UpdateGroup;
import awe.idea.com.service.models.OptionVo;
import awe.idea.com.common.annotation.SysLog;
import awe.idea.com.common.utils.PageUtils;
import awe.idea.com.common.utils.Query;
import awe.idea.com.common.utils.R;
import awe.idea.com.common.validators.Assert;
import awe.idea.com.service.entity.SysUserEntity;
import awe.idea.com.service.service.AccountService;
import awe.idea.com.service.service.SysUserRoleService;
import awe.idea.com.service.service.SysUserService;
import awe.idea.com.shiro.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:33
 */
@RestController
@RequestMapping("sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysuser:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysUserEntity> sysUserList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	@RequestMapping("/commercelist")
	@RequiresPermissions("sysuser:list")
	public R getCommerceList(){
		OptionVo noneOption = new OptionVo();
		noneOption.setValue("0");
		noneOption.setShowValue("无");
		List<OptionVo> list = accountService.getActiveAccountOption();
		list.add(0,noneOption);
		return R.ok().put("list", list);
	}

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}

	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");

		//sha256加密
		password = new Sha256Hash(password).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword).toHex();

		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(count == 0){
			return R.error("原密码不正确");
		}

		//退出
		ShiroUtils.logout();

		return R.ok();
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sysuser:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity sysUser = sysUserService.queryObject(userId);
		sysUser.setAccountAmount(new BigDecimal(0));
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		sysUser.setRoleIdList(roleIdList);

		return R.ok().put("user", sysUser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysuser:save")
	public R save(@RequestBody SysUserEntity sysUser){
		ValidatorUtils.validateEntity(sysUser, AddGroup.class);

		sysUser.setCreateUserId(getUserId());
		sysUserService.save(sysUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysuser:update")
	public R update(@RequestBody SysUserEntity sysUser){
		ValidatorUtils.validateEntity(sysUser, UpdateGroup.class);

		sysUser.setCreateUserId(getUserId());
		sysUserService.update(sysUser);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysuser:delete")
	public R delete(@RequestBody Long[] userIds){
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
