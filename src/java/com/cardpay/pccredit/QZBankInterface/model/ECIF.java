package com.cardpay.pccredit.QZBankInterface.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author shaoming
 *
 */
@ModelParam(table = "qz_iesb_for_ecif",generator=IDType.assigned)
public class ECIF extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String globalType;
	private String globalId;
	private String globalDesc;
	private String certAreaCode;
	private String certOrg;
	private Date issDate;
	private Date effectDate;
	private Date expiryDate;
	private String clientName;
	private String clientNameType;
	private String clientType;
	private String clientStatus;
	private Date birthDate;
	private String sex;
	private String custManagerId;
	private String recordTellerNo;
	private Date registeredDate;
	private String clientBelongOrg;
	private String registeredTellerNo;
	private String registOrgNo;
	private Date orgRegisteredDate;
	private String countryCitizen;
	private String nationalityCode;
	private String regPermResidence;
	private String openAcctBranchId;
	private String openTellerNo;
	private Date openAcctDate;
	private String maritalStatus;
	private String educationLevel;
	private String city;
	private String areaCode;
	private String incidenceRelation;
	private String identityType;
	private String address;
	private String addressType;
	private String postalCode;
	private String contactModeType;
	private String contactMode;
	private String occupation;
	private String companyName;
	private String createBy;
	private String userId;

    //增加贷款所需客户资料字段

    //农户标志，是否农户,todo:界面需增加
    private String AGRI_FLAG;
    //最高学历，todo:界面需增加
    private String EDUCATION;
    //最高学位,todo:界面需增加
    private String DEGREE;
    //签约日期,todo:界面需增加
    private String SIGN_DATE;
    //持卡情况，todo:界面需增加
    private String HOLD_CARD_MSG;
    //是否持有外国护照，todo：界面需增加
    private String PASSPORT_FLAG;
    //信用等级 todo:默认，界面不用增加
    private String CREDIT_LEVEL;
    //信用到期日期 todo:默认置空
    private String EXPIRY_DATE;
    //是否关联客户 todo:页面需增加
    private String REL_CLIENT_FLAG;
    //与我行关系，为关联客户后需填 todo:页面需增加
    private String OWN_BRANCH_RELATION;
    //我行职务，为关联客户后需填 todo:页面需增加
    private String POST;
    //贷款卡标志
    private String LOAN_CARD_FLAG;
    //贷款卡卡号，todo:页面需增加
    private String LOAN_CARD_NO;

    public String getAGRI_FLAG() {
        return AGRI_FLAG;
    }

    public void setAGRI_FLAG(String AGRI_FLAG) {
        this.AGRI_FLAG = AGRI_FLAG;
    }

    public String getEDUCATION() {
        return EDUCATION;
    }

    public void setEDUCATION(String EDUCATION) {
        this.EDUCATION = EDUCATION;
    }

    public String getDEGREE() {
        return DEGREE;
    }

    public void setDEGREE(String DEGREE) {
        this.DEGREE = DEGREE;
    }

    public String getSIGN_DATE() {
        return SIGN_DATE;
    }

    public void setSIGN_DATE(String SIGN_DATE) {
        this.SIGN_DATE = SIGN_DATE;
    }

    public String getHOLD_CARD_MSG() {
        return HOLD_CARD_MSG;
    }

    public void setHOLD_CARD_MSG(String HOLD_CARD_MSG) {
        this.HOLD_CARD_MSG = HOLD_CARD_MSG;
    }

    public String getPASSPORT_FLAG() {
        return PASSPORT_FLAG;
    }

    public void setPASSPORT_FLAG(String PASSPORT_FLAG) {
        this.PASSPORT_FLAG = PASSPORT_FLAG;
    }

    public String getCREDIT_LEVEL() {
        return CREDIT_LEVEL;
    }

    public void setCREDIT_LEVEL(String CREDIT_LEVEL) {
        this.CREDIT_LEVEL = CREDIT_LEVEL;
    }

    public String getEXPIRY_DATE() {
        return EXPIRY_DATE;
    }

    public void setEXPIRY_DATE(String EXPIRY_DATE) {
        this.EXPIRY_DATE = EXPIRY_DATE;
    }

    public String getREL_CLIENT_FLAG() {
        return REL_CLIENT_FLAG;
    }

    public void setREL_CLIENT_FLAG(String REL_CLIENT_FLAG) {
        this.REL_CLIENT_FLAG = REL_CLIENT_FLAG;
    }

    public String getOWN_BRANCH_RELATION() {
        return OWN_BRANCH_RELATION;
    }

    public void setOWN_BRANCH_RELATION(String OWN_BRANCH_RELATION) {
        this.OWN_BRANCH_RELATION = OWN_BRANCH_RELATION;
    }

    public String getPOST() {
        return POST;
    }

    public void setPOST(String POST) {
        this.POST = POST;
    }

    public String getLOAN_CARD_FLAG() {
        return LOAN_CARD_FLAG;
    }

    public void setLOAN_CARD_FLAG(String LOAN_CARD_FLAG) {
        this.LOAN_CARD_FLAG = LOAN_CARD_FLAG;
    }

    public String getLOAN_CARD_NO() {
        return LOAN_CARD_NO;
    }

    public void setLOAN_CARD_NO(String LOAN_CARD_NO) {
        this.LOAN_CARD_NO = LOAN_CARD_NO;
    }

	//ecif返回的信息
	private String clientNo;//客户号
	
	public String getGlobalType() {
		return globalType;
	}
	public void setGlobalType(String globalType) {
		this.globalType = globalType;
	}
	public String getGlobalId() {
		return globalId;
	}
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
	public String getGlobalDesc() {
		return globalDesc;
	}
	public void setGlobalDesc(String globalDesc) {
		this.globalDesc = globalDesc;
	}
	public String getCertAreaCode() {
		return certAreaCode;
	}
	public void setCertAreaCode(String certAreaCode) {
		this.certAreaCode = certAreaCode;
	}
	public String getCertOrg() {
		return certOrg;
	}
	public void setCertOrg(String certOrg) {
		this.certOrg = certOrg;
	}
	public Date getIssDate() {
		return issDate;
	}
	public void setIssDate(Date issDate) {
		this.issDate = issDate;
	}
	public Date getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientNameType() {
		return clientNameType;
	}
	public void setClientNameType(String clientNameType) {
		this.clientNameType = clientNameType;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getClientStatus() {
		return clientStatus;
	}
	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCustManagerId() {
		return custManagerId;
	}
	public void setCustManagerId(String custManagerId) {
		this.custManagerId = custManagerId;
	}
	public String getRecordTellerNo() {
		return recordTellerNo;
	}
	public void setRecordTellerNo(String recordTellerNo) {
		this.recordTellerNo = recordTellerNo;
	}
	public Date getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
	public String getClientBelongOrg() {
		return clientBelongOrg;
	}
	public void setClientBelongOrg(String clientBelongOrg) {
		this.clientBelongOrg = clientBelongOrg;
	}
	public String getRegisteredTellerNo() {
		return registeredTellerNo;
	}
	public void setRegisteredTellerNo(String registeredTellerNo) {
		this.registeredTellerNo = registeredTellerNo;
	}
	public String getRegistOrgNo() {
		return registOrgNo;
	}
	public void setRegistOrgNo(String registOrgNo) {
		this.registOrgNo = registOrgNo;
	}
	public Date getOrgRegisteredDate() {
		return orgRegisteredDate;
	}
	public void setOrgRegisteredDate(Date orgRegisteredDate) {
		this.orgRegisteredDate = orgRegisteredDate;
	}
	public String getCountryCitizen() {
		return countryCitizen;
	}
	public void setCountryCitizen(String countryCitizen) {
		this.countryCitizen = countryCitizen;
	}
	public String getNationalityCode() {
		return nationalityCode;
	}
	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}
	public String getRegPermResidence() {
		return regPermResidence;
	}
	public void setRegPermResidence(String regPermResidence) {
		this.regPermResidence = regPermResidence;
	}
	public String getOpenAcctBranchId() {
		return openAcctBranchId;
	}
	public void setOpenAcctBranchId(String openAcctBranchId) {
		this.openAcctBranchId = openAcctBranchId;
	}
	public String getOpenTellerNo() {
		return openTellerNo;
	}
	public void setOpenTellerNo(String openTellerNo) {
		this.openTellerNo = openTellerNo;
	}
	public Date getOpenAcctDate() {
		return openAcctDate;
	}
	public void setOpenAcctDate(Date openAcctDate) {
		this.openAcctDate = openAcctDate;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getIncidenceRelation() {
		return incidenceRelation;
	}
	public void setIncidenceRelation(String incidenceRelation) {
		this.incidenceRelation = incidenceRelation;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getContactModeType() {
		return contactModeType;
	}
	public void setContactModeType(String contactModeType) {
		this.contactModeType = contactModeType;
	}
	public String getContactMode() {
		return contactMode;
	}
	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
