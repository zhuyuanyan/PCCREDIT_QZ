package com.cardpay.pccredit.intopieces.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.service.CircleService;
import com.cardpay.pccredit.QZBankInterface.service.ECIFService;
import com.cardpay.pccredit.QZBankInterface.web.IESBForECIFReturnMap;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.VideoAccessoriesFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.model.QzApplnHtqdtz;
import com.cardpay.pccredit.intopieces.model.QzApplnNbscyjb;
import com.cardpay.pccredit.intopieces.service.AttachmentListService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationProcessService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
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
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

@Controller
@RequestMapping("/intopieces/intopiecesxingzheng2*")
@JRadModule("intopieces.intopiecesxingzheng2")
public class IntoPiecesXingzhengendControl extends BaseController {

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	@Autowired
	private CustomerApplicationInfoService customerApplicationInfoService;
	@Autowired
	private CustomerApplicationProcessService customerApplicationProcessService;
	
	@Autowired
	private CircleService circleService;
	@Autowired
	private ECIFService eCIFService;
	
	@Autowired
	private AttachmentListService attachmentListService;
	/**
	 * 行政岗终进件页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "xingzhengend.page", method = { RequestMethod.GET })
	public AbstractModelAndView Xingzhengend(@ModelAttribute CustomerApplicationProcessFilter filter, HttpServletRequest request) throws SQLException {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setLoginId(loginId);
		filter.setNodeName(Constant.status_xingzheng2);
		QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.recieveIntopieceWaitForm(filter);
		JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);

		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/intopieces_wait/intopiecesApprove_xingzhengend", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("filter", filter);
		return mv;
	}
	
	/**
	 * 进入合同扫描单页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_upload.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createUpload(@ModelAttribute VideoAccessoriesFilter filter,HttpServletRequest request) {
		String appId = request.getParameter("appId");
		String type = request.getParameter("type");
		List<QzDcnrUploadForm>  result =intoPiecesService.getUploadList(appId);
		for(int i=0;i<result.size();i++){
			if(result.get(i).getHetongId()==null){
				result.get(i).setHetongId("");
				result.get(i).setUserName("");
				result.get(i).setCardId("");
			}
		}

		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_wait/intopiecesApprove_xingzhengend_upload", request);
		mv.addObject("result", result);
		mv.addObject("appId",appId);
		mv.addObject("type",type);
		return mv;
	}
	
	/**
	 * 决议单保存
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "saveYxzl.json",method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public Map<String,Object> saveYxzl(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, Constant.FILE_EMPTY);
				return map;
			}
			intoPiecesService.saveHtByCustomerId(request.getParameter("appId"),request.getParameter("remark"),request.getParameter("hetongId"),request.getParameter("userName"),request.getParameter("cardId"),file);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Constant.FAIL_MESSAGE);
			return map;
		}
		return map;
	}
	
	/**
	 * 申请件审批通过 
	 * 从信审岗--行政岗终
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save_apply.json")
	public JRadReturnMap saveApply(HttpServletRequest request) throws SQLException {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String appId = request.getParameter("id");

			CustomerApplicationProcess process =  customerApplicationProcessService.findByAppId(appId);
			request.setAttribute("serialNumber", process.getSerialNumber());
			request.setAttribute("applicationId", process.getApplicationId());
			request.setAttribute("applicationStatus", ApproveOperationTypeEnum.APPROVE.toString());
			request.setAttribute("objection", "false");
			//查找审批金额
			Circle circle = circleService.findCircleByAppId(appId);
			if(StringUtils.isBlank(circle.getClientNo())){
				returnMap.put(JRadConstants.SUCCESS, false);			
				returnMap.put("retMsg", "客户号不能为空~！");
				return returnMap;
			}
			request.setAttribute("examineAmount", circle.getContractAmt());
			
			//先开户 后通过applicationId查找circle并放款 
			String rtn = circleService.updateCustomerInforCircle_ESB(circle);
			if("放款成功".equals(rtn)){
				customerApplicationIntopieceWaitService.updateCustomerApplicationProcessBySerialNumberApplicationInfo1(request);
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.put("retMsg", rtn);
			}
			else{
				returnMap.put(JRadConstants.SUCCESS, false);			
				returnMap.put("retMsg", rtn);
			}
			
		} catch (Exception e) {
			returnMap.put(JRadConstants.SUCCESS, false);
			returnMap.put("retMsg", "保存失败");
			e.printStackTrace();
		}
		return returnMap;
	}
	/**
	 * 进入台帐录入页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_tz_form.page")
	public AbstractModelAndView createSyjyForm(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page11", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getLogin();
		if (StringUtils.isNotEmpty(appId)) {
			List<QzApplnHtqdtz> qzTz = intoPiecesService.getTzList(appId);
			mv.addObject("appId", appId);
			mv.addObject("list", qzTz);
			mv.addObject("type", type);
			mv.addObject("returnUrl", intoPiecesService.getReturnUrl(operate));
		}
		mv.addObject("loginId",loginId);
		return mv;
	}
	/**
	 * 台帐保存
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "tz_save.json")
	public JRadReturnMap tzSave(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String appId = RequestHelper.getStringValue(request, ID);
				intoPiecesService.insertTzList(request,appId);
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
	//iframe_approve(申请后)
	@ResponseBody
	@RequestMapping(value = "iframe_approve.page")
	public AbstractModelAndView iframeApprove(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_approve", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		String ifHideUser = RequestHelper.getStringValue(request, "ifHideUser");
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
			mv.addObject("appId", appId);
			mv.addObject("operate", Constant.status_xingzheng2);
			mv.addObject("ifHideUser", ifHideUser);
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "returnAppln.json")
	public JRadReturnMap returnAppln(HttpServletRequest request) throws SQLException {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			int nodeNo = 7;//中心终审
			String appId = request.getParameter("appId");
			String operate = request.getParameter("operate");
			String nodeName = request.getParameter("nodeName");
			//退回客户经理和其他岗位不一致
			if("1".equals(nodeName)){
				
				intoPiecesService.checkDoNotToManager(appId,request);
			}else{
				intoPiecesService.returnAppln(appId, request,nodeName);
			}
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("保存失败");
			e.printStackTrace();
		}
		return returnMap;
	}
	//进件查询(卡中心)入口
	@ResponseBody
	@RequestMapping(value = "iframe_approve_query.page")
	public AbstractModelAndView iframeApproveQuery(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_approve", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		String ifHideUser = RequestHelper.getStringValue(request, "ifHideUser");
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
			mv.addObject("appId", appId);
			mv.addObject("operate", Constant.status_cardquery);
			mv.addObject("ifHideUser", ifHideUser);
		}
		return mv;
	}
	
	//进件查询(安居贷)入口
		@ResponseBody
		@RequestMapping(value = "iframe_approve_query_anjudai.page")
		public AbstractModelAndView iframeApproveQueryAnjudai(HttpServletRequest request) {
			JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_approve", request);
			String customerInforId = RequestHelper.getStringValue(request, ID);
			String appId = RequestHelper.getStringValue(request, "appId");
			String ifHideUser = RequestHelper.getStringValue(request, "ifHideUser");
			if (StringUtils.isNotEmpty(customerInforId)) {
				CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
				mv.addObject("customerInfor", customerInfor);
				mv.addObject("customerId", customerInfor.getId());
				mv.addObject("appId", appId);
				mv.addObject("operate", Constant.status_anjudai);
				mv.addObject("ifHideUser", ifHideUser);
			}
			return mv;
		}
	//进件查询入口
		@ResponseBody
		@RequestMapping(value = "iframe_cardapprove.page")
		public AbstractModelAndView iframeApproveCard(HttpServletRequest request) {
			JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_approve", request);
			String customerInforId = RequestHelper.getStringValue(request, ID);
			String appId = RequestHelper.getStringValue(request, "appId");
			String ifHideUser = RequestHelper.getStringValue(request, "ifHideUser");
			if (StringUtils.isNotEmpty(customerInforId)) {
				CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
				mv.addObject("customerInfor", customerInfor);
				mv.addObject("customerId", customerInfor.getId());
				mv.addObject("appId", appId);
				mv.addObject("operate", Constant.status_query);
				mv.addObject("ifHideUser", ifHideUser);
			}
			return mv;
		}
}
