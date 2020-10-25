package awe.idea.com.service.dao;

import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.service.entity.SysUserRoleEntity;
import awe.idea.com.service.dao.BaseDao;

import java.util.List;

/**
 * 用户与角色对应关系
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:33
 */
@MapperScan
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);
}
