package awe.idea.com.service.dao;

import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.service.entity.SysRoleEntity;
import awe.idea.com.service.dao.BaseDao;

import java.util.List;

/**
 * 角色
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:32
 */
@MapperScan
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);
}
