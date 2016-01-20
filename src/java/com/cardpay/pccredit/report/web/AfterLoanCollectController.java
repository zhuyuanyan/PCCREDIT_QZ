package com.cardpay.pccredit.report.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.report.filter.AccLoanCollectFilter;
import com.cardpay.pccredit.report.model.AccLoanCollectInfo;
import com.cardpay.pccredit.report.model.AccLoanInfo;
import com.cardpay.pccredit.report.service.AferAccLoanService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 贷款汇总查询（客户经理）
 * @author chinh
 *
 * 2014-11-27上午10:50:49
 */
@Controller
@RequestMapping("/report/afterLoancollect/*")
@JRadModule("report.afterLoancollect")
public class AfterLoanCollectController extends BaseController{
	
	@Autowired
	private AferAccLoanService aferAccLoanService;
	
	private static final Logger logger = Logger.getLogger(AferAccLoanController.class);
	/**
	 * 贷款汇总查询
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	public AbstractModelAndView browse(@ModelAttribute AccLoanCollectFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		if(StringUtils.isEmpty(filter.getStartDate())){
			filter.setStartDate("2013-08-01");
		}
		if(StringUtils.isEmpty(filter.getEndDate())){
			filter.setEndDate(DateHelper.getDateFormat(new Date(),"yyyy-MM-dd"));
		}
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		filter.setUserId(userId);
		
		List<AccLoanCollectInfo> accloanList = aferAccLoanService.getAccLoanCollect(filter);
		JRadModelAndView mv = new JRadModelAndView("/report/afteraccloan/afterAccLoanCollect_manager_browse", request);
		mv.addObject("list", accloanList);
		mv.addObject("filter", filter);
		return mv;
		
	}
	
	/**
	 * 贷款汇总查询(卡中心)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 * 新增用信客户:首次提款时间在选定时间段内的客户		 |新增用信余额:提款时间在选定时间段内 且在结束时间前未还清的借据
	 * 累计用信客户:结束时间前提过款的客户                                             |累计用信余额:结束时间前未还清的所有借据
	 * 新增授信客户:授信时间在选定时间段内的客户                              |新增授信余额:授信时间在选定时间段内的合同,且结束日期前未结束的合同
	 * 累计授信客户:结束时间前授信的客户                                                  |累计授信总额:结束时间前授信的合同,且结束日期前未结束的合同
	 * 新增逾期客户数:逾期时间在选定时间段内的客户                         |新增逾期余额:逾期时间在选定时间段内的借据
	 * 累计逾期客户数:逾期时间在结束时间前的客户                              |累计逾期余额:逾期时间在结束时间前的借据
	 * 用信余额（日均):(选定时间段内每天的用信余额求和)/时间段的天数
	 */
	@ResponseBody
	@RequestMapping(value = "browseAll.page", method = { RequestMethod.GET })
	public AbstractModelAndView browseAll(@ModelAttribute AccLoanCollectFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		if(StringUtils.isEmpty(filter.getStartDate())){
			filter.setStartDate("2013-08-01");
		}
		if(StringUtils.isEmpty(filter.getEndDate())){
			filter.setEndDate(DateHelper.getDateFormat(new Date(),"yyyy-MM-dd"));
		}
//		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
//		String userId = user.getId();
//		filter.setUserId(userId);
		
		List<AccLoanCollectInfo> accloanList = aferAccLoanService.getAccLoanCollect(filter);
		JRadModelAndView mv = new JRadModelAndView("/report/afteraccloan/afterAccLoanCollect_centre_browseAll", request);
		mv.addObject("list", accloanList);
		mv.addObject("filter", filter);
		return mv;
		
	}
}
