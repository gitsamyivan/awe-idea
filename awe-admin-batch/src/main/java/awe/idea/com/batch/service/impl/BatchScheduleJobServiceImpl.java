package awe.idea.com.batch.service.impl;

import awe.idea.com.batch.utils.ScheduleUtils;
import awe.idea.com.common.utils.Constant;
import awe.idea.com.service.constants.enums.ScheduleStatus;
import awe.idea.com.service.dao.ScheduleJobDao;
import awe.idea.com.service.entity.ScheduleJobEntity;
import awe.idea.com.batch.service.BatchScheduleJobService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("batch_scheduleJobService")
public class BatchScheduleJobServiceImpl implements BatchScheduleJobService {
	@Autowired
	private Scheduler scheduler;  //spring-quartz调度管理器
	@Autowired
	private ScheduleJobDao schedulerJobDao;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJobEntity> scheduleJobList = schedulerJobDao.queryList(new HashMap<String, Object>());
		for(ScheduleJobEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
			//如果不存在，则创建
			if(cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			}else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	@Override
	public ScheduleJobEntity queryObject(Long jobId) {
		return schedulerJobDao.queryObject(jobId);
	}

	@Override
	public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
		return schedulerJobDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return schedulerJobDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(ScheduleJobEntity scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());
		schedulerJobDao.save(scheduleJob);

		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	}

	@Override
	@Transactional
	public void update(ScheduleJobEntity scheduleJob) {
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

		schedulerJobDao.update(scheduleJob);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		}

		//删除数据
		schedulerJobDao.deleteBatch(jobIds);
	}

	@Override
	public int updateBatch(Long[] jobIds, int status){
		Map<String, Object> map = new HashMap<>();
		map.put("list", jobIds);
		map.put("status", status);
		return schedulerJobDao.updateBatch(map);
	}

	@Override
	@Transactional
	public void run(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.run(scheduler,schedulerJobDao.queryObject(jobId));
		}
	}

	@Override
	@Transactional
	public void pause(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.pauseJob(scheduler, jobId);
		}

		updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
	}

	@Override
	@Transactional
	public void resume(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.resumeJob(scheduler, jobId);
		}

		updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
	}
}
