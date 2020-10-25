package awe.idea.com.admin.controller;

import awe.idea.com.common.utils.PageUtils;
import awe.idea.com.common.utils.Query;
import awe.idea.com.common.utils.R;
import awe.idea.com.service.entity.SysLogEntity;
import awe.idea.com.service.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 系统日志
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:30
 */
@RestController
@RequestMapping("sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("syslog:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysLogEntity> sysLogList = sysLogService.queryList(query);
		int total = sysLogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysLogList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("syslog:info")
	public R info(@PathVariable("id") Long id){
		SysLogEntity sysLog = sysLogService.queryObject(id);
		
		return R.ok().put("log", sysLog);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("syslog:save")
	public R save(@RequestBody SysLogEntity sysLog){
		sysLogService.save(sysLog);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("syslog:update")
	public R update(@RequestBody SysLogEntity sysLog){
		sysLogService.update(sysLog);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("syslog:delete")
	public R delete(@RequestBody Long[] ids){
		sysLogService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
