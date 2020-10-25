package awe.idea.com.admin.controller;

import awe.idea.com.common.annotation.SysLog;
import awe.idea.com.common.utils.PageUtils;
import awe.idea.com.common.utils.Query;
import awe.idea.com.common.utils.R;
import awe.idea.com.common.validators.ValidatorUtils;
import awe.idea.com.service.entity.ScheduleJobEntity;
import awe.idea.com.service.service.ScheduleJobService;
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
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;
	
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

}
