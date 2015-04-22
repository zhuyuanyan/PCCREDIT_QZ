package com.cardpay.pccredit.QZBankInterface.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年4月22日 下午2:18:39 
 * 程序的简单说明 
 */
@Service
public class ECIFService {
	@Autowired
	private CommonDao commonDao;
	/**
	 * 插入数据
	 * @param customerinfo
	 * @return
	 */
	public void insertCustomerInfor(ECIF ecif) {
		String id = IDGenerator.generateID();
		ecif.setId(id);
		ecif.setCreatedTime(new Date());
		commonDao.insertObject(ecif);
	}
	
	public void insertCustomerInforCircle(Circle circle) {
		String id = IDGenerator.generateID();
		circle.setId(id);
		circle.setCreatedTime(new Date());
		commonDao.insertObject(circle);
	}
}
