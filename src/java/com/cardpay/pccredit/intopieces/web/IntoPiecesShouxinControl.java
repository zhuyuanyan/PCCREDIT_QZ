package com.cardpay.pccredit.intopieces.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.cardpay.pccredit.QZBankInterface.web.IESBForECIFForm;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.VideoAccessoriesFilter;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.constant.ApplicationStatusEnum;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.QzShouxin;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationProcessService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
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

@Controller
@RequestMapping("/intopieces/intopiecesshouxin/*")
@JRadModule("intopieces.intopiecesshouxin")
public class IntoPiecesShouxinControl extends BaseController {

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	@Autowired
	private CustomerApplicationProcessService customerApplicationProcessService;
	
	
	/**
	 * 授信岗进件页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "shouxin.page", method = { RequestMethod.GET })
	public AbstractModelAndView shouxin(@ModelAttribute CustomerApplicationProcessFilter filter, HttpServletRequest request) throws SQLException {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setLoginId(loginId);
		filter.setNodeName(Constant.status_shouxin);
		QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.recieveIntopieceWaitForm(filter);
		JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);

		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/intopieces_wait/intopiecesApprove_shouxin", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("filter", filter);
		return mv;
	}
	
	/**
	 * 进入授信决议单页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_form.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createForm(HttpServletRequest request) {
		String appId = request.getParameter(ID);
		List<QzShouxin>  result =intoPiecesService.getShouxinform(appId);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_wait/intopiecesApprove_shouxin_form", request);
		mv.addObject("appId",appId);
		if(result.size()>0){
			mv.addObject("result",result.get(0));
		}else{
			mv.addObject("result",result);
		}
		return mv;
	}
	
	/**
	 * 授信决议单保存
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "insert.json")
	public JRadReturnMap insert(@ModelAttribute QzShouxinForm qzShouxinForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String appId = request.getParameter("appId");
				QzShouxin qzShouxin = qzShouxinForm.createModel(QzShouxin.class);
				qzShouxin.setApplicationId(appId);
				intoPiecesService.insertShouxinForm(qzShouxin,appId);
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
	
	/**
	 * 申请件审批通过 
	 * 从授信审批岗--中心负责岗
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pass.json")
	public JRadReturnMap pass(HttpServletRequest request) throws SQLException {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String appId = request.getParameter("id");
			CustomerApplicationProcess process =  customerApplicationProcessService.findByAppId(appId);
			request.setAttribute("serialNumber", process.getSerialNumber());
			request.setAttribute("applicationId", process.getApplicationId());
			request.setAttribute("applicationStatus", ApplicationStatusEnum.APPROVE);
			request.setAttribute("objection", "false");
			request.setAttribute("examineAmount", "");
			customerApplicationIntopieceWaitService.updateCustomerApplicationProcessBySerialNumberApplicationInfo1(request);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("保存失败");
			e.printStackTrace();
		}
		return returnMap;
	}
	
	/**
	 * 申请件退件
	 * 从授信岗--行政岗
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "returnAppln.json")
	public JRadReturnMap returnAppln(HttpServletRequest request) throws SQLException {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String appId = request.getParameter("appId");
			intoPiecesService.returnAppln(appId, request);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("保存失败");
			e.printStackTrace();
		}
		return returnMap;
	}
}
