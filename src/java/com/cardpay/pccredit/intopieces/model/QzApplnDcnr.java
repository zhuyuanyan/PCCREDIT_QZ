/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

/**
 * 
 * @author 贺珈
 * 
 */
@ModelParam(table = "qz_appln_dcnr")
public class QzApplnDcnr extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String id;
	private String customerId;
	private String reportId;
	private String reportName;
	private String loadStatus;
	private String applicationId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getLoadStatus() {
		return loadStatus;
	}
	public void setLoadStatus(String loadStatus) {
		this.loadStatus = loadStatus;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
}
