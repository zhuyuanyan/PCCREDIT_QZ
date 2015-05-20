package com.cardpay.pccredit.QZBankInterface.constant;

public class Constant {
	/* 新开户 未填写贷款信息 */
	public static String STATUS_NONE = "0";
	/* 已填写信息，未申请 */
	public static String STATUS_NOAPPLY = "1";
	/* 已填写信息，申请 */
	public static String STATUS_APPLY = "2";
	/* 申请成功 */
	public static String STATUS_APPLY_SUCCESS = "3";
	/* 申请失败 */
	public static String STATUS_APPLY_FAILURE = "4";
	
	//某笔贷款的状态值
	/* 结清*/
	public static String STATUS_END = "99";
	
	public static String RET_CODE_CIRCLE = "000000";//circle 成功时的返回值
}
