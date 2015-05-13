package com.cardpay.pccredit.QZBankInterface.service;

/**
 * ECIF接口，经ESB转发
 * Created by johhny on 15/4/11.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.eai.data.Array;
import com.dc.eai.data.CompositeData;
import com.dc.eai.data.Field;
import com.dc.eai.data.FieldAttr;
import com.dc.eai.data.FieldType;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
@Service
public class IESBForECIFQuery {
	
	@Autowired
	CommonDao commonDao;
    /**
     * 组装CompositeData报文
     * 客户信息查询(11003000001)	个人客户信息查询(02)
     * @return
     */
    public static CompositeData createEcifRequest() {
    	CompositeData cd = new CompositeData();

        //SYS_HEAD
        CompositeData syaHead_struct = new CompositeData();
        //在syaHead_struct中加SERVICECODE
        Field serviceCodeField = new Field(new FieldAttr(FieldType.FIELD_STRING, 11));
        serviceCodeField.setValue("11003000001");//核心账户查询
        syaHead_struct.addField("SERVICE_CODE", serviceCodeField);

        //在syaHead_struct中加SERVICESCENE
        Field serviceSceneField = new Field(new FieldAttr(FieldType.FIELD_STRING, 2));
        serviceSceneField.setValue("02"); //账户查询对应场景
        syaHead_struct.addField("SERVICE_SCENE", serviceSceneField);
        
        //在syaHead_struct中加TRAN_DATE
        Field tran_datefield = new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        tran_datefield.setValue("20150416"); //交易日期
        syaHead_struct.addField("TRAN_DATE", tran_datefield);
        
        //在syaHead_struct中加CONSUMER_ID
        Field consumer_idField = new Field(new FieldAttr(FieldType.FIELD_STRING, 6));
        consumer_idField.setValue("300025"); //消费系统编号
        syaHead_struct.addField("CONSUMER_ID", consumer_idField);
        
        cd.addStruct("SYS_HEAD", syaHead_struct);

        //BODY
        CompositeData body_struct = new CompositeData();

        //客户号
        Field CLIENT_NO=new Field(new FieldAttr(FieldType.FIELD_STRING, 20));
        CLIENT_NO.setValue("0010000151130009");//todo:客户号
        body_struct.addField("CLIENT_NO", CLIENT_NO);
        
        //客户类型
        Field CLIENT_TYPE=new Field(new FieldAttr(FieldType.FIELD_STRING, 6));
        CLIENT_TYPE.setValue("Z");//todo:客户类型
        body_struct.addField("CLIENT_TYPE", CLIENT_TYPE);
        
        //证件号码
        Field GLOBAL_ID=new Field(new FieldAttr(FieldType.FIELD_STRING, 30));
        GLOBAL_ID.setValue("320482198601271133");//todo:证件号码
        body_struct.addField("GLOBAL_ID", GLOBAL_ID);
        
        //证件类型
        Field GLOBAL_TYPE=new Field(new FieldAttr(FieldType.FIELD_STRING, 5));
        GLOBAL_TYPE.setValue("0");//todo:证件类型
        body_struct.addField("GLOBAL_TYPE", GLOBAL_TYPE);
        
        //账号
        Field ACCT_NO=new Field(new FieldAttr(FieldType.FIELD_STRING, 50));
        ACCT_NO.setValue("");//todo:传入账号 置空 暂时没用
        body_struct.addField("ACCT_NO", ACCT_NO);
        
        cd.addStruct("BODY",body_struct);
        return cd;
    }

    /**
     * 组装CompositeData报文
     * 客户信息查询(11003000001)	客户存款账号信息查询(05)
     * @return
     */
    public static CompositeData createEcifRequest2() {
    	CompositeData cd = new CompositeData();

        //SYS_HEAD
        CompositeData syaHead_struct = new CompositeData();
        //在syaHead_struct中加SERVICECODE
        Field serviceCodeField = new Field(new FieldAttr(FieldType.FIELD_STRING, 11));
        serviceCodeField.setValue("11003000001");//核心账户查询
        syaHead_struct.addField("SERVICE_CODE", serviceCodeField);

        //在syaHead_struct中加SERVICESCENE
        Field serviceSceneField = new Field(new FieldAttr(FieldType.FIELD_STRING, 2));
        serviceSceneField.setValue("05"); //账户查询对应场景
        syaHead_struct.addField("SERVICE_SCENE", serviceSceneField);
        
        //在syaHead_struct中加TRAN_DATE
        Field tran_datefield = new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        tran_datefield.setValue("20150416"); //交易日期
        syaHead_struct.addField("TRAN_DATE", tran_datefield);
        
        //在syaHead_struct中加CONSUMER_ID
        Field consumer_idField = new Field(new FieldAttr(FieldType.FIELD_STRING, 6));
        consumer_idField.setValue("300025"); //消费系统编号
        syaHead_struct.addField("CONSUMER_ID", consumer_idField);
        
        cd.addStruct("SYS_HEAD", syaHead_struct);

        //BODY
        CompositeData body_struct = new CompositeData();

        //客户号
        Field CLIENT_NO=new Field(new FieldAttr(FieldType.FIELD_STRING, 20));
        CLIENT_NO.setValue("0010000151130009");//todo:客户号
        body_struct.addField("CLIENT_NO", CLIENT_NO);
        
        //账号
        Field ACCT_NO=new Field(new FieldAttr(FieldType.FIELD_STRING, 50));
        ACCT_NO.setValue("");//todo:传入账号 为空  暂时没用
        body_struct.addField("ACCT_NO", ACCT_NO);
        
        cd.addStruct("BODY",body_struct);
        
        //APP_HEAD
        CompositeData APP_HEAD = new CompositeData();

        //本页记录总数
        Field TOTAL_NUM=new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
        TOTAL_NUM.setValue("10");//todo:本页记录总数
        APP_HEAD.addField("TOTAL_NUM", TOTAL_NUM);
        
        //当前记录号
        Field CURRENT_NUM=new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
        CURRENT_NUM.setValue("1");//todo:传入当前记录号
        APP_HEAD.addField("CURRENT_NUM", CURRENT_NUM);
        
        cd.addStruct("APP_HEAD",APP_HEAD);
        
        return cd;
    }
    
    /**
     * 解析CompositeData报文
     * @param cd
     */
    public static void parseEcifResponse(CompositeData cd){

    }
    
}
