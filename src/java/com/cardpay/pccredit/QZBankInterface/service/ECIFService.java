package com.cardpay.pccredit.QZBankInterface.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.QZBankInterface.client.Client;
import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.dc.eai.data.CompositeData;
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
	
	@Autowired
	private IESBForECIF iesbForECIF;
	
	@Autowired
	private Client client;
	
	/**
	 * 插入数据
	 * @param customerinfo
	 * @return
	 */
	public void insertCustomerInfor(ECIF ecif) {
		String id = IDGenerator.generateID();
		ecif.setId(id);
		ecif.setCreatedTime(new Date());
		int res = commonDao.insertObject(ecif);
		
		//insert 成功后发送报文
		if(res > 0){
			//组包
			CompositeData req = iesbForECIF.createEcifRequest(ecif);
			//发送
			CompositeData resp = client.sendMess(req);
			//解析，存db
			iesbForECIF.parseEcifResponse(resp,ecif);
		}
	}
	
}
