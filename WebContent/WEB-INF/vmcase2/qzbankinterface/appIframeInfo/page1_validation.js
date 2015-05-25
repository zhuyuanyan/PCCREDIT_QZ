var validator = $($formName).validate({
	rules:
    { 
		applyTime:{required:true},
		sex:{required:true},
		phone_1:{required:true},
		
		applyAmount:{required:true},
		applyDeadline:{required:true},
		applyUse:{required:true},
		applyEndTime:{required:true},
		monthRepay:{required:true},
		
		signDate:{required:true},
		
		commet:{required:true},
		
		managerTime:{required:true}
     },
messages:
    {
		applyTime:{required:"申请日期不能为空"},
		sex:{required:"性别不能为空"},
		phone_1:{required:"移动电话1不能为空"},
		
		applyAmount:{required:"贷款金额不能为空"},
		applyDeadline:{required:"贷款期限不能为空"},
		applyUse:{required:"贷款用途不能为空"},
		applyEndTime:{required:"申请到期日不能为空"},
		monthRepay:{required:"月还款能力不能为空"},
		
		signDate:{required:"签名日期不能为空"},
		
		commet:{required:"评论不能为空"},
		
		managerTime:{required:"日期不能为空"}
   },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});