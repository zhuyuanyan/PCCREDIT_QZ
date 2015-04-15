package com.cardpay.pccredit.QZBankInterface.service;

/**
 * 信贷管理系统接口，经ESB转发
 * Created by johhny on 15/4/11.
 */
import java.util.Collection;
import java.util.Iterator;

import com.dc.eai.data.Array;
import com.dc.eai.data.CompositeData;
import com.dc.eai.data.Field;
import com.dc.eai.data.FieldAttr;
import com.dc.eai.data.FieldType;
import com.dcfs.esb.pack.standardxml.PackUtil;


public class IESBForCredit {

    /**
     * 组装CompositeData报文，传统微贷
     * @return
     */
    public static CompositeData createCommonCreditRequest() {
        CompositeData cd = new CompositeData();

        //SYS_HEAD
        CompositeData syaHead_struct = new CompositeData();
        //在syaHead_struct中加SERVICECODE
        Field serviceCodeField = new Field(new FieldAttr(FieldType.FIELD_STRING, 11));
        serviceCodeField.setValue("02002000005");//传统微贷服务服务码
        syaHead_struct.addField("SERVICE_CODE", serviceCodeField);

        //在syaHead_struct中加SERVICESCENE
        Field serviceSceneField = new Field(new FieldAttr(FieldType.FIELD_STRING, 2));
        serviceSceneField.setValue("01"); //个人微贷对应服务场景01
        syaHead_struct.addField("SERVICE_SCENE", serviceSceneField);
        cd.addStruct("SYS_HEAD", syaHead_struct);

        //BODY
        CompositeData body_struct = new CompositeData();

        //客户号
        Field CLIENT_NO=new Field(new FieldAttr(FieldType.FIELD_STRING, 20));
        CLIENT_NO.setValue("00001");//todo:传入客户号
        body_struct.addField("CLIENT_NO", CLIENT_NO);

        //币种
        Field CCY=new Field(new FieldAttr(FieldType.FIELD_STRING, 3));
        CCY.setValue("CNY");//人民币
        body_struct.addField("CCY", CCY);

        //授信额度
        Field CREDIT_LIMIT=new Field(new FieldAttr(FieldType.FIELD_DOUBLE,20,2));
        CREDIT_LIMIT.setValue("100000");//todo:授信额度
        body_struct.addField("CREDIT_LIMIT", CREDIT_LIMIT);

        //担保方式
        Field GUARANTEE_MODE=new Field(new FieldAttr(FieldType.FIELD_STRING,10));
        GUARANTEE_MODE.setValue("B");//抵押 todo:置后担保，需确认
        body_struct.addField("GUARANTEE_MODE", GUARANTEE_MODE);

        //期限类型
        Field TERM_TYPE=new Field(new FieldAttr(FieldType.FIELD_STRING, 3));
        TERM_TYPE.setValue("002");//todo:传入期限类型，年，月，日
        body_struct.addField("TERM_TYPE", TERM_TYPE);

        //期限
        Field TERM=new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
        TERM.setValue("2");//todo:传入期限
        body_struct.addField("TERM", TERM);

        //贷款起始日期
        Field START_DATE=new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        START_DATE.setValue("20140404");//todo:传入起始日期 YYYYMMdd
        body_struct.addField("START_DATE", START_DATE);

        //贷款结束日期
        Field END_DATE=new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        END_DATE.setValue("20160404");//todo:传入结束日期
        body_struct.addField("END_DATE", END_DATE);

        /*
        客户信息数组 todo:需要在前端补录应在前端补录
         */
        Array CLIENT_ARRAY = new Array(); //CLIENT_NO数组
        CompositeData CusArrayStruct = new CompositeData();

        //客户号
        Field A_CLIENT_NO = new Field(new FieldAttr(FieldType.FIELD_STRING, 20));
        A_CLIENT_NO.setValue("00001");//todo:传入客户号
        CusArrayStruct.addField("A_CLIENT_NO", A_CLIENT_NO);

        //客户名称
        Field CLIENT_NAME = new Field(new FieldAttr(FieldType.FIELD_STRING, 150));
        CLIENT_NAME.setValue("张三");//todo:传入客户名称
        CusArrayStruct.addField("CLIENT_NAME", CLIENT_NAME);

        //性别
        Field SEX = new Field(new FieldAttr(FieldType.FIELD_STRING, 2));
        SEX.setValue("01");//todo:传入性别，男：01 ；女：02； 未知：03
        CusArrayStruct.addField("SEX", SEX);

        //客户类型
        Field CLIENT_TYPE = new Field(new FieldAttr(FieldType.FIELD_STRING, 6));
        CLIENT_TYPE.setValue("Z");//个人客户 todo:文档中多个个人客户码，测试需明确
        CusArrayStruct.addField("CLIENT_TYPE", CLIENT_TYPE);

        //证件类型
        Field GLOBAL_TYPE = new Field(new FieldAttr(FieldType.FIELD_STRING, 5));
        GLOBAL_TYPE.setValue("0");//身份证
        CusArrayStruct.addField("GLOBAL_TYPE", GLOBAL_TYPE);

        //证件号码
        Field GLOBAL_ID = new Field(new FieldAttr(FieldType.FIELD_STRING, 30));
        GLOBAL_ID.setValue("320482198601271133");//todo:传入证件号码
        CusArrayStruct.addField("GLOBAL_ID", GLOBAL_ID);

        //长期证件标志 todo:数据字典缺失
        Field LONG_GLOBAL_TYPE = new Field(new FieldAttr(FieldType.FIELD_STRING, 3));
        LONG_GLOBAL_TYPE.setValue("1");//todo:传入标识
        CusArrayStruct.addField("LONG_GLOBAL_TYPE", LONG_GLOBAL_TYPE);

        //签发日期
        Field ISS_DATE = new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        ISS_DATE.setValue("");//todo:传入数值，无则置空
        CusArrayStruct.addField("ISS_DATE", ISS_DATE);

        //证件有效日期
        Field GLOBAL_EFF_DATE = new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        GLOBAL_EFF_DATE.setValue("20200404");//todo:传入数值，无则置空
        CusArrayStruct.addField("GLOBAL_EFF_DATE", GLOBAL_EFF_DATE);

        //农户标识 todo:数据字典缺失
        Field AGRI_FLAG = new Field(new FieldAttr(FieldType.FIELD_STRING, 3));
        AGRI_FLAG.setValue("1");//todo:传入数值，无则置空
        CusArrayStruct.addField("AGRI_FLAG", AGRI_FLAG);

        //国家代码
        Field COUNTRY_CODE = new Field(new FieldAttr(FieldType.FIELD_STRING, 3));
        COUNTRY_CODE.setValue("CHN");//中国
        CusArrayStruct.addField("COUNTRY_CODE", COUNTRY_CODE);

        //民族代码
        Field NATIONALITY_CODE = new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
        NATIONALITY_CODE.setValue("01");//todo:传入民族，例如 “汉”
        CusArrayStruct.addField("NATIONALITY_CODE", NATIONALITY_CODE);

        //户籍所在地
        Field REG_PERM_RESIDENCE = new Field(new FieldAttr(FieldType.FIELD_STRING, 60));
        REG_PERM_RESIDENCE.setValue("110228");//todo:传入户籍所在地
        CusArrayStruct.addField("REG_PERM_RESIDENCE", REG_PERM_RESIDENCE);

        //地址
        Field ADDRESS = new Field(new FieldAttr(FieldType.FIELD_STRING, 300));
        ADDRESS.setValue("地址");//todo:传入地址
        CusArrayStruct.addField("ADDRESS", ADDRESS);

        //出生日期
        Field BIRTH_DATE = new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
        BIRTH_DATE.setValue("19880404");//todo:传入出生日期，格式YYYYMMdd
        CusArrayStruct.addField("BIRTH_DATE", BIRTH_DATE);

        //最高学历 todo:字典项缺失
        Field EDUCATION = new Field(new FieldAttr(FieldType.FIELD_STRING, 1));
        EDUCATION.setValue("20");//todo:传入最高学历
        CusArrayStruct.addField("EDUCATION", EDUCATION);

        //最高学位 todo:字典项缺失
        Field DEGREE = new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
        DEGREE.setValue("3");//todo:传入最高学位
        CusArrayStruct.addField("DEGREE", DEGREE);

        //签约日期
        Field SIGN_DATE = new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        SIGN_DATE.setValue("20140404");//todo:传入签约日期，格式YYYYMMdd
        CusArrayStruct.addField("SIGN_DATE", SIGN_DATE);

        //持卡情况 todo:数据字典项缺失
        Field HOLD_CARD_MSG = new Field(new FieldAttr(FieldType.FIELD_STRING, 3));
        HOLD_CARD_MSG.setValue("1");//todo:传入持卡情况
        CusArrayStruct.addField("HOLD_CARD_MSG", HOLD_CARD_MSG);

        //是否拥有外国护照或居住权 todo:数据字典项缺失
        Field PASSPORT_FLAG = new Field(new FieldAttr(FieldType.FIELD_STRING, 3));
        PASSPORT_FLAG.setValue("1");//todo:传入数值
        CusArrayStruct.addField("PASSPORT_FLAG", PASSPORT_FLAG);

        //信用等级 todo:数据字典项缺失
        Field CREDIT_LEVEL = new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
        CREDIT_LEVEL.setValue("00");//todo:传入信用等级
        CusArrayStruct.addField("CREDIT_LEVEL", CREDIT_LEVEL);

        //信用到期日期
        Field EXPIRY_DATE = new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        EXPIRY_DATE.setValue("20200404");//todo:传入到期日期
        CusArrayStruct.addField("EXPIRY_DATE", EXPIRY_DATE);

        //是否关联客户 todo:数据字典项缺失
        Field REL_CLIENT_FLAG = new Field(new FieldAttr(FieldType.FIELD_STRING, 3));
        REL_CLIENT_FLAG.setValue("1");//todo:传入是否关联客户
        CusArrayStruct.addField("REL_CLIENT_FLAG", REL_CLIENT_FLAG);

        //与我行关系 todo:数据字典项缺失
        Field OWN_BRANCH_RELATION = new Field(new FieldAttr(FieldType.FIELD_STRING, 30));
        OWN_BRANCH_RELATION.setValue("B1");//todo:传入我行关系
        CusArrayStruct.addField("OWN_BRANCH_RELATION", OWN_BRANCH_RELATION);

        //我行职务
        Field POST = new Field(new FieldAttr(FieldType.FIELD_STRING, 30));
        POST.setValue("7");//todo:传入我行职务情况
        CusArrayStruct.addField("POST", POST);

        //贷款卡标识 todo:数据字典项缺失
        Field LOAN_CARD_FLAG = new Field(new FieldAttr(FieldType.FIELD_STRING, 3));
        LOAN_CARD_FLAG.setValue("1");//todo:传入贷款卡标识
        CusArrayStruct.addField("LOAN_CARD_FLAG", LOAN_CARD_FLAG);

        //贷款卡号
        Field LOAN_CARD_NO = new Field(new FieldAttr(FieldType.FIELD_STRING, 30));
        LOAN_CARD_NO.setValue("111111");//todo:传入贷款卡号
        CusArrayStruct.addField("LOAN_CARD_NO", LOAN_CARD_NO);

        //手机号码
        Field MOBILE = new Field(new FieldAttr(FieldType.FIELD_STRING, 30));
        MOBILE.setValue("13513513513");//todo:传入手机号码
        CusArrayStruct.addField("MOBILE", MOBILE);


        //上级机构 todo:数据字典项缺失
        Field HIGHER_ORG_NO = new Field(new FieldAttr(FieldType.FIELD_STRING, 32));
        HIGHER_ORG_NO.setValue("01");//todo:传入上级机构
        CusArrayStruct.addField("HIGHER_ORG_NO", HIGHER_ORG_NO);

        //客户经理
        Field ACCT_EXEC = new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
        ACCT_EXEC.setValue("001");//todo:传入客户经理柜员号
        CusArrayStruct.addField("ACCT_EXEC", ACCT_EXEC);

        //开户日期
        Field OPEN_ACCT_DATE = new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        OPEN_ACCT_DATE.setValue("20150414");//todo:传入开户日期，YYYYMMdd
        CusArrayStruct.addField("OPEN_ACCT_DATE", OPEN_ACCT_DATE);

        //信息加入数组
        CLIENT_ARRAY.addStruct(CusArrayStruct);
        body_struct.addArray("CLIENT_ARRAY",CLIENT_ARRAY);

        cd.addStruct("BODY",body_struct);
        return cd;
    }

    /**
     * 解析CompositeData报文
     * @param cd
     */
    public static void parseCreditResponse(CompositeData cd){
        //todo:需要返回格式
    }
}
