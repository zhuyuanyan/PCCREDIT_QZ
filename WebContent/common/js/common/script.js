function Open(obj){//展开或隐藏box	
	var className=$(obj).parent().find("div[name=content]").attr("class");
	if(className=="display"){//隐藏时
		$(obj).parent().find("div[name=content]").attr("class","");
		$(obj).find("img").attr("src",contextPath+"/images/min.gif");
	}
	else{//展开时
		$(obj).parent().find("div[name=content]").attr("class","display");
		$(obj).find("img").attr("src","${contextPath}/images/max.gif");
	}
}
function Open2(obj){//展开或隐藏box	
	var className=$(obj).parent().find("div[name=content]").attr("class");
	if(className=="display information"){//隐藏时
		$(obj).parent().find("div[name=content]").attr("class","information");
		$(obj).find("img").attr("src",contextPath+"/images/min.gif");
	}
	else{//展开时
		$(obj).parent().find("div[name=content]").attr("class","display information");
		$(obj).find("img").attr("src","${contextPath}/images/max.gif");
	}
}
function selectTag(id,obj){//tab页
	$(".tags li").attr("class","");
	$(obj).attr("class","active");
	$(".Content div").hide();
	$("#"+id).show();
}
function addTd(table){	//表格添加行
	if(table=="zygys"){//主要供应商
		$("#"+table).append("<tr>" + 
								"<td><input type='text' name='NAME'/></td>" + 
								"<td><input type='text' name='RATE'/></td>" + 
								"<td><input type='text' name='CONDITION'/></td>" + 
							"</tr>");      
	}
	if(table=="zykh"){//主要客户
		$("#"+table).append("<tr>" + 
								"<td><input type='text' name='NAME'/></td>" + 
								"<td><input type='text' name='RATE'/></td>" + 
								"<td><input type='text' name='CONDITION'/></td>" + 
							"</tr>");      
	}
	if(table=="jkjl"){//借款记录
		$("#"+table).append("<tr>" + 
								"<td><input type='text' class='input' name='BANK_OR_OTHER_TYPE'/></td>" + 
								"<td><input type='text' class='input' name='PURPOSE'/></td>" + 
								"<td><input type='text' class='input' name='TOTAL_AMOUNT'/></td>" + 
								"<td><input type='text' class='input' name='LOAN_DATE' onmouseover='_calendar()'/></td>" + 
								"<td><input type='text' class='input' name='DEADLINE'/></td>" + 
								"<td><input type='text' class='input' name='RATES'/></td>" + 
								"<td><input type='text' class='input' name='REPAY_TYPE'/></td>" + 
								"<td><input type='text' class='input' name='REMAIN_SUM'/></td>" + 
							"</tr>");      
	}
	if(table=="dkjl"){//贷款记录
		$("#"+table).append("<tr>" + 
								"<td><input type='text' class='input' name='LOAN_TYPE' /></td>	" +
								"<td><input type='text' class='input' name='AMOUNT' /></td>" +					
								"<td><input type='text' class='input' name='DEADLINE' /></td>" +	
								"<td><input type='text' class='input' name='LOAN_DATE' onmouseover='_calendar()'/></td>	" +
								"<td><input type='text' class='input' name='PURPOSE' /></td>" +					
								"<td><input type='text' class='input' name='REMAIN_AMOUNT' /></td>" +	
								"<td><input type='text' class='input' name='GUNT_FOR_OTHER' /></td>" +	
								"<td><input type='text' class='input' name='GUNT_AMOUNT' /></td>" +	
								"<td><input type='text' class='input' name='GUNT_DEADLINE' /></td>" +
							"</tr>");      
	}
	if(table=="fc"){//房产
		$("#"+table).append("<tr>" + 
								"<td><input type='text' class='input' name='ADDR' /></td>" + 	
								"<td><input type='text' class='input' name='PRICE' /></td>	" + 				
								"<td>" + 
									"<select name='USR_SITUATION'>" + 
										"<option>自住</option>" + 
										"<option>出租</option>" + 
										"<option>商业</option>" + 
										"<option>空置</option>" + 
									"</select>" + 
								"</td>	" + 
								"<td>" + 
									"<select name='HAVE_COPY'>" + 
										"<option>有</option>" + 
										"<option>无</option>" + 
									"</select>" + 
								"</td>	" + 	
							"</tr>");      
	}
	if(table=="jdc"){//机动车
		$("#"+table).append("<tr>" + 
								"<td><input type='text' class='input' name='CARD_NO' /></td>	" +
								"<td><input type='text' class='input' name='PRICE' /></td>	" +
								"<td><input type='text' class='input' name='BUY_DATE' onmouseover='_calendar()'/></td>	" +			
								"<td>" +
									"<select name='HAVE_COPY_1'>" +
										"<option>有</option>" +
										"<option>无</option>" +
									"</select>" +
								"</td>	" +
								"<td>" +
									"<select name='HAVE_COPY_2'>" +
										"<option>有</option>" +
										"<option>无</option>" +
									"</select>" +
								"</td>	" +	
							"</tr>");      
	}
	if(table=="htqdtz"){//合同签订台账
		$("#"+table).append("<tr>" + 
								"<td><input type='text' class='input' name='slrq' onmouseover='_calendar()'/></td>" + 
								"<td><input type='text' class='input' name='jkrxm'/></td>" + 
								"<td><input type='text' class='input' name='pzje'/></td>" + 
								"<td><input type='text' class='input' name='yyqdrq' onmouseover='_calendar()'/></td>" + 
								"<td><input type='text' class='input' name='sjqdrq' onmouseover='_calendar()'/></td>" + 
								"<td><input type='text' class='input' name='zbkhjl'/></td>" + 
								"<td><input type='text' class='input' name='jbr'/></td>" + 
								"<td><input type='text' class='input' name='bz'/></td>" + 
							"</tr>");      
	}
}

function removeTd(table){ //表格删除行 
	var tr= document.getElementById(table).getElementsByTagName("tr");
	if(tr.length>3){//至少要保留三行
		document.getElementById(table).deleteRow(tr.length-1);//删除最后一行	
	}
}