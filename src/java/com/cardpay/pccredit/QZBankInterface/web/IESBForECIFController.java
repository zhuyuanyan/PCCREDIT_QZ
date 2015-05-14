package com.cardpay.pccredit.QZBankInterface.web;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.QZBankInterface.filter.EcifFilter;
import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.cardpay.pccredit.QZBankInterface.service.ECIFService;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
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
@RequestMapping("/intopieces/ecif/*")
@JRadModule("intopieces.ecif")
public class IESBForECIFController extends BaseController{
	@Autowired
	private ECIFService ecifService;
	
	@Autowired
	private CustomerInforService customerInforService;
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute EcifFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		filter.setUserId(userId);
		QueryResult<ECIF> result = ecifService.findEcifByFilter(filter);
		JRadPagedQueryResult<ECIF> pagedResult = new JRadPagedQueryResult<ECIF>(
				filter, result);

		JRadModelAndView mv = new JRadModelAndView(
				"/qzbankinterface/iesbforecif_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/*
	 * 跳转到增加客户信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "create.page")
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/iesbforecif", request);
		
		//查找登录用户信息
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String orgId = user.getOrganization().getId();//机构ID
		String parentOrgId = user.getOrganization().getParentId();//机构ID
		if(parentOrgId.equals("000000")){//替换为泉州总行id
			parentOrgId = Constant.QZ_ORG_ROOT_ID;
		}
		String externalId = user.getLogin();//工号
		mv.addObject("orgId",orgId);
		mv.addObject("parentOrgId",parentOrgId);
		mv.addObject("externalId",externalId);
				
		return mv;
	}
	
	/*
	 * 跳转到修改客户信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "display.page")
	public AbstractModelAndView change(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/iesbforecif_display", request);
		
		String clientNo = request.getParameter(ID).split("_")[0];
		ECIF ecif = ecifService.findEcifByClientNo(clientNo);
		mv.addObject("ecif",ecif);
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
		SimpleDateFormat formatter10 = new SimpleDateFormat("yyyy-MM-dd");
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				//设置级联选项
				iesbForECIFForm.setRegPermResidence(iesbForECIFForm.getRegPermResidence_3().split("_")[1]);
				iesbForECIFForm.setCity(iesbForECIFForm.getCity_3().split("_")[1]);
				
				ECIF ecif = iesbForECIFForm.createModel(ECIF.class);
				
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				//写入数据到basic_customer_information表
				CustomerInfor info = new CustomerInfor();
				info.setUserId(user.getId());
				info.setChineseName(ecif.getClientName());
				info.setBirthday(formatter10.format(ecif.getBirthDate()));
				info.setNationality("NTC00000000156");
				info.setSex(ecif.getSex().equals("01") ? "Male" : "Female");
				customerInforService.insertCustomerInfor(info);
				
				ecif.setCreatedBy(user.getId());
				ecif.setUserId(user.getId());
				ecif.setStatus(com.cardpay.pccredit.QZBankInterface.constant.Constant.STATUS_NONE);
				ecif.setCustomerId(info.getId());;//设置关联basic_customer_information
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

