package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.comdao.ManagerPromotionRuleComdao;
import com.cardpay.pccredit.manager.model.ManagerPromotionRule;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-21 下午4:27:11
 */
@Service
public class ManagerPromotionRuleService {
	
	
	@Autowired
	private CommonDao commonDao;
	
	
	@Autowired
	private ManagerPromotionRuleComdao managerPromotionRuleComdao;
	
	/**
	 * 查询客户经理晋级规则
	 * @return
	 */
	public List<ManagerPromotionRule> getManagerPromotionRule(){
	
		return managerPromotionRuleComdao.getManagerPromotionRule();
		
	}
	
	/**
	 * 修改客户经理晋级规则
	 * @param request
	 */
	public void updateManagerPromotionRule(HttpServletRequest request) {
		
		Calendar calendar = Calendar.getInstance();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		String[] id= request.getParameterValues("id");
		String[] initialLevel= request.getParameterValues("initialLevel");
		String[] quarterAverageOverBalance= request.getParameterValues("quarterAverageOverBalance");
		String[] teamDailyOverdraftBalance= request.getParameterValues("teamDailyOverdraftBalance");
		String[] levelTeamMember= request.getParameterValues("levelTeamMember");
		String[] subMangerNumber= request.getParameterValues("subMangerNumber");
		String[] tubeNumber= request.getParameterValues("tubeNumber");
		String[] activationRate= request.getParameterValues("activationRate");
		String[] activeRate= request.getParameterValues("activeRate");
		String[] promotionLevel= request.getParameterValues("promotionLevel");
		
		for(int i=0;i<id.length;i++){
			ManagerPromotionRule managerPromotionRule = new ManagerPromotionRule();
			String idValue= id[i];
			String initialLevelValue=initialLevel[i];
			
			if(quarterAverageOverBalance[i] !=null && quarterAverageOverBalance[i] !=""){
				
				Double quarterAverageOverBalanceDouble = Double.parseDouble(quarterAverageOverBalance[i]) * 1000000;
				String quarterAverageOverBalanceValue = quarterAverageOverBalanceDouble.toString();
				managerPromotionRule.setQuarterAverageOverBalance(quarterAverageOverBalanceValue);
				
			}
            if(teamDailyOverdraftBalance[i] !=null && teamDailyOverdraftBalance[i] !=""){
				
				Double teamDailyOverdraftBalanceDouble = Double.parseDouble(teamDailyOverdraftBalance[i]) * 1000000;
				String teamDailyOverdraftBalanceValue = teamDailyOverdraftBalanceDouble.toString();
				managerPromotionRule.setTeamDailyOverdraftBalance(teamDailyOverdraftBalanceValue);
			}
			
			String levelTeamMemberValue=levelTeamMember[i];
			String activationRateValue=activationRate[i];
			String activeRateValue=activeRate[i];
			String promotionLevelValue=promotionLevel[i];
			
			managerPromotionRule.setInitialLevel(initialLevelValue);
			managerPromotionRule.setLevelTeamMember(levelTeamMemberValue);
			if(subMangerNumber[i] !="" && subMangerNumber[i] !=null){
				int subMangerNumberValue=Integer.parseInt(subMangerNumber[i]);
				managerPromotionRule.setSubMangerNumber(subMangerNumberValue);
				
			}
			if(tubeNumber[i] !="" && tubeNumber[i] !=null){
				int tubeNumberValue=Integer.parseInt(tubeNumber[i]);
				managerPromotionRule.setTubeNumber(tubeNumberValue);
				
			}
			
			managerPromotionRule.setActivationRate(activationRateValue);
			managerPromotionRule.setActiveRate(activeRateValue);
			managerPromotionRule.setPromotionLevel(promotionLevelValue);
			managerPromotionRule.setModifiedBy(userId);
			managerPromotionRule.setModifiedTime(calendar.getTime());
			
			if(idValue != null && idValue !=""){
				managerPromotionRule.setId(idValue);
				commonDao.updateObject(managerPromotionRule);
			}else{
				managerPromotionRule.setCreatedBy(userId);
				managerPromotionRule.setCreatedTime(calendar.getTime());
				commonDao.insertObject(managerPromotionRule);		
					
			}
	
		}
		
		
	}
	
}
