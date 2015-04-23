package com.cardpay.pccredit.QZBankInterface.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.cardpay.pccredit.QZBankInterface.service.ECIFService;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.web.CustomerInforForm;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年4月14日 下午5:44:14 
 * 程序的简单说明 
 */
@Controller
@RequestMapping("/qzbankinterface/ecif/*")
@JRadModule("qzbankinterface.ecif")
public class IESBForECIFController extends BaseController{
	@Autowired
	private ECIFService ecifService;
	
	/*
	 * 跳转到增加客户信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "create.page")
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/QZBankInterface/iesbforecif", request);
		return mv;
	}
	
	/**
	 * 执行添加客户信息
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "insert.json")
	public JRadReturnMap insert(@ModelAttribute IESBForECIFForm iesbForECIFForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				ECIF ecif = iesbForECIFForm.createModel(ECIF.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				ecif.setCreatedBy(user.getId());
				ecif.setUserId(user.getId());
				ecifService.insertCustomerInfor(ecif);
//				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}

}

