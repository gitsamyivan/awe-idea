package awe.idea.com.service.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-07 17:08:27
 */
public class AccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long id;
	//系统对应的用户id
	private Long userId;
	//账户名称
	private String accountName;
	//账户类型（1.管理员 2.商务 3站长 4客户）
	private Integer accountType;
	//所属上级账户id
	private Long parentAccountId;
	//账户当前所有的金额
	private BigDecimal accountAmount;
	//账户状态
	private Integer accountStatus;
	//用户的公司地址或者家庭地址
	private String address;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

	/**
	 * 设置：ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：系统对应的用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：系统对应的用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：账户名称
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * 获取：账户名称
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * 设置：账户类型（1.管理员 2.商务 3站长 4客户）
	 */
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	/**
	 * 获取：账户类型（1.管理员 2.商务 3站长 4客户）
	 */
	public Integer getAccountType() {
		return accountType;
	}
	/**
	 * 设置：所属上级账户id
	 */
	public void setParentAccountId(Long parentAccountId) {
		this.parentAccountId = parentAccountId;
	}
	/**
	 * 获取：所属上级账户id
	 */
	public Long getParentAccountId() {
		return parentAccountId;
	}
	/**
	 * 设置：账户当前所有的金额
	 */
	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}
	/**
	 * 获取：账户当前所有的金额
	 */
	public BigDecimal getAccountAmount() {
		return accountAmount;
	}
	/**
	 * 设置：账户状态
	 */
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}
	/**
	 * 获取：账户状态
	 */
	public Integer getAccountStatus() {
		return accountStatus;
	}
	/**
	 * 设置：用户的公司地址或者家庭地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：用户的公司地址或者家庭地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
