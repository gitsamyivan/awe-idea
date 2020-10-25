package awe.idea.com.service.dao;

import awe.idea.com.service.models.OptionVo;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import awe.idea.com.service.entity.AccountEntity;
import awe.idea.com.service.dao.BaseDao;

import java.util.List;

/**
 * 
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-07 17:08:27
 */
@MapperScan
public interface AccountDao extends BaseDao<AccountEntity> {

    /**
     * 根据用户获取账户列表信息
     * @param userIdList
     * @return
     */
    List<AccountEntity> getAccountListByUserIdList(@Param("userIdList")List<Long> userIdList);

    /**
     * 获取所有二级商务用户
     * @return
     */
    List<AccountEntity> getAllCommerceAccount();

    /**
     * 获取当前在职的所有二级商务账户
     * @return
     */
    List<AccountEntity> getAllActiveCommerceAccount();

    /**
     * 根据用户id获取关联的账户
     * @param userId
     * @return
     */
    AccountEntity getAccountByUserId(@Param("userId") Long userId);

    /**
     * 获取所有在职二级用户的选择项  id 名称
     * @return
     */
    List<OptionVo> getActiveAccountOption();

    /**
     * 更新用户余额信息
     * @param account
     * @return
     */
    int updateAccountAmount(AccountEntity account);
}
