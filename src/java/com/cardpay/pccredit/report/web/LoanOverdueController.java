package com.cardpay.pccredit.report.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.report.filter.OClpmAccLoanFilter;
import com.cardpay.pccredit.report.model.AccLoanInfo;
import com.cardpay.pccredit.report.model.AccLoanOverdueInfo;
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
 * 客户逾期查询清单（客户经理）
 * @author chinh
 *
 * 2014-11-27上午10:50:49
 */
@Controller
@RequestMapping("/report/loanoverdue/*")
@JRadModule("report.loanoverdue")
public class LoanOverdueController extends BaseController{
	
	@Autowired
	private AferAccLoanService aferAccLoanService;
	
	private static final Logger logger = Logger.getLogger(LoanOverdueController.class);
	
	/**
	 * 浏览客户逾期清单
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute OClpmAccLoanFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		filter.setUserId(userId);
		
		List<AccLoanOverdueInfo> overdue = aferAccLoanService.getLoanOverdue(filter);
		JRadModelAndView mv = new JRadModelAndView("/report/loanoverdue/accloanoverdue_manager_browse", request);
		mv.addObject("list", overdue);
		mv.addObject("filter", filter);
		return mv;
		
	}
	
	/**
	 * 浏览客户逾期清单(卡中心)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browseAll.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browseAll(@ModelAttribute OClpmAccLoanFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
//		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
//		String userId = user.getId();
//		filter.setUserId(userId);
		
		List<AccLoanOverdueInfo> overdue = aferAccLoanService.getLoanOverdue(filter);
		JRadModelAndView mv = new JRadModelAndView("/report/loanoverdue/accloanoverdue_centre_browseAll", request);
		mv.addObject("list", overdue);
		mv.addObject("filter", filter);
		return mv;
		
	}
}
