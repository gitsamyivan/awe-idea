package awe.idea.com.service.dao;

import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.service.entity.ScheduleJobEntity;
import awe.idea.com.service.dao.BaseDao;

import java.util.Map;

/**
 * 定时任务
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-07 17:08:27
 */
@MapperScan
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);
}
