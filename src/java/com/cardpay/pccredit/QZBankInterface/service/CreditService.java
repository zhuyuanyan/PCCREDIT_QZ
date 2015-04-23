package com.cardpay.pccredit.QZBankInterface.service;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.model.Credit;

import java.util.Date;

import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by johhny on 15/4/22.
 */
@Service
public class CreditService {
    @Autowired
    private CommonDao commonDao;
    /**
     * 插入数据
     * @param credit
     * @return
     */
	public void insertCustomerInforCredit(Credit credit) {
		String id = IDGenerator.generateID();
		credit.setId(id);
		credit.setCreatedTime(new Date());
		commonDao.insertObject(credit);
	}
}
