package awe.idea.com.batch.service;

import awe.idea.com.service.entity.ScheduleJobEntity;

import java.util.List;
import java.util.Map;

public interface BatchScheduleJobService {
    ScheduleJobEntity queryObject(Long jobId);

    List<ScheduleJobEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    /**
     * 保存定时任务
     */
    void save(ScheduleJobEntity scheduleJob);

    /**
     * 更新定时任务
     */
    void update(ScheduleJobEntity scheduleJob);

    /**
     * 批量删除定时任务
     */
    void deleteBatch(Long[] jobIds);

    /**
     * 批量更新定时任务状态
     */
    int updateBatch(Long[] jobIds, int status);

    /**
     * 立即执行
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     */
    void resume(Long[] jobIds);
}