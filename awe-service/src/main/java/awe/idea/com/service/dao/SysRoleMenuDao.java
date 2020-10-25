package awe.idea.com.service.dao;

import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.service.entity.SysRoleMenuEntity;
import awe.idea.com.service.dao.BaseDao;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:32
 */
@MapperScan
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {

    List<Long> queryMenuIdList(Long roleId);
}
