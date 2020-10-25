package awe.idea.com.service.dao;

import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.service.entity.ScheduleJobLogEntity;
import awe.idea.com.service.dao.BaseDao;

/**
 * 定时任务日志
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-07 17:08:27
 */
@MapperScan
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}
