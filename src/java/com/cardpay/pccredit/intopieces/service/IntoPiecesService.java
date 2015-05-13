package com.cardpay.pccredit.intopieces.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.intopieces.constant.ApplicationStatusEnum;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.CustomerApplicationIntopieceWaitDao;
import com.cardpay.pccredit.intopieces.dao.IntoPiecesDao;
import com.cardpay.pccredit.intopieces.dao.comdao.IntoPiecesComdao;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.MakeCardFilter;
import com.cardpay.pccredit.intopieces.model.ApplicationDataImport;
import com.cardpay.pccredit.intopieces.model.BasicCustomerInformationS;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.CustomerCareersInformationS;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.intopieces.model.QzApplnDcnr;
import com.cardpay.pccredit.intopieces.model.QzApplnProcessResult;
import com.cardpay.pccredit.intopieces.model.QzApplnSxjc;
import com.cardpay.pccredit.intopieces.model.QzShouxin;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.web.ApproveHistoryForm;
import com.cardpay.pccredit.intopieces.web.QzApplnSxjcForm;
import com.cardpay.pccredit.intopieces.web.QzDcnrUploadForm;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.workflow.dao.WfStatusResultDao;
import com.cardpay.workflow.models.WfProcessRecord;
import com.cardpay.workflow.models.WfStatusQueueRecord;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

@Service
public class IntoPiecesService {

	// TODO 路径使用相对路径，首先获得应用所在路径，之后建立上传文件目录，图片类型使用IMG，文件使用DOC

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private IntoPiecesDao intoPiecesDao;

	@Autowired
	private IntoPiecesComdao intoPiecesComdao;
	
	@Autowired
	private CustomerInforService customerInforService;
	
	@Autowired
	private DivisionalService divisionalService;
	@Autowired
	private CustomerApplicationProcessService customerApplicationProcessService;

	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private CustomerApplicationIntopieceWaitDao customerApplicationIntopieceWaitDao;
	
	@Autowired
	private WfStatusResultDao wfStatusResultDao;
	
	/* 查询进价信息 */
	/*
	 * TODO 1.添加注释 2.SQL写进DAO层
	 */
	public QueryResult<IntoPieces> findintoPiecesByFilter(
			IntoPiecesFilter filter) {
		QueryResult<IntoPieces> queryResult = intoPiecesComdao.findintoPiecesByFilter(filter);
		List<IntoPieces> intoPieces = queryResult.getItems();
		for(IntoPieces pieces : intoPieces){
			if(pieces.getStatus().equals(Constant.SAVE_INTOPICES)){
				pieces.setNodeName("未提交申请");
			} else if(pieces.getStatus().equals(Constant.APPROVE_INTOPICES)){
				String nodeName = intoPiecesComdao.findAprroveProgress(pieces.getId());
				if(StringUtils.isNotEmpty(nodeName)){
					pieces.setNodeName(nodeName);
				} else {
					pieces.setNodeName("不在审批中");
				}
			} else {
				pieces.setNodeName("审批结束");
			}
		}
		return queryResult;
	}
	
	/*
	 * 查询已经申请通过进件
	 */
	public QueryResult<IntoPieces> findintoApplayPiecesByFilter(
			IntoPiecesFilter filter) {
		return intoPiecesComdao.findintoApplayPiecesByFilter(filter);
	}
	
	/*
	 * 判断经理是否有权操作，否则移交
	 */
	public int checkValue(String userId,String valueType) {
		return intoPiecesDao.checkValue(userId,valueType);
	}
	
	/*
	 * 查询已经申请通过进件
	 */
	public QueryResult<MakeCard> findCardByFilter(
			MakeCardFilter filter) {
		return intoPiecesComdao.findCardByFilter(filter);
	}

	/* 保存客户所有资料(主表及其其他的表) */
	/*
	 * TODO 1.文件上传使用另外的工具类进行操作
	 * 2.文件上传文件与服务器端文件名相同使用删除极为不正确，应每次对文件进行重命名，使用UUID作为文件名
	 * 3.文件上传设置类型后缀枚举类型，对不在枚举中的文件类型不进行上传(不仅仅是前端检查)
	 * 4.使用stringbuffer作为文件缓存，不要使用类似byte[8192]这种 5.注释应标明输入输出，异常抛出类型
	 */
	public void saveAllInfo(List<BusinessModel> list,
			HttpServletRequest request, String userId, String status,
			Map<String, Object> paramMap){
		if (list != null && list.size() > 0) {
			for (BusinessModel obj : list) {
				obj.setCreatedTime(new Date());
				obj.setCreatedBy(userId);
				if (!(obj instanceof AddressAccessories)) {
					if(obj instanceof CustomerInfor){
						CustomerInfor customerInfor = (CustomerInfor)obj;
						/* 判断是否需要移交进件 */
						if ((Boolean) paramMap.get("checkFlag")&&(Boolean)paramMap.get("flag")) {
							divisionalService.insertDivisionalCustomer(
									customerInfor.getId(),
									DivisionalTypeEnum.initiative,
									DivisionalProgressEnum.charge);
						}
					}
					commonDao.insertObject(obj);
				} else if (obj instanceof AddressAccessories) {
					AddressAccessories v = (AddressAccessories) obj;
					String fileName = UploadFileTool.uploadFileBySpring(
							request, v);
					if (StringUtils.trimToNull(fileName) != null) {
						v.setProductAccessoriesUrl(Constant.FILE_PATH
								+ fileName);
					}
					commonDao.insertObject(v);
				}
			}
		}
		/* 生成快照 */
		if (StringUtils.trimToNull(status) != null
				&& Constant.APPROVE_INTOPICES.equalsIgnoreCase(status)
				&& (Boolean)paramMap.get("flag")) {
			this.cloneData(paramMap.get("customerId").toString(),
					paramMap.get("applicationId").toString(), paramMap.get("productId").toString());
			paramMap.put("flag", false);
		}
	}

	/* 更新客户所有信息 */
	public void updateAllInfo(List<BusinessModel> list,
			HttpServletRequest request, String userId, String status,Map<String, Object> paramMap){
		if (list != null && list.size() > 0) {
			for (BusinessModel obj : list) {
				obj.setModifiedTime(new Date());
				obj.setModifiedBy(userId);
				if (!(obj instanceof AddressAccessories)) {
					if(obj instanceof CustomerInfor){
						CustomerInfor customerInfor = (CustomerInfor)obj;
						/* 判断是否需要移交进件 */
						if ((Boolean) paramMap.get("checkFlag")&&(Boolean)paramMap.get("flag")) {
							divisionalService.insertDivisionalCustomer(
									customerInfor.getId(),
									DivisionalTypeEnum.initiative,
									DivisionalProgressEnum.charge);
						}
					}
					commonDao.updateObject(obj);
				} else {
					AddressAccessories v = (AddressAccessories) obj;
					String fileName = UploadFileTool.uploadFileBySpring(
							request, v);
					if (StringUtils.trimToNull(fileName) != null) {
						v.setProductAccessoriesUrl(Constant.FILE_PATH
								+ fileName);
					}
					commonDao.updateObject(v);
				}
			}
		}
		/*生成快照*/
		if (StringUtils.trimToNull(status) != null
				&& Constant.APPROVE_INTOPICES.equalsIgnoreCase(status)&&(Boolean)paramMap.get("flag")) {
			this.cloneData(paramMap.get("customerId").toString(),
					paramMap.get("applicationId").toString(), paramMap.get("productId").toString());
			paramMap.put("flag", false);
		}
	}
	
	
	/* 申请件待审核中维护进件
	 * 注：基本资料 和 客户职业信息资料 更新快照表中数据
	 * 更新客户所有信息 
	 * 
	 * author：王海东
	 * */
	public void updateAllInfoWait(List<BusinessModel> list,
			HttpServletRequest request, String userId, String status,Map<String, Object> paramMap){
		if (list != null && list.size() > 0) {
			for (BusinessModel obj : list) {
				obj.setModifiedTime(new Date());
				obj.setModifiedBy(userId);
				if (!(obj instanceof AddressAccessories)) {
					if(obj instanceof BasicCustomerInformationS){
						BasicCustomerInformationS customerInfor = (BasicCustomerInformationS)obj;
					    this.updateBasicCustomerInformationS(customerInfor);
					}
					if(obj instanceof CustomerCareersInformationS){
						CustomerCareersInformationS customerCareersInformationS = (CustomerCareersInformationS)obj;
					    this.updateCustomerCareersInformationS(customerCareersInformationS);
					}
					commonDao.updateObject(obj);
				} else {
					AddressAccessories v = (AddressAccessories) obj;
					String fileName = UploadFileTool.uploadFileBySpring(
							request, v);
					if (StringUtils.trimToNull(fileName) != null) {
						v.setProductAccessoriesUrl(Constant.FILE_PATH
								+ fileName);
					}
					commonDao.updateObject(v);
				}
			}
		}
	}
	/*
	 * 更新客户基本信息快照表BASIC_CUSTOMER_INFORMATION_S
	 * 
	 */
	public int updateBasicCustomerInformationS(BasicCustomerInformationS basicCustomerInformationS){
		return commonDao.updateObject(basicCustomerInformationS);
	}
	/*
	 * 更新客户职业信息快照表CUSTOMER_CAREERS_INFORMATION_S
	 */
	public int updateCustomerCareersInformationS(CustomerCareersInformationS customerCareersInformationS) {
		return commonDao.updateObject(customerCareersInformationS);
		
	}
	private void cloneData(String customerId,String applicationId,String productId){
		customerInforService.insertCustomerInfoBySubmitApp(
				customerId, applicationId,productId);
	}

	/* 客户证件ID模糊匹配 */
	/*
	 * TODO 1.注释标明输入输出，异常抛出类型 2.SQL写进DAO层
	 */
	public void selectLikeByCardId(HttpServletResponse response,
			String tempParam) throws Exception {
		intoPiecesComdao.selectLikeByCardId(response, tempParam);
	}
	
	/*
	 * TODO 1.注释标明输入输出，异常抛出类型 2.SQL写进DAO层
	 */
	public void delete(String name,String value) throws Exception {
		if(Constant.CONTACTID.equals(name)){
			commonDao.deleteObject(CustomerApplicationContact.class, value);
		}else if(Constant.GUARANTORID.equals(name)){
			commonDao.deleteObject(CustomerApplicationGuarantor.class, value);
		}else if(Constant.RECOMMENDID.equals(name)){
			commonDao.deleteObject(CustomerApplicationRecom.class, value);
		}else{
			throw new Exception("该方法只用来删除联系人，担保人，推荐人");
		}
	}
	
	/*
	 * 导出文本格式的文件并且上传到ftp服务器上
	 */
	public void exportData(String applicationId, String customerId,HttpServletResponse response) throws Exception {
		StringBuffer content = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		List<CustomerApplicationContact> customerApplicationContactList = null;
		List<CustomerApplicationGuarantor> customerApplicationGuarantorList = null;
		List<CustomerApplicationRecom> customerApplicationRecomList = null;
		CustomerInfor customerInfor = this.findCustomerInforById(customerId);
		customerInfor = customerInfor==null?new CustomerInfor():customerInfor;
		CustomerCareersInformation customerCareersInformation = this.findCustomerCareersInformationByCustomerId(customerId);
		customerCareersInformation = customerCareersInformation==null?new CustomerCareersInformation():customerCareersInformation;
		CustomerApplicationInfo customerApplicationInfo = this.findCustomerApplicationInfoByApplicationId(applicationId);
		customerApplicationInfo = customerApplicationInfo==null?new CustomerApplicationInfo():customerApplicationInfo;
		List<CustomerApplicationContact> customerApplicationContactListDB = this.findCustomerApplicationContactsByApplicationId(applicationId);
		customerApplicationContactListDB = customerApplicationContactListDB==null?new ArrayList<CustomerApplicationContact>():customerApplicationContactListDB;
		List<CustomerApplicationGuarantor> customerApplicationGuarantorListDB = this.findCustomerApplicationGuarantorsByApplicationId(applicationId);
		customerApplicationGuarantorListDB = customerApplicationGuarantorListDB==null?new ArrayList<CustomerApplicationGuarantor>():customerApplicationGuarantorListDB;
		List<CustomerApplicationRecom> customerApplicationRecomListDB = this.findCustomerApplicationRecomsByApplicationId(applicationId);
		customerApplicationRecomListDB = customerApplicationRecomListDB==null?new ArrayList<CustomerApplicationRecom>():customerApplicationRecomListDB;
		CustomerApplicationOther customerApplicationOther = this.findCustomerApplicationOtherByApplicationId(applicationId);
		customerApplicationOther = customerApplicationOther==null?new CustomerApplicationOther():customerApplicationOther;
		CustomerAccountData customerAccountData = this.findCustomerAccountDataByApplicationId(applicationId);
		customerAccountData = customerAccountData==null?new CustomerAccountData():customerAccountData;
		CustomerApplicationCom customerApplicationCom = this.findCustomerApplicationComByApplicationId(applicationId);
		customerApplicationCom = customerApplicationCom==null?new CustomerApplicationCom():customerApplicationCom;
		List<ApplicationDataImport> applicationDataImportList = this.findApplicationDataImport();
		if(customerApplicationContactListDB!=null){
			customerApplicationContactList = customerApplicationContactListDB;
			if(customerApplicationContactList.size()!=2){
				for(int i=customerApplicationContactList.size();i<2;i++){
					customerApplicationContactList.add(new CustomerApplicationContact());
				}
			}
		}
		if(customerApplicationGuarantorListDB!=null){
			customerApplicationGuarantorList = customerApplicationGuarantorListDB;
			if(customerApplicationGuarantorList.size()!=2){
				for(int i=customerApplicationGuarantorList.size();i<2;i++){
					customerApplicationGuarantorList.add(new CustomerApplicationGuarantor());
				}
			}
		}
		if(customerApplicationRecomListDB!=null){
			customerApplicationRecomList = customerApplicationRecomListDB;
			if(customerApplicationRecomList.size()!=2){
				for(int i=customerApplicationRecomList.size();i<2;i++){
					customerApplicationRecomList.add(new CustomerApplicationRecom());
				}
			}
		}
		for(int i=0;i<applicationDataImportList.size();i++){
			ApplicationDataImport applicationDataImport = applicationDataImportList.get(i);
			int id = Integer.valueOf(applicationDataImport.getId());
			int length = Integer.valueOf(applicationDataImport.getFieldLength());
			switch(id-1){
			    case 0:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 1:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 2:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 3:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 4:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 5:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 6:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 7:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 8:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 9:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 10:content = UploadFileTool.getContent(content,customerInfor.getCardType(),length);break;
			    case 11:content = UploadFileTool.getContent(content,customerInfor.getCardId(),length);break;
			    case 12:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 13:content = UploadFileTool.getContent(content,customerInfor.getChineseName(),length);break;
			    case 14:content = UploadFileTool.getContent(content,customerInfor.getPinyinenglishName(),length);break;
			    case 15:content = UploadFileTool.getContent(content,customerInfor.getSex(),length);break;
			    case 16:content = UploadFileTool.getContent(content,customerInfor.getBirthday(),length);break;
			    case 17:content = UploadFileTool.getContent(content,customerInfor.getNationality(),length);break;
			    case 18:content = UploadFileTool.getContent(content,customerInfor.getMaritalStatus(),length);break;
			    case 19:content = UploadFileTool.getContent(content,customerInfor.getDegreeEducation(),length);break;
			    case 20:content = UploadFileTool.getContent(content,customerCareersInformation.getTitle(),length);break;
			    case 21:content = UploadFileTool.getContent(content,customerInfor.getHomePhone(),length);break;
			    case 22:content = UploadFileTool.getContent(content,customerInfor.getTelephone(),length);break;
			    case 23:content = UploadFileTool.getContent(content,customerInfor.getMail(),length);break;
			    case 24:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 25:content = UploadFileTool.getContent(content,customerInfor.getResidentialAddress(),length);break;
			    case 26:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 27:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 28:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 29:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 30:content = UploadFileTool.getContent(content,customerInfor.getResidentialPropertie(),length);break;
			    case 31:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 32:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 33:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 34:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 35:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 36:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 37:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 38:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 39:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 40:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 41:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 42:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 43:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 44:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 45:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 46:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 47:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 48:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 49:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 50:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 51:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 52:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 53:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 54:content = UploadFileTool.getContent(content,customerCareersInformation.getPositio(),length);break;
			    case 55:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 56:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 57:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 58:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 59:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 60:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 61:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 62:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 63:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 64:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 65:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 66:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 67:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 68:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 69:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 70:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 71:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 72:content = UploadFileTool.getContent(content,customerApplicationContactList.get(0).getContactName(),length);break;
			    case 73:content = UploadFileTool.getContent(content,customerApplicationContactList.get(0).getContactPhone(),length);break;
			    case 74:content = UploadFileTool.getContent(content,customerApplicationContactList.get(0).getRelationshipWithApplicant(),length);break;
			    case 75:content = UploadFileTool.getContent(content,customerApplicationContactList.get(0).getUnitName(),length);break;
			    case 76:content = UploadFileTool.getContent(content,customerApplicationContactList.get(0).getCellPhone(),length);break;
			    case 77:content = UploadFileTool.getContent(content,customerApplicationContactList.get(1).getContactName(),length);break;
			    case 78:content = UploadFileTool.getContent(content,customerApplicationContactList.get(1).getContactPhone(),length);break;
			    case 79:content = UploadFileTool.getContent(content,customerApplicationContactList.get(1).getRelationshipWithApplicant(),length);break;
			    case 80:content = UploadFileTool.getContent(content,customerApplicationContactList.get(1).getUnitName(),length);break;
			    case 81:content = UploadFileTool.getContent(content,customerApplicationContactList.get(1).getCellPhone(),length);break;
			    case 82:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 83:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 84:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 85:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 86:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 87:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 88:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 89:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 90:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 91:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 92:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 93:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 94:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 95:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 96:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 97:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 98:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 99:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 100:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 101:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 102:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 103:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 104:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 105:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 106:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 107:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 108:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 109:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 110:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 111:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 112:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 113:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 114:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 115:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 116:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 117:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 118:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 119:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 120:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 121:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 122:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 123:content = UploadFileTool.getContent(content,customerApplicationGuarantorList.get(0).getDocumentNumber(),length);break;
			    case 124:content = UploadFileTool.getContent(content,customerApplicationGuarantorList.get(0).getGuarantorMortgagorPledge(),length);break;
			    case 125:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 126:content = UploadFileTool.getContent(content,customerApplicationGuarantorList.get(0).getSex(),length);break;
			    case 127:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 128:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 129:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 130:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 131:content = UploadFileTool.getContent(content,customerApplicationGuarantorList.get(0).getContactPhone(),length);break;
			    case 132:content = UploadFileTool.getContent(content,customerApplicationGuarantorList.get(0).getCellPhone(),length);break;
			    case 133:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 134:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 135:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 136:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 137:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 138:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 139:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 140:content = UploadFileTool.getContent(content,customerApplicationGuarantorList.get(0).getUnitName(),length);break;
			    case 141:content = UploadFileTool.getContent(content,customerApplicationGuarantorList.get(0).getDepartment(),length);break;
			    case 142:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 143:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 144:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 145:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 146:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 147:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 148:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 149:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 150:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 151:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 152:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 153:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 154:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 155:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 156:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 157:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 158:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 159:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 160:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 161:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 162:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 163:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 164:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 165:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 166:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 167:content = UploadFileTool.getContent(content,customerApplicationRecomList.get(0).getName(),length);break;
			    case 168:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 169:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 170:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 171:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 172:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 173:content = UploadFileTool.getContent(content,customerApplicationInfo.getBillingDate(),length);break;
			    case 174:content = UploadFileTool.getContent(content,customerApplicationOther.getPaperBillingShippingAddress(),length);break;
			    case 175:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 176:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 177:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 178:content = UploadFileTool.getContent(content,customerApplicationOther.getConsumptionUsePassword(),length);break;
			    case 179:content = UploadFileTool.getContent(content,customerApplicationOther.getSmsOpeningTrading(),length);break;
			    case 180:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 181:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 182:content = UploadFileTool.getContent(content,customerApplicationOther.getBillingMethod(),length);break;
			    case 183:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 184:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 185:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 186:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 187:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 188:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 189:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 190:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 191:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 192:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 193:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 194:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 195:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 196:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 197:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 198:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 199:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 200:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 201:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 202:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 203:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 204:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 205:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 206:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 207:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 208:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 209:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 210:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 211:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 212:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 213:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 214:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 215:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 216:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 217:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 218:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 219:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 220:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 221:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 222:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 223:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 224:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 225:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 226:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 227:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 228:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 229:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 230:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 231:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 232:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 233:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 234:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 235:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 236:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 237:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 238:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 239:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 240:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 241:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 242:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 243:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 244:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 245:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 246:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 247:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 248:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 249:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 250:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 251:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 252:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 253:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 254:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 255:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 256:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 257:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 258:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 259:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 260:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 261:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 262:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 263:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 264:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 265:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 266:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 267:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 268:content = UploadFileTool.getContent(content,customerApplicationInfo.getCashAdvanceProportion(),length);break;
			    case 269:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 270:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 271:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 272:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 273:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 274:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 275:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 276:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 277:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 278:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 279:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 280:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 281:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 282:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 283:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 284:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 285:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 286:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 287:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 288:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 289:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 290:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 291:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 292:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 293:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 294:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 295:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 296:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 297:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 298:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 299:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 300:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 301:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 302:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 303:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 304:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 305:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 306:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 307:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 308:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 309:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 310:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 311:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 312:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 313:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 314:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 315:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 316:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 317:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 318:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 319:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 320:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 321:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 322:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 323:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 324:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 325:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 326:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 327:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 328:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 329:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 330:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 331:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 332:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 333:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 334:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 335:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 336:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 337:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 338:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 339:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 340:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 341:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 342:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 343:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 344:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 345:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    case 346:content = UploadFileTool.getContent(content,sb.toString(),length);break;
			    default:break;
			}
		}
		String fileName = customerInfor.getChineseName()+"("+customerInfor.getCardId()+").txt";
		/*生成的接口数据上传到ftp文件上*/
		UploadFileTool.uploadFileToFtp(Constant.FTPIP, Integer.valueOf(Constant.FTPPORT), Constant.FTPUSERNAME, Constant.FTPPASSWORD, Constant.FTPPATH, fileName, content.toString());
		/*如果要下载接口数据,将下面的exportTextFile方法打开*/
		/*if(response!=null){
			UploadFileTool.exportTextFile(response, content.toString(), fileName);
		}*/
		/*上传成功后讲进件信息主表的状态置为已上传状态*/
		if(customerApplicationInfo!=null&&StringUtils.trimToNull(customerApplicationInfo.getId())!=null){
			/*更新上传状态为防止影响到别的属性，这里new一个新实例*/
			CustomerApplicationInfo temp = new CustomerApplicationInfo();
			temp.setId(customerApplicationInfo.getId());
			temp.setUploadStatus(Constant.UPLOAD_INTOPICES);
			commonDao.updateObject(temp);
		}
		
	}

	/* 根据客户id查询客户职业资料 */
	public CustomerCareersInformation findCustomerCareersInformationByCustomerId(
			String customerId) {
		return intoPiecesComdao
				.findCustomerCareersInformationByCustomerId(customerId);
	}

	/* 根据id查询联系人资料 */
	public List<CustomerApplicationContact> findCustomerApplicationContactsByApplicationId(
			String applicationId) {
		return intoPiecesComdao
				.findCustomerApplicationContactsByApplicationId(applicationId);
	}

	/* 根据id查询推荐人资料 */
	public List<CustomerApplicationRecom> findCustomerApplicationRecomsByApplicationId(
			String applicationId) {
		return intoPiecesComdao
				.findCustomerApplicationRecomsByApplicationId(applicationId);
	}

	/* 根据id查询担保人资料 */
	public List<CustomerApplicationGuarantor> findCustomerApplicationGuarantorsByApplicationId(
			String applicationId) {
		return intoPiecesComdao
				.findCustomerApplicationGuarantorsByApplicationId(applicationId);
	}

	/* 根据id查询申请的主表信息 */
	public CustomerApplicationInfo findCustomerApplicationInfoByApplicationId(
			String applicationId) {
		return intoPiecesComdao
				.findCustomerApplicationInfoByApplicationId(applicationId);
	}

	/* 根据id查询申请主表信息 的其他资料信息 */
	public CustomerApplicationOther findCustomerApplicationOtherByApplicationId(
			String applicationId) {
		return intoPiecesComdao
				.findCustomerApplicationOtherByApplicationId(applicationId);
	}

	/* 根据id查询申请主表信息 的行社专栏信息 */
	public CustomerApplicationCom findCustomerApplicationComByApplicationId(
			String applicationId) {
		return intoPiecesComdao
				.findCustomerApplicationComByApplicationId(applicationId);
	}
	
	/* 根据id查询客户账户信息 */
	public CustomerAccountData findCustomerAccountDataByApplicationId(
			String applicationId) {
		return intoPiecesComdao
				.findCustomerAccountDataByApplicationId(applicationId);
	}
	

	/* 根据客户id查询客户申请主表信息 的影像资料信息 */
	public VideoAccessories findVideoAccessoriesByCustomerId(String customerId) {
		return intoPiecesComdao.findVideoAccessoriesByCustomerId(customerId);
	}

	/* 校验客户是否存在，如果存在这里需要更新客户信息 */
	public CustomerInfor findCustomerInforById(String id) {
		return intoPiecesComdao.findCustomerInforById(id);
	}

	/* 校验职业信息是否存在，如果存在这里需要更新该客户信息 */
	public CustomerCareersInformation findCustomerCareersInformationById(
			String id) {
		return intoPiecesComdao.findCustomerCareersInformationById(id);
	}

	/* 信息主表信息是否存在，如果存在则更新信息 */
	public CustomerApplicationInfo findCustomerApplicationInfoById(String id) {
		return intoPiecesComdao.findCustomerApplicationInfoById(id);
	}

	/* 查询申请的某一笔进件申请单中上传的产品的附件 */
	public List<AddressAccessories> findAddressAccessories(
			String applicationId, String productId) {
		return intoPiecesComdao
				.findAddressAccessories(applicationId, productId);
	}
	
	/* 查找接口字段配置表 */
	public List<ApplicationDataImport> findApplicationDataImport() {
		return intoPiecesComdao.findApplicationDataImport();
	}
	
	
	/* 查找接口字段配置表 */
	public void saveCard(MakeCard makeCard) {
		commonDao.insertObject(makeCard);
	}
	
	/* 卡片下发 */
	public void organizationIssuedCard(String id,String cardOrganizationStatus,String cardCustomerStatus) {
		MakeCard makeCard = new MakeCard();
		makeCard.setId(id);
		if(StringUtils.trimToNull(cardOrganizationStatus)!=null){
			makeCard.setCardOrganizationStatus(cardOrganizationStatus);
		}
		if(StringUtils.trimToNull(cardCustomerStatus)!=null){
			makeCard.setCardCustomerStatus(cardCustomerStatus);
		}
		commonDao.updateObject(makeCard);
	}
	
	/* 查看卡片信息 */
	public MakeCard findMakeCardById(String id) {
		return commonDao.findObjectById(MakeCard.class, id);
	}
	
	/**
	 * 
	 * @param id
	 * @param dataType application 进件 amountadjustment 调额信息
	 * @return
	 */
	public List<ApproveHistoryForm> findApplicationDataImport(String id, String dataType) {
		return intoPiecesComdao.findApproveHistoryByDataId(id, dataType);
	}
	
	public boolean checkApplyQuota(float applyQuota,String userId,String productId){
		boolean flag = false;
		Float floatDb = intoPiecesComdao.checkApplyQuota(userId, productId);
		if(floatDb!=null){
			if(applyQuota<=floatDb.floatValue()){
				flag = true;
			}
		}
		return flag;
	}
	
	/*
	 * 添加三性检测，并进入下一节点行政岗-初
	 */
	public void addSxjc(QzApplnSxjcForm filter,HttpServletRequest request) throws Exception{
		QzApplnSxjc sxjc = commonDao.findObjectById(QzApplnSxjc.class, filter.getApplicationId());
		if(sxjc==null){
			sxjc = new QzApplnSxjc();
			sxjc.setApplication_id(filter.getApplicationId());
		}
		sxjc.setReality(filter.getReality());
		sxjc.setComplete(filter.getComplete());
		sxjc.setStandard(filter.getStandard());
		commonDao.insertObject(sxjc);
		CustomerApplicationProcess process =  customerApplicationProcessService.findByAppId(filter.getApplicationId());
		request.setAttribute("serialNumber", process.getSerialNumber());
		request.setAttribute("applicationId", process.getApplicationId());
		request.setAttribute("applicationStatus", ApplicationStatusEnum.APPROVE);
		request.setAttribute("objection", "false");
		request.setAttribute("examineAmount", "");
		customerApplicationIntopieceWaitService.updateCustomerApplicationProcessBySerialNumberApplicationInfo1(request);
	}
	
	/* 初审节点退回进件 */
	/*
	 * TODO 1.添加注释 2.SQL写进DAO层
	 */
	public void checkDoNot(QzApplnSxjcForm filter) throws Exception{
		QzApplnSxjc sxjc = commonDao.findObjectById(QzApplnSxjc.class, filter.getApplicationId());
		if(sxjc==null){
			sxjc = new QzApplnSxjc();
			sxjc.setApplication_id(filter.getApplicationId());
		}
		sxjc.setReality(filter.getReality());
		sxjc.setComplete(filter.getComplete());
		sxjc.setStandard(filter.getStandard());
		commonDao.insertObject(sxjc);
		//获取进件信息
		CustomerApplicationInfo applicationInfo= commonDao.findObjectById(CustomerApplicationInfo.class, filter.getApplicationId());
		//获取客户信息
		CustomerInfor infor = commonDao.findObjectById(CustomerInfor.class, applicationInfo.getCustomerId());
		//删除申请表信息
		commonDao.deleteObject(CustomerApplicationInfo.class, filter.getApplicationId());
		//删除流程表信息
		commonDao.queryBySql("delete from customer_application_process where application_id='"+filter.getApplicationId()+"'", null);
		//更新客户信息--退回
		infor.setProcessId("1");
		commonDao.updateObject(infor);
	}
	
	/**
	 * 根据进件id获取调查内容附件记录
	 */
	public List<QzDcnrUploadForm> getUploadList(String appId){
		CustomerApplicationInfo infor = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
		String sql="SELECT A .id ,a.CUSTOMER_ID ,a.REPORT_ID ,a.REPORT_NAME ,";
				sql+=" a.LOAD_STATUS ,a.APPLICATION_ID ,a.hetong_id,a.user_name,a.card_id,b.id as upload_id,b.FILE_NAME ,";
				sql+="b.REMARK   FROM QZ_APPLN_DCNR A LEFT JOIN VIDEO_ACCESSORIES b on b.dcnr_id=a.id WHERE";
				sql+=" A .CUSTOMER_ID = '"+infor.getCustomerId()+"'";
		return commonDao.queryBySql(QzDcnrUploadForm.class,sql, null);
	}
	
	/**
	 * 保存调查内容附件
	 * @param customerId
	 */
	public void saveDcnrByCustomerId(String appId,String dcnrId,String remark, MultipartFile file) {
		CustomerApplicationInfo infor = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file);
		String fileName = map.get("fileName");
		String url = map.get("url");
		//先删除之前记录
		List<VideoAccessories> video = commonDao.queryBySql(VideoAccessories.class,"select * from video_accessories where customer_id='"+infor.getCustomerId()+"' and dcnr_id='"+dcnrId+"'", null);
		if(video.size()>0){
			customerInforservice.deleteYxzlById(video.get(0).getId());
		}
		VideoAccessories videoAccessories = new VideoAccessories();
		videoAccessories.setId(IDGenerator.generateID());
		videoAccessories.setCustomerId(infor.getCustomerId());
		videoAccessories.setRemark(remark);
		videoAccessories.setCreatedTime(new Date());
		videoAccessories.setDcnrId(dcnrId);
		if (StringUtils.trimToNull(url) != null) {
			videoAccessories.setServerUrlPath(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			videoAccessories.setFileName(fileName);
		}
		commonDao.insertObject(videoAccessories);
		QzApplnDcnr dcnr = commonDao.findObjectById(QzApplnDcnr.class, dcnrId);
		//设置已上传
		dcnr.setLoadStatus("1");
		dcnr.setCardId(null);
		dcnr.setUserName(null);
		dcnr.setHetongId(null);
		commonDao.updateObject(dcnr);
	}
	
	/**
	 * 根据进件id获取授信决议单form
	 */
	public List<QzShouxin> getShouxinform(String appId){
		String sql="select * from qz_appln_sxjyd where application_id='"+appId+"'";
		return commonDao.queryBySql(QzShouxin.class,sql, null);
	}
	
	/**
	 * 保存授信决议单form
	 */
	public void insertShouxinForm(QzShouxin qzShouxin,String appId){
		String sql="select * from qz_appln_sxjyd where application_id='"+appId+"'";
		List<QzShouxin> qz = commonDao.queryBySql(QzShouxin.class,sql, null);
		if(qz.size()>0){
			commonDao.deleteObject(QzShouxin.class, qz.get(0).getId());
		}
			commonDao.insertObject(qzShouxin);
	}
	
	/**
	 * 保存决议单
	 * @param customerId
	 */
	public void saveJydByCustomerId(String appId,String remark, MultipartFile file) {
		CustomerApplicationInfo infor = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file);
		String fileName = map.get("fileName");
		String url = map.get("url");
		//先查询是否存在决议单记录
		List<QzApplnDcnr> dcnrList = commonDao.queryBySql(QzApplnDcnr.class,"select * from qz_appln_dcnr where application_id='"+appId+"' and report_id='"+Constant.jyd_id+"'", null);
		if(dcnrList.size()>0){
			//先删除影像资料表之前记录
			String dcnrId = dcnrList.get(0).getId();
			List<VideoAccessories> video = commonDao.queryBySql(VideoAccessories.class,"select * from video_accessories where customer_id='"+infor.getCustomerId()+"' and dcnr_id='"+dcnrId+"'", null);
			if(video.size()>0){
				customerInforservice.deleteYxzlById(video.get(0).getId());
			}
			//添加影像资料表
			VideoAccessories videoAccessories = new VideoAccessories();
			videoAccessories.setId(IDGenerator.generateID());
			videoAccessories.setCustomerId(infor.getCustomerId());
			videoAccessories.setRemark(remark);
			videoAccessories.setCreatedTime(new Date());
			videoAccessories.setDcnrId(dcnrId);
			if (StringUtils.trimToNull(url) != null) {
				videoAccessories.setServerUrlPath(url);
			}
			if (StringUtils.trimToNull(fileName) != null) {
				videoAccessories.setFileName(fileName);
			}
			commonDao.insertObject(videoAccessories);
		}else{
			//添加记录表
			QzApplnDcnr dcnr = new QzApplnDcnr();
			dcnr.setApplicationId(appId);
			dcnr.setCustomerId(infor.getCustomerId());
			dcnr.setReportId(Constant.jyd_id);
			dcnr.setReportName("授信决议单");
			dcnr.setLoadStatus("1");
			dcnr.setCardId(null);
			dcnr.setUserName(null);
			dcnr.setHetongId(null);
			commonDao.insertObject(dcnr);
			//添加影像资料表
			VideoAccessories videoAccessories = new VideoAccessories();
			videoAccessories.setId(IDGenerator.generateID());
			videoAccessories.setCustomerId(infor.getCustomerId());
			videoAccessories.setRemark(remark);
			videoAccessories.setCreatedTime(new Date());
			videoAccessories.setDcnrId(dcnr.getId());
			if (StringUtils.trimToNull(url) != null) {
				videoAccessories.setServerUrlPath(url);
			}
			if (StringUtils.trimToNull(fileName) != null) {
				videoAccessories.setFileName(fileName);
			}
			commonDao.insertObject(videoAccessories);
		}

	}
	
	/**
	 * 保存合同单
	 * @param customerId
	 */
	public void saveHtByCustomerId(String appId,String remark,String hetongId,String userName,String cardId, MultipartFile file) {
		CustomerApplicationInfo infor = commonDao.findObjectById(CustomerApplicationInfo.class, appId);
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file);
		String fileName = map.get("fileName");
		String url = map.get("url");
		//先查询是否存在决议单记录
		List<QzApplnDcnr> dcnrList = commonDao.queryBySql(QzApplnDcnr.class,"select * from qz_appln_dcnr where application_id='"+appId+"' and report_id='"+Constant.htd_id+"'", null);
		if(dcnrList.size()>0){
			//先删除影像资料表之前记录
			String dcnrId = dcnrList.get(0).getId();
			List<VideoAccessories> video = commonDao.queryBySql(VideoAccessories.class,"select * from video_accessories where customer_id='"+infor.getCustomerId()+"' and dcnr_id='"+dcnrId+"'", null);
			if(video.size()>0){
				customerInforservice.deleteYxzlById(video.get(0).getId());
			}
			//修改调查内容记录
			QzApplnDcnr dcnr = dcnrList.get(0);
			dcnr.setCardId(cardId);
			dcnr.setUserName(userName);
			dcnr.setHetongId(hetongId);
			commonDao.updateObject(dcnr);
			//添加影像资料表
			VideoAccessories videoAccessories = new VideoAccessories();
			videoAccessories.setId(IDGenerator.generateID());
			videoAccessories.setCustomerId(infor.getCustomerId());
			videoAccessories.setRemark(remark);
			videoAccessories.setCreatedTime(new Date());
			videoAccessories.setDcnrId(dcnrId);
			if (StringUtils.trimToNull(url) != null) {
				videoAccessories.setServerUrlPath(url);
			}
			if (StringUtils.trimToNull(fileName) != null) {
				videoAccessories.setFileName(fileName);
			}
			commonDao.insertObject(videoAccessories);
		}else{
			//添加记录表
			QzApplnDcnr dcnr = new QzApplnDcnr();
			dcnr.setApplicationId(appId);
			dcnr.setCustomerId(infor.getCustomerId());
			dcnr.setReportId(Constant.htd_id);
			dcnr.setReportName("合同单");
			dcnr.setLoadStatus("1");
			dcnr.setHetongId(hetongId);
			dcnr.setUserName(userName);
			dcnr.setCardId(cardId);
			commonDao.insertObject(dcnr);
			//添加影像资料表
			VideoAccessories videoAccessories = new VideoAccessories();
			videoAccessories.setId(IDGenerator.generateID());
			videoAccessories.setCustomerId(infor.getCustomerId());
			videoAccessories.setRemark(remark);
			videoAccessories.setCreatedTime(new Date());
			videoAccessories.setDcnrId(dcnr.getId());
			if (StringUtils.trimToNull(url) != null) {
				videoAccessories.setServerUrlPath(url);
			}
			if (StringUtils.trimToNull(fileName) != null) {
				videoAccessories.setFileName(fileName);
			}
			commonDao.insertObject(videoAccessories);
		}

	}
	
	/*
	 * 退件（退到上一节点）
	 */
	public void returnAppln(String applicationId,HttpServletRequest request) throws Exception{
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		
		//通过申请表ID获取流程表
		CustomerApplicationProcess process =  customerApplicationProcessService.findByAppId(applicationId);
		//插入流程log表
		insertProcessLog(applicationId,Constant.APPLN_TYPE_2,request,request.getParameter("remark"),process);
		
		//通过流程表的当前节点获取上一节点
		NodeControl nodeControl = customerApplicationProcessService.getLastStatus(process.getNextNodeId());
		//更新业务流程表
		process.setNextNodeId(nodeControl.getCurrentNode());
		process.setAuditUser(loginId);
		process.setCreatedTime(new Date());
	    customerApplicationIntopieceWaitDao.updateCustomerApplicationProcessBySerialNumber(process);
	    
	    //更新流程备份表
	    
	   //查找当前所处流转状态
  		WfProcessRecord wfProcessRecord = commonDao.findObjectById(WfProcessRecord.class, process.getSerialNumber());
		WfStatusQueueRecord wfStatusQueueRecord = commonDao.findObjectById(WfStatusQueueRecord.class,wfProcessRecord.getWfStatusQueueRecord());
		//查找上一节点
		String beforeStatus = wfStatusQueueRecord.getBeforeStatus();
		//通过上一节点获取上一流程
		WfStatusQueueRecord befoRecord = wfStatusResultDao.getLastStatus(beforeStatus);
		
		wfProcessRecord.setWfStatusQueueRecord(befoRecord.getId());
		commonDao.updateObject(wfProcessRecord);
	}

	
	public void insertProcessLog(String appId,String operateType,HttpServletRequest request,String remark,CustomerApplicationProcess process){
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		//通过申请表ID获取流程表
		NodeAudit nodeAudit = commonDao.findObjectById(NodeAudit.class, process.getNextNodeId());
		
		//添加流程记录表
		QzApplnProcessResult processResult = new QzApplnProcessResult();
		processResult.setApplicationId(appId);
		processResult.setNodeId(nodeAudit.getId());
		processResult.setNodeName(nodeAudit.getNodeName());
		processResult.setOperateType(operateType);
		processResult.setUserId(user.getId());
		processResult.setUserName(user.getDisplayName());
		processResult.setCreatedTime(new Date());
		processResult.setRemark(remark);
		commonDao.insertObject(processResult);
	}
	
}
