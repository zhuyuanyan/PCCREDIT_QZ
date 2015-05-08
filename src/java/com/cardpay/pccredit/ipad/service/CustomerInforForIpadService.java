/**
 * 
 */
package com.cardpay.pccredit.ipad.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.ipad.dao.CustomerInforIpadDao;
import com.cardpay.pccredit.ipad.model.CustomerInforIpad;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.util.date.DateHelper;

/**
 * @author shaoming
 *
 * 2014年11月28日   上午11:41:49
 */
@Service
public class CustomerInforForIpadService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerInforIpadDao customerInfor;
	/**
	 * 添加客户基本信息
	 * @param customerInfor
	 * @return
	 */
	public boolean insertCustomerInfor(CustomerInfor customerInfor){
		String userId = customerInfor.getUserId();
		if(StringUtils.isNotEmpty(userId)){
			customerInfor.setCreatedBy(userId);
		}
		customerInfor.setCreatedTime(DateHelper.normalizeDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		customerInfor.setId(IDGenerator.generateID());
		int i = commonDao.insertObject(customerInfor);
		return i>0?true:false;
	}
	
	public List<CustomerInforIpad> getCustomerInforByUserId(String userId,int currentPage,int pageSize){
		currentPage = currentPage - 1;
		if(currentPage<0){
			currentPage = 0;
		}
		List<CustomerInforIpad> list = customerInfor.getCustomerInforByUserId(userId, currentPage, pageSize);
		return list;
	}
	public int getCustomerInforCountByUserId(String userId){
		int i = customerInfor.getCustomerInforCountByUserId(userId);
		return i;
	}
	/**
	 * 通过id查询客户基本信息
	 * @param id
	 * @return
	 */
	public CustomerInforIpad findCustomerInforById(String id){
		return customerInfor.findCustomerInforById(id);
	}
	/**
	 * 通过证件号查询客户基本信息
	 * @param cardId
	 * @return
	 */
	public CustomerInforIpad findCustomerInforByCardId(String cardId){
		return customerInfor.findCustomerInforByCardId(cardId);
	}
}
