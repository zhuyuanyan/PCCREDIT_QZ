package com.cardpay.pccredit.QZBankInterface.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

import java.util.Date;

/**
 * Created by johhny on 15/4/22.
 */
public class Credit {
    /**
     * @author johnny
     */
    @ModelParam(table = "qz_credit", generator = IDType.assigned)
    public class Credit extends BusinessModel {
        private static final long serialVersionUID = 1L;
        private String ID;
        //客户号
        private String CLIENT_NO;

        //币种
        private String CCY;

        //授信额度
        private Double CREDIT_LIMIT;

        //担保方式
        private String GUARANTEE_MODE;

        //期限类型
        private String TERM_TYPE;

        //期限
        private String TERM;

        //贷款起始日期
        private String START_DATE;

        //贷款结束日期
        private String END_DATE;

        private Date CreatedTime;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public Date getCreatedTime() {
            return CreatedTime;
        }

        public void setCreatedTime(Date createdTime) {
            CreatedTime = createdTime;
        }

        public String getCLIENT_NO() {
            return CLIENT_NO;
        }

        public void setCLIENT_NO(String CLIENT_NO) {
            this.CLIENT_NO = CLIENT_NO;
        }

        public String getCCY() {
            return CCY;
        }

        public void setCCY(String CCY) {
            this.CCY = CCY;
        }

        public Double getCREDIT_LIMIT() {
            return CREDIT_LIMIT;
        }

        public void setCREDIT_LIMIT(Double CREDIT_LIMIT) {
            this.CREDIT_LIMIT = CREDIT_LIMIT;
        }

        public String getGUARANTEE_MODE() {
            return GUARANTEE_MODE;
        }

        public void setGUARANTEE_MODE(String GUARANTEE_MODE) {
            this.GUARANTEE_MODE = GUARANTEE_MODE;
        }

        public String getTERM_TYPE() {
            return TERM_TYPE;
        }

        public void setTERM_TYPE(String TERM_TYPE) {
            this.TERM_TYPE = TERM_TYPE;
        }

        public String getTERM() {
            return TERM;
        }

        public void setTERM(String TERM) {
            this.TERM = TERM;
        }

        public String getSTART_DATE() {
            return START_DATE;
        }

        public void setSTART_DATE(String START_DATE) {
            this.START_DATE = START_DATE;
        }

        public String getEND_DATE() {
            return END_DATE;
        }

        public void setEND_DATE(String END_DATE) {
            this.END_DATE = END_DATE;
        }
    }
}
