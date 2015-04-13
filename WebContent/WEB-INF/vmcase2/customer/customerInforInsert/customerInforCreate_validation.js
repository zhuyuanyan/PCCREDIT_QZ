var validator = $($formName).validate({
	rules:
    { chineseName:
           {required:true,
            },
    pinyinenglishName:
          {required:true,
           },
     nationality:
        {required:true,
              },
              cardType:
              {required:true,
                    },
                    cardId:
                    {required:true,
                          },
    telephone:
           {
           maxlength:11}
    
     },
messages:
    {
	chineseName:
        {required:"中文姓名不能为空"
           },
    pinyinenglishName:
       {required:"拼音或英文姓名不能为空"
           },
    nationality:
           {required:"国籍不能为空"
               },
               cardType:
               {required:"身份证件类型不能为空",
                     },
                     cardId:
                     {required:"证件号码不能为空",
                           },
    telephone:
        {
        maxlength:"手机号码长度不能超过11位"},
   
 },
	errorPlacement : function(error, element) {
		element.after(error);
		if(layout){
			layout.resizeLayout();
		}
	}
});