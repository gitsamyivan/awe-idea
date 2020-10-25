package awe.idea.com.service.entity;

import awe.idea.com.common.validators.group.AddGroup;
import awe.idea.com.common.validators.group.UpdateGroup;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.shiro.authz.AuthorizationInfo;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * 系统用户
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-09 11:51:33
 */
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message="Id不能为空", groups = {UpdateGroup.class})
	private Long userId;
	//用户名
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;
	//密码
	@JSONField(serialize = false)
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	private String password;
	//邮箱
	@NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	private String email;
	//手机号
	private String mobile;
	//状态  0：禁用   1：正常
	private Integer status;
	//创建者ID
	private Long createUserId;
	//创建时间
	private Date createTime;


	private Set<String> permissionList;
	private AuthorizationInfo authorizationInfo;
	private String token;
	//角色ID列表
	private List<Long> roleIdList;
	//以下是关于账户体系的字段
	private String accountName;				//账户名称  可以是客户的姓名
	private Long parentAccountId;		//站长或者渠道属于哪个商务
	private String parentAccountName;		//站长或者渠道所属哪个商务账户名称
	private BigDecimal accountAmount;		//账户余额
	private String address;					//用户住址
	private Integer accountType;			//用户类型1管理员 2商务 3站长 4渠道客户

	/**
	 * 设置：
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态  0：禁用   1：正常
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建者ID
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 获取：创建者ID
	 */
	public Long getCreateUserId() {
		return createUserId;
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

	public Set<String> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(Set<String> permissionList) {
		this.permissionList = permissionList;
	}

	public AuthorizationInfo getAuthorizationInfo() {
		return authorizationInfo;
	}

	public void setAuthorizationInfo(AuthorizationInfo authorizationInfo) {
		this.authorizationInfo = authorizationInfo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getParentAccountId() {
		return parentAccountId;
	}

	public void setParentAccountId(Long parentAccountId) {
		this.parentAccountId = parentAccountId;
	}

	public String getParentAccountName() {
		return parentAccountName;
	}

	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}

	public BigDecimal getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
}
