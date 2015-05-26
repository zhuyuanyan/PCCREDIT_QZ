package com.cardpay.pccredit.intopieces.web;

import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.QZBankInterface.model.ECIF;
import com.cardpay.pccredit.QZBankInterface.service.CircleService;
import com.cardpay.pccredit.QZBankInterface.service.ECIFService;
import com.cardpay.pccredit.QZBankInterface.web.IESBForCircleForm;
import com.cardpay.pccredit.QZBankInterface.web.IESBForECIFReturnMap;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.datapri.service.DataAccessSqlService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxx;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxxDkjl;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxxFc;
import com.cardpay.pccredit.intopieces.model.QzApplnDbrxxJdc;
import com.cardpay.pccredit.intopieces.model.QzApplnJydBzdb;
import com.cardpay.pccredit.intopieces.model.QzApplnJydDydb;
import com.cardpay.pccredit.intopieces.model.QzApplnJydGtjkr;
import com.cardpay.pccredit.intopieces.model.QzApplnNbscyjb;
import com.cardpay.pccredit.intopieces.model.QzApplnYwsqb;
import com.cardpay.pccredit.intopieces.model.QzApplnYwsqbJkjl;
import com.cardpay.pccredit.intopieces.model.QzApplnYwsqbZygys;
import com.cardpay.pccredit.intopieces.model.QzApplnYwsqbZykh;
import com.cardpay.pccredit.intopieces.service.AttachmentListService;
import com.cardpay.pccredit.intopieces.model.QzApplnJyd;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;
import com.cardpay.pccredit.intopieces.service.DbrxxService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.intopieces.service.NbscyjbService;
import com.cardpay.pccredit.intopieces.service.YwsqbService;
import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
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
import com.wicresoft.util.web.RequestHelper;

@Controller
@RequestMapping("/intopieces/intopiecesapprove/*")
@JRadModule("intopieces.intopiecesapprove")
public class IntoPiecesApproveControl extends BaseController {

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	
	@Autowired
	private DataAccessSqlService dataAccessSqlService;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerInforDao customerInforDao;
	
	@Autowired
	private CustomerInforCommDao customerinforcommDao;
	
	@Autowired
	private NodeAuditService nodeAuditService;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	
	@Autowired
	private CircleService circleService;
	
	@Autowired
	private ECIFService eCIFService;
	
	@Autowired
	private YwsqbService ywsqbService;
	
	@Autowired
	private DbrxxService dbrxxService;
	
	@Autowired
	private AttachmentListService attachmentListService;
	
	@Autowired
	private NbscyjbService nbscyjbService;
	
	/**
	 * 申请页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "approve.page", method = { RequestMethod.GET })
	public AbstractModelAndView browse(@ModelAttribute CustomerInforFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		QueryResult<CustomerInfor> result = customerInforservice.findCustomerInforWithEcifByFilter(filter);
		for(int i=0;i<result.getItems().size();i++){
				CustomerApplicationInfo info = customerInforservice.ifProcess(result.getItems().get(i).getId());
				//目前存在申请件
				if(info!=null){
					if(info.getStatus().equals(Constant.APPROVED_INTOPICES)){
						result.getItems().get(i).setProcessId(Constant.APP_STATE_4);
					}else{
						result.getItems().get(i).setProcessId(Constant.APP_STATE_1);
					}
					//目前不存在申请件（初审退回）
				}else if(result.getItems().get(i).getProcessId()==null){
					result.getItems().get(i).setProcessId(Constant.APP_STATE_2);
					//没申请件
				}else{
					result.getItems().get(i).setProcessId(Constant.APP_STATE_3);
				}
			}
		JRadPagedQueryResult<CustomerInfor> pagedResult = new JRadPagedQueryResult<CustomerInfor>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_approve",
                                                    request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/**
	 * 添加申请进件信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "changewh.page")
	public AbstractModelAndView changewh(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/qz_customerinfor_base", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
		}
		return mv;
		
	}
	
	/**
	 * 执行申请
	 * @param customerInforFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save_apply.page")
	public JRadReturnMap save_apply(@ModelAttribute CustomerInforFilter customerInforFilter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("id");
				//设置流程开始
				saveApply(customerId);
				
				//查找customerId对应当前申请中的贷款，并更新其状态为申请中
				IESBForECIFReturnMap ecif = eCIFService.findEcifByCustomerId(customerId);
				circleService.updateCustomerInforCircle_APPLY(ecif.getClientNo());
				
				returnMap.put(RECORD_ID, customerId);
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
	 * 提交申请，开始流程
	 * @param customer_id
	 */
	public void saveApply(String customer_id){
		//设置申请
		CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
		//customerApplicationInfo.setStatus(status);
		customerApplicationInfo.setId(IDGenerator.generateID());
			customerApplicationInfo.setApplyQuota("0");//设置额度
		customerApplicationInfo.setCustomerId(customer_id);
		if(customerApplicationInfo.getApplyQuota()!=null){
			customerApplicationInfo.setApplyQuota((Integer.valueOf(customerApplicationInfo.getApplyQuota())*100)+"");
		}
		customerApplicationInfo.setCreatedTime(new Date());
		customerApplicationInfo.setStatus(Constant.APPROVE_INTOPICES);
		//查找默认产品
		ProductFilter filter = new ProductFilter();
		filter.setDefault_type(Constant.DEFAULT_TYPE);
		ProductAttribute productAttribute = productService.findProductsByFilter(filter).getItems().get(0);
		customerApplicationInfo.setProductId(productAttribute.getId());
				
		commonDao.insertObject(customerApplicationInfo);
		
		
		//添加申请件流程
		WfProcessInfo wf=new WfProcessInfo();
		wf.setProcessType(WfProcessInfoType.process_type);
		wf.setVersion("1");
		commonDao.insertObject(wf);
		List<NodeAudit> list=nodeAuditService.findByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(),productAttribute.getId());
		boolean startBool=false;
		boolean endBool=false;
		//节点id和WfStatusInfo id的映射
		Map<String, String> nodeWfStatusMap = new HashMap<String, String>();
		for(NodeAudit nodeAudit:list){
			if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
				startBool=true;
			}
			
			if(startBool&&!endBool){
				WfStatusInfo wfStatusInfo=new WfStatusInfo();
				wfStatusInfo.setIsStart(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())?"1":"0");
				wfStatusInfo.setIsClosed(nodeAudit.getIsend().equals(YesNoEnum.YES.name())?"1":"0");
				wfStatusInfo.setRelationedProcess(wf.getId());
				wfStatusInfo.setStatusName(nodeAudit.getNodeName());
				wfStatusInfo.setStatusCode(nodeAudit.getId());
				commonDao.insertObject(wfStatusInfo);
				
				nodeWfStatusMap.put(nodeAudit.getId(), wfStatusInfo.getId());
				
				if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
					//添加初始审核
					CustomerApplicationProcess customerApplicationProcess=new CustomerApplicationProcess();
					String serialNumber = processService.start(wf.getId());
					customerApplicationProcess.setSerialNumber(serialNumber);
					customerApplicationProcess.setNextNodeId(nodeAudit.getId()); 
					customerApplicationProcess.setApplicationId(customerApplicationInfo.getId());
					commonDao.insertObject(customerApplicationProcess);
					
					CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, customerApplicationInfo.getId());
					applicationInfo.setSerialNumber(serialNumber);
					commonDao.updateObject(applicationInfo);
				}
			}
			
			if(nodeAudit.getIsend().equals(YesNoEnum.YES.name())){
				endBool=true;
			}
		}
		//节点关系
		List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(), productAttribute.getId());
		for(NodeControl control : nodeControls){
			WfStatusResult wfStatusResult = new WfStatusResult();
			wfStatusResult.setCurrentStatus(nodeWfStatusMap.get(control.getCurrentNode()));
			wfStatusResult.setNextStatus(nodeWfStatusMap.get(control.getNextNode()));
			wfStatusResult.setExamineResult(control.getCurrentStatus());
			commonDao.insertObject(wfStatusResult);
		}
		
		//对之前无appId的表添加id(尤其是调查内容记录表添加appId)
		intoPiecesService.addAppId(customer_id,customerApplicationInfo.getId());
	}
	
	//iframe
	@ResponseBody
	@RequestMapping(value = "iframe.page")
	public AbstractModelAndView iframe(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/iframe", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
		}
		return mv;
	}
	
	
	//page1
	@ResponseBody
	@RequestMapping(value = "page1.page")
	public AbstractModelAndView page1(HttpServletRequest request) {
		
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		//查找page1信息
		QzApplnYwsqb qzApplnYwsqb = ywsqbService.findYwsqb(customerInforId, null);
		JRadModelAndView mv = null;
		if(qzApplnYwsqb != null){
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page1_change", request);
			List<QzApplnYwsqbZygys> zygys_ls = ywsqbService.findYwsqbZygys(qzApplnYwsqb.getId());
			List<QzApplnYwsqbZykh> zykh_ls = ywsqbService.findYwsqbZykh(qzApplnYwsqb.getId());
			List<QzApplnYwsqbJkjl> jkjl_ls = ywsqbService.findYwsqbJkjl(qzApplnYwsqb.getId());
			mv.addObject("qzApplnYwsqb", qzApplnYwsqb);
			mv.addObject("zygys_ls", zygys_ls);
			mv.addObject("zykh_ls", zykh_ls);
			mv.addObject("jkjl_ls", jkjl_ls);
			mv.addObject("type", type);
		}
		else{
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page1", request);
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			mv.addObject("orgName",user.getOrganization().getName());
			mv.addObject("orgId",user.getOrganization().getId());
			String externalId = user.getLogin();//工号
			mv.addObject("externalId",externalId);
			mv.addObject("userName",user.getDisplayName());
			
			if (StringUtils.isNotEmpty(customerInforId)) {
				CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
				mv.addObject("customerInfor", customerInfor);
				mv.addObject("customerId", customerInfor.getId());
			}
			//查找开户信息 自动填充
			ECIF ecif = eCIFService.findEcifByClientNo(eCIFService.findEcifByCustomerId(customerInforId).getClientNo());
			mv.addObject("ecif", ecif);
			mv.addObject("type", type);
			mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		}
		
		return mv;
	}
	
	//insert_page1
	@ResponseBody
	@RequestMapping(value = "insert_page1.json")
	public JRadReturnMap insert_page1(@ModelAttribute QzApplnYwsqbForm qzApplnYwsqbForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				QzApplnYwsqb qzApplnYwsqb = qzApplnYwsqbForm.createModel(QzApplnYwsqb.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnYwsqb.setCreatedBy(user.getId());
				qzApplnYwsqb.setCreatedTime(new Date());
				//未填申请时 关联客户id
				qzApplnYwsqb.setCustomerId(customerId);
				ywsqbService.insert_page1(qzApplnYwsqb, request);
				//returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//page1
	@ResponseBody
	@RequestMapping(value = "page1_change.page")
	public AbstractModelAndView page1_change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page1_change", request);
		String customerId = request.getParameter("customerId");
		//查找page1信息
		QzApplnYwsqb qzApplnYwsqb = ywsqbService.findYwsqb(customerId, null);
		List<QzApplnYwsqbZygys> zygys_ls = ywsqbService.findYwsqbZygys(qzApplnYwsqb.getId());
		List<QzApplnYwsqbZykh> zykh_ls = ywsqbService.findYwsqbZykh(qzApplnYwsqb.getId());
		List<QzApplnYwsqbJkjl> jkjl_ls = ywsqbService.findYwsqbJkjl(qzApplnYwsqb.getId());
		mv.addObject("qzApplnYwsqb", qzApplnYwsqb);
		mv.addObject("zygys_ls", zygys_ls);
		mv.addObject("zykh_ls", zykh_ls);
		mv.addObject("jkjl_ls", jkjl_ls);
		return mv;
	}
		
	//update_page1
	@ResponseBody
	@RequestMapping(value = "update_page1.json")
	public JRadReturnMap update_page1(@ModelAttribute QzApplnYwsqbForm qzApplnYwsqbForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String ywsqbId = request.getParameter("id");
				QzApplnYwsqb qzApplnYwsqb = qzApplnYwsqbForm.createModel(QzApplnYwsqb.class);
				ywsqbService.dealWithNullValue(qzApplnYwsqb);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				//未填申请时 关联客户id
				qzApplnYwsqb.setId(ywsqbId);
				ywsqbService.update_page1(qzApplnYwsqb, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	//page4_list
	@ResponseBody
	@RequestMapping(value = "page4_list.page")
	public AbstractModelAndView page4_list(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page4_list", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		
		List<QzApplnDbrxx> dbrxx_ls = dbrxxService.findDbrxx(customerInforId, null);
		mv.addObject("dbrxx_ls", dbrxx_ls);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
			mv.addObject("type", type);
			mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		}
		return mv;
	}
	
	//page4
	@ResponseBody
	@RequestMapping(value = "page4.page")
	public AbstractModelAndView page4(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page4", request);
		String customerInforId = RequestHelper.getStringValue(request, "customerId");
		
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
			
		}
		return mv;
	}
		
	//insert_page4
	@ResponseBody
	@RequestMapping(value = "insert_page4.json")
	public JRadReturnMap insert_page4(@ModelAttribute QzApplnDbrxxForm qzApplnDbrxxForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				QzApplnDbrxx qzApplnDbrxx = qzApplnDbrxxForm.createModel(QzApplnDbrxx.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnDbrxx.setCreatedBy(user.getId());
				qzApplnDbrxx.setCreatedTime(new Date());
				//未填申请时 关联客户id
				qzApplnDbrxx.setCustomerId(customerId);
				dbrxxService.insert_page4(qzApplnDbrxx, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//update_page4
	@ResponseBody
	@RequestMapping(value = "update_page4.page")
	public AbstractModelAndView update_page4(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page4_change", request);
		String id = RequestHelper.getStringValue(request, ID);
		//查找page4信息
		QzApplnDbrxx qzApplnDbrxx = dbrxxService.findDbrxxById(id);
		List<QzApplnDbrxxDkjl> dkjl_ls = dbrxxService.findDbrxxDkjl(qzApplnDbrxx.getId());
		List<QzApplnDbrxxFc> fc_ls = dbrxxService.findDbrxxFc(qzApplnDbrxx.getId());
		List<QzApplnDbrxxJdc> jdc_ls = dbrxxService.findDbrxxJdc(qzApplnDbrxx.getId());
		mv.addObject("qzApplnDbrxx", qzApplnDbrxx);
		mv.addObject("dkjl_ls", dkjl_ls);
		mv.addObject("fc_ls", fc_ls);
		mv.addObject("jdc_ls", jdc_ls);
		return mv;
	}
		
	//update_page4
	@ResponseBody
	@RequestMapping(value = "update_page4.json")
	public JRadReturnMap update_page4(@ModelAttribute QzApplnDbrxxForm qzApplnDbrxxForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String dbrxxId = request.getParameter("id");
				QzApplnDbrxx qzApplnDbrxx = qzApplnDbrxxForm.createModel(QzApplnDbrxx.class);
				dbrxxService.dealWithNullValue(qzApplnDbrxx);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				//未填申请时 关联客户id
				qzApplnDbrxx.setId(dbrxxId);
				dbrxxService.update_page4(qzApplnDbrxx, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	//page5
	@ResponseBody
	@RequestMapping(value = "page5.page")
	public AbstractModelAndView page5(HttpServletRequest request) {
		JRadModelAndView mv = null;
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		if(appId==null){
			appId="";
		}
		//查找page5信息
		QzApplnAttachmentList qzApplnAttachmentList = attachmentListService.findAttachmentList(customerInforId, null);
		if(qzApplnAttachmentList == null){
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page5", request);
			if (StringUtils.isNotEmpty(customerInforId)) {
				CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
				mv.addObject("customerInfor", customerInfor);
				mv.addObject("appId", appId);
				mv.addObject("type", type);
				mv.addObject("customerId", customerInfor.getId());
			}
			
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String externalId = user.getLogin();//工号
			mv.addObject("externalId",externalId);
			mv.addObject("userName",user.getDisplayName());
		}
		else{
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page5_change", request);
			mv.addObject("qzApplnAttachmentList", qzApplnAttachmentList);
			mv.addObject("type", type);
			mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		}
		
		return mv;
	}
	
	//insert_page5
	@ResponseBody
	@RequestMapping(value = "insert_page5.json")
	public JRadReturnMap insert_page5(@ModelAttribute QzApplnAttachmentListForm qzApplnAttachmentListForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				QzApplnAttachmentList qzApplnAttachmentList = qzApplnAttachmentListForm.createModel(QzApplnAttachmentList.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnAttachmentList.setCreatedBy(user.getId());
				qzApplnAttachmentList.setCreatedTime(new Date());
				//未填申请时 关联客户id
				qzApplnAttachmentList.setCustomerId(customerId);
				attachmentListService.insert_page5(qzApplnAttachmentList, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
	
	//update_page5
	@ResponseBody
	@RequestMapping(value = "update_page5.json")
	public JRadReturnMap update_page5(@ModelAttribute QzApplnAttachmentListForm qzApplnAttachmentListForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String id = request.getParameter("id");
				String customerId = request.getParameter("customerId");
				QzApplnAttachmentList qzApplnAttachmentList = qzApplnAttachmentListForm.createModel(QzApplnAttachmentList.class);
				//未填申请时 关联客户id
				qzApplnAttachmentList.setId(id);
				qzApplnAttachmentList.setCustomerId(customerId);
				attachmentListService.update_page5(qzApplnAttachmentList, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//page7
	@ResponseBody
	@RequestMapping(value = "page7.page")
	public AbstractModelAndView page7(HttpServletRequest request) {
		JRadModelAndView mv = null;
		String customerInforId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		if(appId==null){
			appId="";
		}
		QzApplnNbscyjb qzApplnNbscyjb = nbscyjbService.findNbscyjb(customerInforId, null);
		if(qzApplnNbscyjb == null){
			 mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page7", request);
			
			if (StringUtils.isNotEmpty(customerInforId)) {
				CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
				mv.addObject("customerInfor", customerInfor);
				mv.addObject("customerId", customerInfor.getId());
				mv.addObject("appId", appId);
				mv.addObject("type", type);
			}
		}
		else{
			mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page7_change", request);
			mv.addObject("qzApplnNbscyjb", qzApplnNbscyjb);
			mv.addObject("type", type);
			mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		}
		
		return mv;
	}
	
	//insert_page7
	@ResponseBody
	@RequestMapping(value = "insert_page7.json")
	public JRadReturnMap insert_page5(@ModelAttribute QzApplnNbscyjbForm qzApplnNbscyjbForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = request.getParameter("customerId");
				QzApplnNbscyjb qzApplnNbscyjb = qzApplnNbscyjbForm.createModel(QzApplnNbscyjb.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnNbscyjb.setCreatedBy(user.getId());
				qzApplnNbscyjb.setCreatedTime(new Date());
				//未填申请时 关联客户id
				qzApplnNbscyjb.setCustomerId(customerId);
				nbscyjbService.insert_page7(qzApplnNbscyjb, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//update_page7
	@ResponseBody
	@RequestMapping(value = "update_page7.json")
	public JRadReturnMap update_page7(@ModelAttribute QzApplnNbscyjbForm qzApplnNbscyjbForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String id = request.getParameter("id");
				QzApplnNbscyjb qzApplnNbscyjb = qzApplnNbscyjbForm.createModel(QzApplnNbscyjb.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				qzApplnNbscyjb.setId(id);
				nbscyjbService.update_page7(qzApplnNbscyjb, request);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
		
	//page8
	@ResponseBody
	@RequestMapping(value = "page8.page")
	public AbstractModelAndView page8(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/qzbankinterface/appIframeInfo/page8", request);
		String customerId = RequestHelper.getStringValue(request, ID);
		//获取内部审查意见表信息
		QzApplnNbscyjb qzApplnNbscyjb = nbscyjbService.findNbscyjb(customerId, null);
		String appId = RequestHelper.getStringValue(request, "appId");
		String type = RequestHelper.getStringValue(request, "type");
		String operate = RequestHelper.getStringValue(request, "operate");
		//作为审批后修改标志
		if(appId==null){
			appId="";
		}
		QzApplnJyd qzSdhjyd = new QzApplnJyd();
		List<QzApplnJydGtjkr> gtjkrs = new ArrayList<QzApplnJydGtjkr>();
		List<QzApplnJydBzdb> bzdbs = new ArrayList<QzApplnJydBzdb>();
		List<QzApplnJydDydb> dydbs = new ArrayList<QzApplnJydDydb>();
		if (StringUtils.isNotEmpty(appId)) {
			qzSdhjyd = intoPiecesService.getSdhjydFormAfter(appId);
		}else{
			qzSdhjyd = intoPiecesService.getSdhjydForm(customerId);
		}
		if(qzSdhjyd!=null){
			//获取共同借款人list
			gtjkrs = intoPiecesService.getJkrList(qzSdhjyd.getId());
			//获取保证担保list
			bzdbs = intoPiecesService.getBzdbList(qzSdhjyd.getId());
			//获取抵押担保list
			dydbs = intoPiecesService.getDydbList(qzSdhjyd.getId());
		}
		mv.addObject("customerId", customerId);
		mv.addObject("appId", appId);
		mv.addObject("type", type);
		mv.addObject("result", qzSdhjyd);
		mv.addObject("qzApplnNbscyjb", qzApplnNbscyjb);
		//查找开户信息 自动填充
		ECIF ecif = eCIFService.findEcifByClientNo(eCIFService.findEcifByCustomerId(customerId).getClientNo());
		mv.addObject("ecif", ecif);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		mv.addObject("orgName",user.getOrganization().getName());
		mv.addObject("userName",user.getDisplayName());
		mv.addObject("gtjkrs", gtjkrs);
		mv.addObject("bzdbs", bzdbs);
		mv.addObject("dydbs", dydbs);
		mv.addObject("returnUrl",intoPiecesService.getReturnUrl(operate) );
		mv.addObject("operate",operate);
		return mv;
	}
	
	/**
	 * 审贷会决议单保存(申请前)
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "page8insert.json")
	public JRadReturnMap insert(@ModelAttribute QzSdhjydForm qzSdhjydForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String customerId = RequestHelper.getStringValue(request, ID);
				String appId = RequestHelper.getStringValue(request, "appId");
				QzApplnJyd qzSdhjyd = qzSdhjydForm.createModel(QzApplnJyd.class);
				qzSdhjyd.setCustomerId(customerId);
				qzSdhjyd.setCreatedTime(new Date());
				qzSdhjyd.setApplicationId(appId);
				if(StringUtils.isNotEmpty(appId)){
					intoPiecesService.insertSdhjydFormAfter(qzSdhjyd);
				}else{
					intoPiecesService.insertSdhjydForm(qzSdhjyd,request);
				}
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
	 * 进入补充上会记录页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add_information.page", method = { RequestMethod.GET })
	public AbstractModelAndView reject(@ModelAttribute CustomerApplicationProcessFilter filter, HttpServletRequest request) throws SQLException {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setLoginId(loginId);
		QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.shouxinAddInforForm(filter);
		JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);

		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/intopieces_wait/intopiecesApprove_shouxin_add_infor", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 客户经理补充上会提交
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pass.json")
	public JRadReturnMap pass(HttpServletRequest request) throws SQLException {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String customerId = request.getParameter("customerId");
			//更新客户信息状态
			CustomerInfor infor = commonDao.findObjectById(CustomerInfor.class, customerId);
			infor.setProcessId("");
			commonDao.updateObject(infor);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			returnMap.addGlobalMessage("保存失败");
			e.printStackTrace();
		}
		return returnMap;
	}
	
}
