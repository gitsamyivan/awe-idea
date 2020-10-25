package awe.idea.com.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import awe.idea.com.service.dao.SysConfigDao;
import awe.idea.com.service.entity.SysConfigEntity;
import awe.idea.com.service.service.SysConfigService;



@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {
	@Autowired
	private SysConfigDao sysConfigDao;
	
	@Override
	public SysConfigEntity queryObject(Long id){
		return sysConfigDao.queryObject(id);
	}
	
	@Override
	public List<SysConfigEntity> queryList(Map<String, Object> map){
		return sysConfigDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysConfigDao.queryTotal(map);
	}
	
	@Override
	public void save(SysConfigEntity sysConfig){
		sysConfigDao.save(sysConfig);
	}
	
	@Override
	public void update(SysConfigEntity sysConfig){
		sysConfigDao.update(sysConfig);
	}
	
	@Override
	public void delete(Long id){
		sysConfigDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysConfigDao.deleteBatch(ids);
	}
	
}
