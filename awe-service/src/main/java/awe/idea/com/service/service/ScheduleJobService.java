package awe.idea.com.service.service;

import awe.idea.com.service.entity.ScheduleJobEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-07 17:08:27
 */
public interface ScheduleJobService {
	
	ScheduleJobEntity queryObject(Long jobId);
	
	List<ScheduleJobEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ScheduleJobEntity scheduleJob);
	
	void update(ScheduleJobEntity scheduleJob);
	
	void delete(Long jobId);
	
	void deleteBatch(Long[] jobIds);
}
