package com.cardpay.pccredit.QZBankInterface.service;

/**
 * 核心系统账户查询接口，经ESB转发
 * Created by johhny on 15/4/13.
 */
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.eai.data.Array;
import com.dc.eai.data.CompositeData;
import com.dc.eai.data.Field;
import com.dc.eai.data.FieldAttr;
import com.dc.eai.data.FieldType;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
@Service
public class IESBForCore {
    /**
     * 组装CompositeData报文
     * @return
     */
    public static CompositeData createCoreRequest() {
        CompositeData cd = new CompositeData();

        //SYS_HEAD
        CompositeData syaHead_struct = new CompositeData();
        //在syaHead_struct中加SERVICECODE
        Field serviceCodeField = new Field(new FieldAttr(FieldType.FIELD_STRING, 11));
        serviceCodeField.setValue("11003000007");//核心账户查询
        syaHead_struct.addField("SERVICE_CODE", serviceCodeField);

        //在syaHead_struct中加SERVICESCENE
        Field serviceSceneField = new Field(new FieldAttr(FieldType.FIELD_STRING, 2));
        serviceSceneField.setValue("17"); //账户查询对应场景
        syaHead_struct.addField("SERVICE_SCENE", serviceSceneField);
        cd.addStruct("SYS_HEAD", syaHead_struct);

        //BODY
        CompositeData body_struct = new CompositeData();

        //帐号
        Field ACCT_NO=new Field(new FieldAttr(FieldType.FIELD_STRING, 50));
        ACCT_NO.setValue("");//todo:传入账号
        body_struct.addField("ACCT_NO", ACCT_NO);

        cd.addStruct("BODY",body_struct);
        return cd;
    }

    /**
     * 解析CompositeData报文
     * @param cd
     */
    public static void parseCoreResponse(CompositeData cd) {

//        CompositeData body = cd.getStruct("BODY");
//
//        String ACCT_NO = body.getField("ACCT_NO").getValue();//账号
//        String ACCT_NAME = body.getField("ACCT_NAME").getValue();//户名
//        String CCY = body.getField("CCY").getValue();//币种
//        String ACCT_TYPE = body.getField("ACCT_TYPE").getValue();//账户类型
//        String OPEN_ACCT_BRANCH_ID = body.getField("OPEN_ACCT_BRANCH_ID").getValue();//开户机构
//        String OPEN_ACCT_BRANCH_NAME = body.getField("OPEN_ACCT_BRANCH_NAME").getValue();//开户行名
//        String ORG_NO = body.getField("ORG_NO").getValue();//机构码
//        String C_INTERBANK_ID = body.getField("C_INTERBANK_ID").getValue();//全国联行号
//        String OPEN_ACCT_DATE = body.getField("OPEN_ACCT_DATE").getValue();//开户日期
//        String GL_CODE = body.getField("GL_CODE").getValue();//科目号
//        String BALANCE = body.getField("BALANCE").getValue();//余额

    }
}
