package com.cardpay.pccredit.QZBankInterface.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author shaoming
 *
 */
@ModelParam(table = "qz_iesb_for_circle",generator=IDType.assigned)
public class Circle extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String contractNo;
	private String cardNo;
	private String clientNo;
	private String guaranteeMode;
	private String guaranteeModeDetail;
	private String ccy1;
	private String contractAmt;
	private String startDate;
	private String endDate;
	private String exchangeRate;
	private String manaOrg;
	private String registeredId;
	private String registOrgNo;
	private String incomeOrgNo;
	private String registeredDate;
	private String term;
	private String termType;
	private String deferFlag;
	private String actIntRate;
	private String overdueIntRate;
	private String penaltyIntRate;
	private String repayType;
	private String repayDate;
	private String fiveLevelType;
	private String speLoanType;
	private String limitUseedType;
	private String drUsage;
	private String flag;
	private String loanKind;
	private String agriLoanKind;
	private String personLoanKind;
	private String adjustType;
	private String newPrdType;
	private String newPrdLoan;
	private String loanDirection;
	private String loanBelong1;
	private String loanBelong2;
	private String loanBelong3;
	private String loanBelong4;
	private String bussType;
	private String chiCustManager;
	private String aClientNo;
	private String clientName;
	private String sex;
	private String clientType;
	private String globalType;
	private String globalId;
	private String longGlobalType;
	private String issDate;
	private String globalEffDate;
	private String agriFlag;
	private String countryCode;
	private String nationalityCode;
	private String regPermResidence;
	private String address;
	private String birthDate;
	private String education;
	private String degree;
	private String signDate;
	private String holdCardMsg;
	private String passportFlag;
	private String creditLevel;
	private String expiryDate;
	private String relClientFlag;
	private String ownBranchRelation;
	private String post;
	private String loanCardFlag;
	private String loanCardNo;
	private String mobile;
	private String higherOrgNo;
	private String acctExec;
	private String openAcctDate;
	private String ccy2;
	private String feeAmount;
	private String acctNo;
	private String acctChrt;
	private String ownBranchFlag;
	private String acctNo1;
	private String acctName;
	private String openAcctBranchId;
	private String openAcctBranchName;
	private String payAmt;
	private String glCode;
	private String ccy3;
	private String cInterbankId;
	private String createBy;
	private String userId;
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getGuaranteeMode() {
		return guaranteeMode;
	}
	public void setGuaranteeMode(String guaranteeMode) {
		this.guaranteeMode = guaranteeMode;
	}
	public String getGuaranteeModeDetail() {
		return guaranteeModeDetail;
	}
	public void setGuaranteeModeDetail(String guaranteeModeDetail) {
		this.guaranteeModeDetail = guaranteeModeDetail;
	}
	public String getCcy1() {
		return ccy1;
	}
	public void setCcy1(String ccy1) {
		this.ccy1 = ccy1;
	}
	public String getContractAmt() {
		return contractAmt;
	}
	public void setContractAmt(String contractAmt) {
		this.contractAmt = contractAmt;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getManaOrg() {
		return manaOrg;
	}
	public void setManaOrg(String manaOrg) {
		this.manaOrg = manaOrg;
	}
	public String getRegisteredId() {
		return registeredId;
	}
	public void setRegisteredId(String registeredId) {
		this.registeredId = registeredId;
	}
	public String getRegistOrgNo() {
		return registOrgNo;
	}
	public void setRegistOrgNo(String registOrgNo) {
		this.registOrgNo = registOrgNo;
	}
	public String getIncomeOrgNo() {
		return incomeOrgNo;
	}
	public void setIncomeOrgNo(String incomeOrgNo) {
		this.incomeOrgNo = incomeOrgNo;
	}
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public String getDeferFlag() {
		return deferFlag;
	}
	public void setDeferFlag(String deferFlag) {
		this.deferFlag = deferFlag;
	}
	public String getActIntRate() {
		return actIntRate;
	}
	public void setActIntRate(String actIntRate) {
		this.actIntRate = actIntRate;
	}
	public String getOverdueIntRate() {
		return overdueIntRate;
	}
	public void setOverdueIntRate(String overdueIntRate) {
		this.overdueIntRate = overdueIntRate;
	}
	public String getPenaltyIntRate() {
		return penaltyIntRate;
	}
	public void setPenaltyIntRate(String penaltyIntRate) {
		this.penaltyIntRate = penaltyIntRate;
	}
	public String getRepayType() {
		return repayType;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public String getFiveLevelType() {
		return fiveLevelType;
	}
	public void setFiveLevelType(String fiveLevelType) {
		this.fiveLevelType = fiveLevelType;
	}
	public String getSpeLoanType() {
		return speLoanType;
	}
	public void setSpeLoanType(String speLoanType) {
		this.speLoanType = speLoanType;
	}
	public String getLimitUseedType() {
		return limitUseedType;
	}
	public void setLimitUseedType(String limitUseedType) {
		this.limitUseedType = limitUseedType;
	}
	public String getDrUsage() {
		return drUsage;
	}
	public void setDrUsage(String drUsage) {
		this.drUsage = drUsage;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLoanKind() {
		return loanKind;
	}
	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}
	public String getAgriLoanKind() {
		return agriLoanKind;
	}
	public void setAgriLoanKind(String agriLoanKind) {
		this.agriLoanKind = agriLoanKind;
	}
	public String getPersonLoanKind() {
		return personLoanKind;
	}
	public void setPersonLoanKind(String personLoanKind) {
		this.personLoanKind = personLoanKind;
	}
	public String getAdjustType() {
		return adjustType;
	}
	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}
	public String getNewPrdType() {
		return newPrdType;
	}
	public void setNewPrdType(String newPrdType) {
		this.newPrdType = newPrdType;
	}
	public String getNewPrdLoan() {
		return newPrdLoan;
	}
	public void setNewPrdLoan(String newPrdLoan) {
		this.newPrdLoan = newPrdLoan;
	}
	public String getLoanDirection() {
		return loanDirection;
	}
	public void setLoanDirection(String loanDirection) {
		this.loanDirection = loanDirection;
	}
	public String getLoanBelong1() {
		return loanBelong1;
	}
	public void setLoanBelong1(String loanBelong1) {
		this.loanBelong1 = loanBelong1;
	}
	public String getLoanBelong2() {
		return loanBelong2;
	}
	public void setLoanBelong2(String loanBelong2) {
		this.loanBelong2 = loanBelong2;
	}
	public String getLoanBelong3() {
		return loanBelong3;
	}
	public void setLoanBelong3(String loanBelong3) {
		this.loanBelong3 = loanBelong3;
	}
	public String getLoanBelong4() {
		return loanBelong4;
	}
	public void setLoanBelong4(String loanBelong4) {
		this.loanBelong4 = loanBelong4;
	}
	public String getBussType() {
		return bussType;
	}
	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
	public String getChiCustManager() {
		return chiCustManager;
	}
	public void setChiCustManager(String chiCustManager) {
		this.chiCustManager = chiCustManager;
	}
	public String getaClientNo() {
		return aClientNo;
	}
	public void setaClientNo(String aClientNo) {
		this.aClientNo = aClientNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
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
	public String getLongGlobalType() {
		return longGlobalType;
	}
	public void setLongGlobalType(String longGlobalType) {
		this.longGlobalType = longGlobalType;
	}
	public String getIssDate() {
		return issDate;
	}
	public void setIssDate(String issDate) {
		this.issDate = issDate;
	}
	public String getGlobalEffDate() {
		return globalEffDate;
	}
	public void setGlobalEffDate(String globalEffDate) {
		this.globalEffDate = globalEffDate;
	}
	public String getAgriFlag() {
		return agriFlag;
	}
	public void setAgriFlag(String agriFlag) {
		this.agriFlag = agriFlag;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getHoldCardMsg() {
		return holdCardMsg;
	}
	public void setHoldCardMsg(String holdCardMsg) {
		this.holdCardMsg = holdCardMsg;
	}
	public String getPassportFlag() {
		return passportFlag;
	}
	public void setPassportFlag(String passportFlag) {
		this.passportFlag = passportFlag;
	}
	public String getCreditLevel() {
		return creditLevel;
	}
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getRelClientFlag() {
		return relClientFlag;
	}
	public void setRelClientFlag(String relClientFlag) {
		this.relClientFlag = relClientFlag;
	}
	public String getOwnBranchRelation() {
		return ownBranchRelation;
	}
	public void setOwnBranchRelation(String ownBranchRelation) {
		this.ownBranchRelation = ownBranchRelation;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getLoanCardFlag() {
		return loanCardFlag;
	}
	public void setLoanCardFlag(String loanCardFlag) {
		this.loanCardFlag = loanCardFlag;
	}
	public String getLoanCardNo() {
		return loanCardNo;
	}
	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHigherOrgNo() {
		return higherOrgNo;
	}
	public void setHigherOrgNo(String higherOrgNo) {
		this.higherOrgNo = higherOrgNo;
	}
	public String getAcctExec() {
		return acctExec;
	}
	public void setAcctExec(String acctExec) {
		this.acctExec = acctExec;
	}
	public String getOpenAcctDate() {
		return openAcctDate;
	}
	public void setOpenAcctDate(String openAcctDate) {
		this.openAcctDate = openAcctDate;
	}
	public String getCcy2() {
		return ccy2;
	}
	public void setCcy2(String ccy2) {
		this.ccy2 = ccy2;
	}
	public String getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAcctChrt() {
		return acctChrt;
	}
	public void setAcctChrt(String acctChrt) {
		this.acctChrt = acctChrt;
	}
	public String getOwnBranchFlag() {
		return ownBranchFlag;
	}
	public void setOwnBranchFlag(String ownBranchFlag) {
		this.ownBranchFlag = ownBranchFlag;
	}
	public String getAcctNo1() {
		return acctNo1;
	}
	public void setAcctNo1(String acctNo1) {
		this.acctNo1 = acctNo1;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getOpenAcctBranchId() {
		return openAcctBranchId;
	}
	public void setOpenAcctBranchId(String openAcctBranchId) {
		this.openAcctBranchId = openAcctBranchId;
	}
	public String getOpenAcctBranchName() {
		return openAcctBranchName;
	}
	public void setOpenAcctBranchName(String openAcctBranchName) {
		this.openAcctBranchName = openAcctBranchName;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public String getCcy3() {
		return ccy3;
	}
	public void setCcy3(String ccy3) {
		this.ccy3 = ccy3;
	}
	public String getcInterbankId() {
		return cInterbankId;
	}
	public void setcInterbankId(String cInterbankId) {
		this.cInterbankId = cInterbankId;
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
