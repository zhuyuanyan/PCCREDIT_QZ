package com.cardpay.pccredit.product.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 产品过滤条件
 * 
 * @author 王海东
 * @created on 2014-10-11
 * 
 * @version $Id:$
 */
public class ProductFilter extends BaseQueryFilter {

	private String productName;
	
	private String type;
	
	private String default_type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDefault_type() {
		return default_type;
	}

	public void setDefault_type(String default_type) {
		this.default_type = default_type;
	}


}
