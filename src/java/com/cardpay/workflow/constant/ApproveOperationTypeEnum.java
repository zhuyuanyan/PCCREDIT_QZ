package com.cardpay.workflow.constant;

public enum ApproveOperationTypeEnum {
	APPROVE,//通过
	RETURNAPPROVE, //退回
	RETURNTOFIRST, //退回到客户经理
	REJECTAPPROVE, //拒绝
	NORMALEND, //流程结束
	OBJECTION;//异议状态
}
