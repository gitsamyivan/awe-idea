package awe.idea.com.gen.dao;

import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.gen.entity.SysConfigEntity;
import awe.idea.com.service.dao.BaseDao;

/**
 * 系统配置信息表
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-25 16:46:51
 */
@MapperScan
public interface SysConfigDao extends BaseDao<SysConfigEntity> {
	
}
