package awe.idea.com.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import awe.idea.com.service.dao.ScheduleJobLogDao;
import awe.idea.com.service.entity.ScheduleJobLogEntity;
import awe.idea.com.service.service.ScheduleJobLogService;



@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
	@Autowired
	private ScheduleJobLogDao scheduleJobLogDao;
	
	@Override
	public ScheduleJobLogEntity queryObject(Long logId){
		return scheduleJobLogDao.queryObject(logId);
	}
	
	@Override
	public List<ScheduleJobLogEntity> queryList(Map<String, Object> map){
		return scheduleJobLogDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return scheduleJobLogDao.queryTotal(map);
	}
	
	@Override
	public void save(ScheduleJobLogEntity scheduleJobLog){
		scheduleJobLogDao.save(scheduleJobLog);
	}
	
	@Override
	public void update(ScheduleJobLogEntity scheduleJobLog){
		scheduleJobLogDao.update(scheduleJobLog);
	}
	
	@Override
	public void delete(Long logId){
		scheduleJobLogDao.delete(logId);
	}
	
	@Override
	public void deleteBatch(Long[] logIds){
		scheduleJobLogDao.deleteBatch(logIds);
	}
	
}
