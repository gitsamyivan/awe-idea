package awe.idea.com.gen.service;

import awe.idea.com.gen.entity.SysConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息表
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-25 16:46:51
 */
public interface SysConfigService {
	
	SysConfigEntity queryObject(Long id);
	
	List<SysConfigEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysConfigEntity sysConfig);
	
	void update(SysConfigEntity sysConfig);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
