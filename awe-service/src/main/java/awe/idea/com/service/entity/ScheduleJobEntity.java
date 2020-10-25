package awe.idea.com.service.entity;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;



/**
 * 定时任务
 * 
 * @author Ivan
 * @email xxx@qq.com
 * @date 2020-10-07 17:08:27
 */
public class ScheduleJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 任务调度参数key
	 */
	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

	//任务id
	private Long jobId;
	//spring bean名称
	@NotBlank(message="bean名称不能为空")
	private String beanName;
	//方法名
	@NotBlank(message="方法名称不能为空")
	private String methodName;
	//参数
	private String params;
	//cron表达式
	@NotBlank(message="cron表达式不能为空")
	private String cronExpression;
	//任务状态
	private Integer status;
	//备注
	private String remark;
	//创建时间
	private Date createTime;

	/**
	 * 设置：任务id
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * 获取：任务id
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * 设置：spring bean名称
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	/**
	 * 获取：spring bean名称
	 */
	public String getBeanName() {
		return beanName;
	}
	/**
	 * 设置：方法名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获取：方法名
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 设置：参数
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 获取：参数
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 设置：cron表达式
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	/**
	 * 获取：cron表达式
	 */
	public String getCronExpression() {
		return cronExpression;
	}
	/**
	 * 设置：任务状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：任务状态
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
}
