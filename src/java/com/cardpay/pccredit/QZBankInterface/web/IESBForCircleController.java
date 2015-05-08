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
@RequestMapping("/qzbankinterface/circle/*")
@JRadModule("qzbankinterface.circle")
public class IESBForCircleController extends BaseController{
//	@Autowired
//	private CircleService circleService;
	
	/*
	 * 跳转到增加客户信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "create.page")
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/iesbforcircle", request);
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
	public JRadReturnMap insert(@ModelAttribute IESBForCircleForm iesbForCircleForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				//设置级联选项
				iesbForCircleForm.setLoanKind(iesbForCircleForm.getLoanKind_6().split("_")[1]);
				iesbForCircleForm.setAgriLoanKind(iesbForCircleForm.getAgriLoanKind_4().split("_")[1]);
				iesbForCircleForm.setLoanDirection(iesbForCircleForm.getLoanDirection_4().split("_")[1]);
				iesbForCircleForm.setLoanBelong1(iesbForCircleForm.getLoanBelong1_5().split("_")[1]);
				iesbForCircleForm.setRegPermResidence(iesbForCircleForm.getRegPermResidence_3().split("_")[1]);
				
				Circle circle = iesbForCircleForm.createModel(Circle.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				circle.setCreatedBy(user.getId());
				circle.setUserId(user.getId());
//				circleService.insertCustomerInforCircle(circle);
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
