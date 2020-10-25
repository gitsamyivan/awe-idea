package awe.idea.com.batch.controller;

import awe.idea.com.batch.service.BatchScheduleJobService;
import awe.idea.com.common.annotation.SysLog;
import awe.idea.com.common.utils.PageUtils;
import awe.idea.com.common.utils.Query;
import awe.idea.com.common.utils.R;
import awe.idea.com.common.validators.ValidatorUtils;
import awe.idea.com.service.entity.ScheduleJobEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月28日 下午2:16:40
 */
@RestController
@RequestMapping("/sys/schedulejob")
public class ScheduleJobController  extends AbstractController {
	@Autowired
	private BatchScheduleJobService scheduleJobService;
	
	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("schedulejob:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(query);
		int total = scheduleJobService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	@RequiresPermissions("schedulejob:info")
	public R info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);
		
		return R.ok().put("schedule", schedule);
	}
	
	/**
	 * 保存定时任务
	 */
	@SysLog("保存定时任务")
	@RequestMapping("/save")
	@RequiresPermissions("schedulejob:save")
	public R save(@RequestBody ScheduleJobEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);
		
		scheduleJobService.save(scheduleJob);
		
		return R.ok();
	}
	
	/**
	 * 修改定时任务
	 */
	@SysLog("修改定时任务")
	@RequestMapping("/update")
	@RequiresPermissions("schedulejob:update")
	public R update(@RequestBody ScheduleJobEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);
				
		scheduleJobService.update(scheduleJob);
		
		return R.ok();
	}
	
	/**
	 * 删除定时任务
	 */
	@SysLog("删除定时任务")
	@RequestMapping("/delete")
	@RequiresPermissions("schedulejob:delete")
	public R delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 立即执行任务
	 */
	@SysLog("立即执行任务")
	@RequestMapping("/run")
	@RequiresPermissions("schedulejob:run")
	public R run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 暂停定时任务
	 */
	@SysLog("暂停定时任务")
	@RequestMapping("/pause")
	@RequiresPermissions("schedulejob:pause")
	public R pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 恢复定时任务
	 */
	@SysLog("恢复定时任务")
	@RequestMapping("/resume")
	@RequiresPermissions("schedulejob:resume")
	public R resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);
		
		return R.ok();
	}

}
