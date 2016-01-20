package com.cardpay.pccredit.intopieces.web;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.service.CircleService;
import com.cardpay.pccredit.QZBankInterface.service.ECIFService;
import com.cardpay.pccredit.QZBankInterface.web.IESBForCircleForm;
import com.cardpay.pccredit.QZBankInterface.web.IESBForECIFReturnMap;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.model.QzApplnJyd;
import com.cardpay.pccredit.intopieces.model.QzApplnSdhjy;
import com.cardpay.pccredit.intopieces.service.AttachmentListService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationProcessService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.workflow.constant.ApproveOperationTypeEnum;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
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
@RequestMapping("/intopieces/intopieceszhongxin*")
@JRadModule("intopieces.intopieceszhongxin")
public class IntoPiecesZhongxinControl extends BaseController {

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
	
	@Autowired
	private ECIFService eCIFService;
	@Autowired
	private CircleService circleService;
	
	@Autowired
	private AttachmentListService attachmentListService;
	
	/**
	 *团队管理岗复核页面（原中心岗）
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "zhongxin.page", method = { RequestMethod.GET })
	public AbstractModelAndView Zhongxin(@ModelAttribute CustomerApplicationProcessFilter filter, HttpServletRequest request) throws SQLException {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setLoginId(loginId);
		filter.setNodeName(Constant.status_zhongxin);
		filter.setFilterTeamLeader("1");
		QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.recieveIntopieceWaitForm(filter);
		JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);

		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/intopieces_wait/intopiecesApprove_zhongxin", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("filter", filter);
		return mv;
	}
	
	
	/**
	 * 申请件审批通过 
	 * 从中心负责岗--信贷岗
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
			request.setAttribute("applicationStatus", ApproveOperationTypeEnum.APPROVE.toString());
			request.setAttribute("objection", "false");
			//查找审批金额
			Circle circle = circleService.findCircleByAppId(appId);
			
			request.setAttribute("examineAmount", circle.getContractAmt());
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
	 * 从中心岗--授信岗
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
			//intoPiecesService.returnAppln(appId, request);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("保存失败");
			e.printStackTrace();
		}
		return returnMap;
	}
	
	/*
	 * 调额
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "zhongxin_edu.page")
	public AbstractModelAndView edu(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/zhongxin_edu", request);
		
		//查找新开户
		/*List<IESBForECIFReturnMap> ls = eCIFService.findAllECIFByStatus2(com.cardpay.pccredit.QZBankInterface.constant.Constant.STATUS_CIRCLE);
		mv.addObject("ECIF_ls",ls);
		JSONArray json = new JSONArray();
		json = JSONArray.fromObject(ls);
		mv.addObject("ECIF_ls_json",json.toString());*/
		String applicationId = request.getParameter(ID);
		CustomerApplicationInfo appInfo = intoPiecesService.findCustomerApplicationInfoByApplicationId(applicationId);
		IESBForECIFReturnMap ecif = eCIFService.findEcifMapByCustomerId(appInfo.getCustomerId());
		mv.addObject("ecif",ecif);
				
		List<Circle> circle_ls = circleService.findCircleByClientNo(ecif.getClientNo());
		mv.addObject("circle",circle_ls.get(circle_ls.size()-1));
		return mv;
	}
	
	/**
	 * 调额
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "update_edu.json")
	public JRadReturnMap update_edu(@ModelAttribute IESBForCircleForm iesbForCircleForm, HttpServletRequest request) {
		String clientNo = request.getParameter(ID);
		
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				List<Circle> circle_ls = circleService.findCircleByClientNo(clientNo);
				Circle circle = circle_ls.get(circle_ls.size()-1);
				
				circle.setContractAmt(iesbForCircleForm.getContractAmt());
				
				circleService.updateCustomerInforCircle(circle);
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

	/**
	 * 申请件拒件
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "rejectAppln.json")
	public JRadReturnMap rejectAppln(HttpServletRequest request) throws SQLException {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String appId = request.getParameter("appId1");
			intoPiecesService.rejectAppln(appId, request);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("保存失败");
			e.printStackTrace();
		}
		return returnMap;
	}
	
	/**
	 * 进入审议纪要页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "show_syjy_form.page")
	public AbstractModelAndView showSyjyForm(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page10", request);
		String appId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(appId)) {
			QzApplnSdhjy qzSyjy = intoPiecesService.getSyjyForm(appId);
			mv.addObject("result", qzSyjy);
			mv.addObject("readType", "readType");
		}
		return mv;
	}
	
	/**
	 * 进入授信决议单页面(只读)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "jyd_form.page")
	public AbstractModelAndView jydForm(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page8_for_approve", request);
		String appId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(appId)) {
			QzApplnJyd qzSdhjyd = intoPiecesService.getSdhjydFormAfter(appId);
			mv.addObject("result", qzSdhjyd);
			mv.addObject("readType", "readType");
		}
		return mv;
	}
	//iframe_approve(申请后)
	@ResponseBody
	@RequestMapping(value = "iframe_approve.page")
	public AbstractModelAndView iframeApprove(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe_approve", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
			mv.addObject("appId", appId);
			mv.addObject("operate", Constant.status_zhongxin);
		}
		return mv;
	}
}
