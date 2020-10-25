package awe.idea.com.admin.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import awe.idea.com.service.entity.SysConfigEntity;
import awe.idea.com.service.service.SysConfigService;
import awe.idea.com.common.utils.R;
import awe.idea.com.common.utils.PageUtils;
import awe.idea.com.common.utils.Query;


/**
 * 系统配置信息表
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-25 16:48:50
 */
@RestController
@RequestMapping("sys/config")
public class SysConfigController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysconfig:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysConfigEntity> sysConfigList = sysConfigService.queryList(query);
		int total = sysConfigService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysConfigList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysconfig:info")
	public R info(@PathVariable("id") Long id){
		SysConfigEntity sysConfig = sysConfigService.queryObject(id);
		
		return R.ok().put("config", sysConfig);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysconfig:save")
	public R save(@RequestBody SysConfigEntity sysConfig){
		sysConfigService.save(sysConfig);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysconfig:update")
	public R update(@RequestBody SysConfigEntity sysConfig){
		sysConfigService.update(sysConfig);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysconfig:delete")
	public R delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
