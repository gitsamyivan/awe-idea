package awe.idea.com.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import awe.idea.com.service.dao.ScheduleJobDao;
import awe.idea.com.service.entity.ScheduleJobEntity;
import awe.idea.com.service.service.ScheduleJobService;

import javax.annotation.PostConstruct;


@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {
	@Autowired
	private ScheduleJobDao scheduleJobDao;

	@Override
	public ScheduleJobEntity queryObject(Long jobId){
		return scheduleJobDao.queryObject(jobId);
	}
	
	@Override
	public List<ScheduleJobEntity> queryList(Map<String, Object> map){
		return scheduleJobDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return scheduleJobDao.queryTotal(map);
	}
	
	@Override
	public void save(ScheduleJobEntity scheduleJob){
		scheduleJobDao.save(scheduleJob);
	}
	
	@Override
	public void update(ScheduleJobEntity scheduleJob){
		scheduleJobDao.update(scheduleJob);
	}
	
	@Override
	public void delete(Long jobId){
		scheduleJobDao.delete(jobId);
	}
	
	@Override
	public void deleteBatch(Long[] jobIds){
		scheduleJobDao.deleteBatch(jobIds);
	}
	
}
