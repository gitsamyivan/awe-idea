package awe.idea.com.gen.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 系统配置信息表
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-25 16:46:51
 */
public class SysConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//key
	private String key;
	//value
	private String value;
	//状态   0：隐藏   1：显示
	private Integer status;
	//备注
	private String remark;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 获取：key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * 设置：value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置：状态   0：隐藏   1：显示
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态   0：隐藏   1：显示
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
}
