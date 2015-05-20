package com.cardpay.pccredit.intopieces.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.constant.ApplicationStatusEnum;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.QzSyjy;
import com.cardpay.pccredit.intopieces.model.QzTz;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;
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
	private CustomerInforService customerInforservice;
	
	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	@Autowired
	private CustomerApplicationProcessService customerApplicationProcessService;
	
	@Autowired
	private CircleService circleService;
	@Autowired
	private ECIFService eCIFService;
	
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
		String appId = request.getParameter(ID);
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
			request.setAttribute("applicationStatus", ApplicationStatusEnum.APPROVE);
			request.setAttribute("objection", "false");
			//查找审批金额
			CustomerApplicationInfo appInfo = intoPiecesService.findCustomerApplicationInfoByApplicationId(appId);
			IESBForECIFReturnMap ecif = eCIFService.findEcifByCustomerId(appInfo.getCustomerId());
			Circle circle = circleService.findCircleByClientNo(ecif.getClientNo());
			
			request.setAttribute("examineAmount", circle.getContractAmt());
			
			//通过applicationId查找circle并放款 
			boolean rtn = circleService.updateCustomerInforCircle_ESB(circle);
			if(rtn){
				customerApplicationIntopieceWaitService.updateCustomerApplicationProcessBySerialNumberApplicationInfo1(request);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			else{
				returnMap.addGlobalMessage("保存失败");
			}
			
		} catch (Exception e) {
			returnMap.addGlobalMessage("保存失败");
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
		String customerId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerId)) {
			List<QzTz> qzTz = intoPiecesService.getTzList(customerId);
			mv.addObject("customerId", customerId);
			mv.addObject("list", qzTz);
		}
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
				String customerId = RequestHelper.getStringValue(request, ID);
				intoPiecesService.insertTzList(request,customerId);
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
