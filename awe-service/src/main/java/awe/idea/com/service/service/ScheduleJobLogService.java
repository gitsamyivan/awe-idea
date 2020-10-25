package awe.idea.com.service.service;

import awe.idea.com.service.entity.ScheduleJobLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-07 17:08:27
 */
public interface ScheduleJobLogService {
	
	ScheduleJobLogEntity queryObject(Long logId);
	
	List<ScheduleJobLogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ScheduleJobLogEntity scheduleJobLog);
	
	void update(ScheduleJobLogEntity scheduleJobLog);
	
	void delete(Long logId);
	
	void deleteBatch(Long[] logIds);
}
