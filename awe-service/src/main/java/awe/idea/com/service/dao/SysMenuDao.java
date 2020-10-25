package awe.idea.com.service.dao;

import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.service.entity.SysMenuEntity;
import awe.idea.com.service.dao.BaseDao;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:32
 */
@MapperScan
public interface SysMenuDao extends BaseDao<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 查询用户的权限列表
     */
    List<SysMenuEntity> queryUserList(Long userId);
}
