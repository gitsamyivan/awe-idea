package awe.idea.com.service.service.impl;

import awe.idea.com.common.utils.Constant;
import awe.idea.com.service.constants.Constants;
import awe.idea.com.service.entity.SysUserEntity;
import awe.idea.com.service.models.OptionVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import awe.idea.com.service.dao.AccountDao;
import awe.idea.com.service.entity.AccountEntity;
import awe.idea.com.service.service.AccountService;



@Service("accountService")
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public AccountEntity queryObject(Long id){
		return accountDao.queryObject(id);
	}
	
	@Override
	public List<AccountEntity> queryList(Map<String, Object> map){
		return accountDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return accountDao.queryTotal(map);
	}
	
	@Override
	public void save(AccountEntity account){
		accountDao.save(account);
	}
	
	@Override
	public void update(AccountEntity account){
		accountDao.update(account);
	}
	
	@Override
	public void delete(Long id){
		accountDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		accountDao.deleteBatch(ids);
	}

	/**
	 * 给用户列表加上账户信息
	 */
	public void addUserListAccountInfo(List<SysUserEntity> userList) {

		if(CollectionUtils.isEmpty(userList)){
			return ;
		}
		List<Long> userIdList = new ArrayList<>();
		for(SysUserEntity user:userList){
			userIdList.add(user.getUserId());
		}

		//根据用户id获取所有的账户对象列表
		List<AccountEntity> accountList = accountDao.getAccountListByUserIdList(userIdList);
		//获取所有二级商务账户列表
		List<AccountEntity> commerceList = accountDao.getAllCommerceAccount();

		//账户信息封装
		for(SysUserEntity user:userList){
			for(AccountEntity account:accountList){
				if(account.getUserId().equals(user.getUserId())){
					user.setAccountName(account.getAccountName());
					user.setAddress(account.getAddress());
					user.setParentAccountId(account.getParentAccountId());
					user.setAccountAmount(account.getAccountAmount());
					user.setAccountType(account.getAccountType());
					break;
				}
			}
		}

		//商务名称封装
		for(SysUserEntity user:userList){
			for(AccountEntity account:commerceList){
				if(account.getId().equals(user.getParentAccountId())){
					user.setParentAccountName(account.getAccountName());
					break;
				}
			}
		}
	}

	/**
	 * 给单个用户添加账户信息  在编辑用户的时候调用
	 */
	@Override
	public void addUserAccountInfo(SysUserEntity sysUser) {

		if(sysUser == null){
			return ;
		}

		AccountEntity account = accountDao.getAccountByUserId(sysUser.getUserId());
		if(account==null){
			return ;
		}
		sysUser.setAccountName(account.getAccountName());
		sysUser.setAddress(account.getAddress());
		sysUser.setParentAccountId(account.getParentAccountId());
		sysUser.setAccountAmount(account.getAccountAmount());
		sysUser.setAccountType(account.getAccountType());


		if(account.getParentAccountId()!=null && account.getParentAccountId()>0){
			AccountEntity parentAccount = null;
			parentAccount = accountDao.queryObject(account.getParentAccountId());
			sysUser.setParentAccountName(parentAccount.getAccountName());
		}

	}

	/**
	 * 保存用户的时候添加账户信息getUserType(sysUser.getRoleIdList())
	 */
	@Override
	public void saveWhenInsertUser(SysUserEntity sysUser) {

		if(sysUser==null){
			return ;
		}

		AccountEntity account = new AccountEntity();
		account.setAccountAmount(BigDecimal.ZERO);
		account.setAccountName(sysUser.getAccountName());
		account.setAccountStatus(Constants.NORMAL_USE);
		account.setAddress(sysUser.getAddress());
		account.setParentAccountId(sysUser.getParentAccountId());
		account.setUserId(sysUser.getUserId());
		account.setAccountType(sysUser.getAccountType());
		accountDao.save(account);

	}

	/**
	 * 更新账户信息  当更新系统用户信息的时候
	 */
	@Override
	public void updateWhenInsertUser(SysUserEntity sysUser) {

		if(sysUser==null){
			return ;
		}

		AccountEntity account = accountDao.getAccountByUserId(sysUser.getUserId());

		if(account==null){
			return;
		}

		if(1==sysUser.getStatus()){
			account.setAccountStatus(Constants.NORMAL_USE);
		}else if(0==sysUser.getStatus()){
			account.setAccountStatus(Constants.LIMIT_USE);
		}
		account.setAccountName(sysUser.getAccountName());
		account.setAddress(sysUser.getAddress());
		account.setParentAccountId(sysUser.getParentAccountId());
		accountDao.update(account);
	}


	/**
	 * 根据角色区分用户类型  做了一个转换
	 * @param listRoleId
	 * @return
	 */
	public Integer getUserType(List<Long> listRoleId){
		if(CollectionUtils.isEmpty(listRoleId)){
			return 0;
		}
		if(listRoleId.size()>1){
			return 0;
		}
		if(2==listRoleId.get(0)){
			return 3;
		}else if(3==listRoleId.get(0)){
			return 4;
		}else if(4==listRoleId.get(0)){
			return 2;
		}else if(5==listRoleId.get(0)){
			return 1;
		}else{
			return 0;
		}
	}

	/**
	 * 获取所有在职商务人员列表作为前端的下拉筛选项
	 */
	@Override
	public List<OptionVo> getActiveAccountOption() {
		return accountDao.getActiveAccountOption();
	}

}
