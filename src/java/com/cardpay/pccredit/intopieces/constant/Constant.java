package com.cardpay.pccredit.intopieces.constant;

public class Constant {
	/* 操作失败 */
	public static String FAIL_MESSAGE = "操作失败";
	
	/* 操作成功 */
	public static String SUCCESS_MESSAGE = "操作成功";
	
	/* 操作成功 */
	public static String UPLOAD_SUCCESS_MESSAGE = "导出并上传成功";
	
	/* 文件为空 */
	public static String FILE_EMPTY = "文件不可为空";
	
	/* 结果集 */
	public static String RESULT_LIST1 = "resultList1";
	
	/* 结果集 */
	public static String RESULT_LIST2 = "resultList2";
	
	/* 影像资料上传路径 */
	public static String FILE_PATH = "/home/pccredit/pccredit_upload/";
	
	/* 保存进件*/
	public static String SAVE_INTOPICES = "save";
	
	/* 申请进件*/
	public static String APPROVE_INTOPICES = "audit";  
	
	/* 拒绝进件*/
	public static String REFUSE_INTOPICES = "refuse";
	
	/* 成功进件*/
	public static String SUCCESS_INTOPICES = "success";
	
	/* 申请未通过*/
	public static String NOPASS_INTOPICES = "nopass";
	
	
	/*以下是上传状态*/
	public static String INITIAL_INTOPICES="initial";
	
	public static String  EXPORT_INTOPICES="export";
	
	public static String  UPLOAD_INTOPICES="upload";
	
	
	
	/* 申请已通过*/
	public static String APPROVED_INTOPICES = "approved";
	
	/*联系人*/
	public static String CONTACTID = "contactId";
	
	/*担保人*/
	public static String GUARANTORID = "guarantorId";
	
    /*推荐人*/
	public static String RECOMMENDID = "recommendId";
	
	/**
	 * 定时生成 默认用户
	 */
	public static String SCHEDULES_SYSTEM_USER = "system";
	
	
	/*FTP链接配置*/
	public static String FTPIP = "11.23.11.43";
	
	public static String FTPPORT = "21";
	
	public static String FTPUSERNAME = "root";
	
	public static String FTPPASSWORD = "abc,123";
	
	public static String FTPPATH = "qiankang";
	/*进件信息*/
	public static String APP_STATE_1="已申请";
	public static String APP_STATE_2="未申请";
	public static String APP_STATE_3="退回";
	
	//泉州银行根机构id
	public static String QZ_ORG_ROOT_ID = "9350000000";
	
	/*默认产品*/
	public static String DEFAULT_TYPE="1";

	/*进件状态*/
	public static String status_shenqing="申请";
	public static String status_chushen="初审";
	public static String status_xingzheng1="行政岗-初";
	public static String status_shouxin="授信审批岗";
	public static String status_zhongxin="团队管理岗复核";
	public static String status_xinshen="行政岗-终";
	public static String status_xingzheng2="团队管理岗最终复核";
	public static String jyd_id="00001";//决议单默认ID
	public static String htd_id="00002";//合同单默认ID
	
	/*审批结果*/
	public static String APPLN_TYPE_1="审批通过";
	public static String APPLN_TYPE_2="退件";
	public static String APPLN_TYPE_3="拒件";
	
	/*审批特殊标志*/
	public static String APPROVE_EDIT_1="1";//初审退回--直接删除申请件
	public static String APPROVE_EDIT_2="3";//初审拒件--授信岗审批
	public static String APPROVE_EDIT_3="4";//授信岗补充上会--退回客户经理
}
