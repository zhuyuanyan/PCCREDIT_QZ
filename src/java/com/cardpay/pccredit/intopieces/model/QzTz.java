package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.web.form.BaseForm;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年5月7日 上午10:41:20 
 * 审议纪要
 */
@ModelParam(table = "qz_appln_ywsqb_htqdtz")
public class QzTz extends BusinessModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993484474057696853L;
	private String customerId;
	private Date slrq;
	private String jkrxm;
	private String pzje;
	private Date yyqdrq;
	private Date sjqdrq;
	private String zbkhjl;
	private String jbr;
	private String bz;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getJkrxm() {
		return jkrxm;
	}
	public void setJkrxm(String jkrxm) {
		this.jkrxm = jkrxm;
	}
	public String getPzje() {
		return pzje;
	}
	public void setPzje(String pzje) {
		this.pzje = pzje;
	}
	public String getZbkhjl() {
		return zbkhjl;
	}
	public void setZbkhjl(String zbkhjl) {
		this.zbkhjl = zbkhjl;
	}
	public String getJbr() {
		return jbr;
	}
	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public Date getSlrq() {
		return slrq;
	}
	public void setSlrq(Date slrq) {
		this.slrq = slrq;
	}
	public Date getYyqdrq() {
		return yyqdrq;
	}
	public void setYyqdrq(Date yyqdrq) {
		this.yyqdrq = yyqdrq;
	}
	public Date getSjqdrq() {
		return sjqdrq;
	}
	public void setSjqdrq(Date sjqdrq) {
		this.sjqdrq = sjqdrq;
	}
	
}
