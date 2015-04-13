package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.CustomerInforUpdateBalanceSheet;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCashFlow;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCrossExamination;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateIncomeStatement;
import com.cardpay.pccredit.customer.model.CustomerinforUpdateWorship;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface CustomerInforUpdateDao {
	
	/**
	 * 删除维护客户信息
	 * 
	 * 
	 */
	public int deleteCustomerInforUpdateCrossExamination(String id);
	
	/**
	 * 删除维护客户信息
	 * 
	 * 
	 */
	public int deleteCustomerInforUpdateCashFlow(String id);
	
	/**
	 * 删除维护客户信息
	 * 
	 * 
	 */
	public int deleteCustomerInforUpdateIncomeStatement(String id);
	
	/**
	 * 删除维护客户信息
	 * 
	 * 
	 */
	public int deleteCustomerInforUpdateBalanceSheet(String id);
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateBalanceSheet
	 */
	public List<CustomerInforUpdateBalanceSheet> getCustomerInforUpdateBalanceSheetById(String id);
	
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public List<CustomerInforUpdateIncomeStatement> getCustomerInforUpdateIncomeStatementById(String id);
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public List<CustomerInforUpdateCashFlow> getCustomerInforUpdateCashFlowById(String id);
	
	/**
	 * 获取维护客户信息
	 * 
	 * @param string id
	 * 	id 客户Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public List<CustomerInforUpdateCrossExamination> getCustomerInforUpdateCrossExaminationById(String id);
	
	
	/**
	 *根据进件ID获得客户陌拜信息快照
	 * 
	 * @param string id
	 * 	id 进件Id
	 * 
	 * @return CustomerInforUpdateIncomeStatement
	 */
	public CustomerinforUpdateWorship getCustomerinforUpdateWorshipByIntoId(@Param("id") String id);
	
}
