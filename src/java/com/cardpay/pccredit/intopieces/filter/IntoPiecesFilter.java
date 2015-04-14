package com.cardpay.pccredit.intopieces.filter;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class IntoPiecesFilter extends BusinessFilter{
	
	private String id;//进件编号
	private String chineseName;//客户名称
    private String productName; //产品名称
    private String cardId; //证件号码
    
    private String status;
    
    private String  userId;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
