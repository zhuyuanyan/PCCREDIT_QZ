/**
 * 
 */
package com.cardpay.pccredit.customer.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午2:58:41
 */
public class MaintenanceForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String chineseName;
	private String customerManagerId;
	private String userName;
	private String maintenanceGoal;
	private String maintenanceWay;
	private String maintenanceDay;
	private String createWay;
	private String endResult;
	private String remarksCreateReason;
	private Date maintenanceEndtime;
	private String maintenancePlanId;
	private String maintenanceResult;
	private String maintenanceStartTime;
	private String maintenanceEndTime;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMaintenancePlanId() {
		return maintenancePlanId;
	}
	public void setMaintenancePlanId(String maintenancePlanId) {
		this.maintenancePlanId = maintenancePlanId;
	}
	public String getMaintenanceResult() {
		return maintenanceResult;
	}
	public void setMaintenanceResult(String maintenanceResult) {
		this.maintenanceResult = maintenanceResult;
	}
	
	public String getMaintenanceStartTime() {
		return maintenanceStartTime;
	}
	public void setMaintenanceStartTime(String maintenanceStartTime) {
		this.maintenanceStartTime = maintenanceStartTime;
	}
	public String getMaintenanceEndTime() {
		return maintenanceEndTime;
	}
	public void setMaintenanceEndTime(String maintenanceEndTime) {
		this.maintenanceEndTime = maintenanceEndTime;
	}
	public Date getMaintenanceEndtime() {
		return maintenanceEndtime;
	}
	public void setMaintenanceEndtime(Date maintenanceEndtime) {
		this.maintenanceEndtime = maintenanceEndtime;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getMaintenanceGoal() {
		return maintenanceGoal;
	}
	public void setMaintenanceGoal(String maintenanceGoal) {
		this.maintenanceGoal = maintenanceGoal;
	}
	public String getMaintenanceWay() {
		return maintenanceWay;
	}
	public void setMaintenanceWay(String maintenanceWay) {
		this.maintenanceWay = maintenanceWay;
	}
	public String getMaintenanceDay() {
		return maintenanceDay;
	}
	public void setMaintenanceDay(String maintenanceDay) {
		this.maintenanceDay = maintenanceDay;
	}
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public String getRemarksCreateReason() {
		return remarksCreateReason;
	}
	public void setRemarksCreateReason(String remarksCreateReason) {
		this.remarksCreateReason = remarksCreateReason;
	}
}
