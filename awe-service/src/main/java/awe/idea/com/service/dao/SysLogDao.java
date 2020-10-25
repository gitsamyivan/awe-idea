package awe.idea.com.service.dao;

import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.service.entity.SysLogEntity;
import awe.idea.com.service.dao.BaseDao;

/**
 * 系统日志
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:30
 */
@MapperScan
public interface SysLogDao extends BaseDao<SysLogEntity> {
	
}
