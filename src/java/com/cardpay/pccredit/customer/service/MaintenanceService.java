/**
 * 
 */
package com.cardpay.pccredit.customer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.MaintenanceEndResultEnum;
import com.cardpay.pccredit.customer.dao.MaintenanceDao;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.Maintenance;
import com.cardpay.pccredit.customer.model.MaintenanceAction;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.customer.web.MaintenanceWeb;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.date.DateHelper;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午3:05:12
 */
@Service
public class MaintenanceService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private MaintenanceDao maintenanceDao;
	
	@Autowired
	private CustomerMarketingService customerMarketingService;
	/**
	 * 得到维护计划(browse)
	 * @param filter
	 * @return
	 */
	public QueryResult<MaintenanceForm> findMaintenancePlansByFilter(MaintenanceFilter filter){
		List<MaintenanceForm> plans = maintenanceDao.findMaintenancePlansByFilter(filter);
		int size = maintenanceDao.findMaintenancePlansCountByFilter(filter);
		QueryResult<MaintenanceForm> qr = new QueryResult<MaintenanceForm>(size,plans);
		return qr;
	}
	/**
	 * 得到维护计划(browse)(下属)
	 * @param filter
	 * @return
	 */
	public QueryResult<MaintenanceForm> findSubMaintenancePlansByFilter(MaintenanceFilter filter){
		List<MaintenanceForm> plans = maintenanceDao.findSubMaintenancePlansByFilter(filter);
		int size = maintenanceDao.findSubMaintenancePlansCountByFilter(filter);
		QueryResult<MaintenanceForm> qr = new QueryResult<MaintenanceForm>(size,plans);
		return qr;
	}
	/**
	 * 得到维护计划(change)
	 * @param maintenanceId
	 * @return
	 */
	public MaintenanceForm findMaintenanceById(String maintenanceId){
		return maintenanceDao.findMaintenanceById(maintenanceId);
	}
	/**
	 * 通过维护计划id得到实施记录
	 * @param id
	 * @return
	 */
	public List<MaintenanceWeb> findMaintenanceActionByMaintenanceId(String id){
		return maintenanceDao.findMaintenanceActionByMaintenanceId(id);
	}
	/**
	 * 检查维护计划是否重复
	 * @param customerId
	 * @param endReuslt
	 * @return
	 */
	public boolean checkRepeat(String customerId,MaintenanceEndResultEnum endResult){
		int i = maintenanceDao.checkRepeat(customerId, endResult.toString());
		return i>0?true:false;
	}
	/**
	 * 添加维护计划
	 * @param maintenance
	 * @return
	 */
	public String insertMaintenance(Maintenance maintenance){
		Date createdTime = new Date();
		String maintenanceDay = maintenance.getMaintenanceDay()==null?"":maintenance.getMaintenanceDay();
		Date maintenanceEndtime = DateHelper.shiftDay(createdTime, Integer.parseInt(maintenanceDay.equals("")?"0":maintenanceDay));
		maintenance.setCreatedTime(createdTime);
		maintenance.setMaintenanceEndtime(maintenanceEndtime);
		commonDao.insertObject(maintenance);
		return maintenance.getId();
	}
	/**
	 * 修改维护计划
	 * @param maintenance
	 * @return
	 */
	public boolean updateMaintenance(Maintenance maintenance){
		Date createdTime = commonDao.findObjectById(Maintenance.class, maintenance.getId()).getCreatedTime();
		String maintenanceDay = maintenance.getMaintenanceDay()==null?"":maintenance.getMaintenanceDay();
		Date maintenanceEndtime = DateHelper.shiftDay(createdTime, Integer.parseInt(maintenanceDay.equals("")?"0":maintenanceDay));
		maintenance.setMaintenanceEndtime(maintenanceEndtime);
		maintenance.setModifiedTime(new Date());
		int i = commonDao.updateObject(maintenance);
		return i>0?true:false;
	}
	/**
	 * 被动修改维护计划
	 * @param maintenance
	 * @return
	 */
	public boolean updateMaintenancePassive(Maintenance maintenance){
		maintenance.setModifiedTime(new Date());
		int i = commonDao.updateObject(maintenance);
		return i>0?true:false;
	}
	/**
	 * 添加维护计划实施记录
	 * @param maintenanceAction
	 * @return
	 */
	public String insertMaintenanceAction(MaintenanceAction maintenanceAction){
		maintenanceAction.setCreatedTime(new Date());
		commonDao.insertObject(maintenanceAction);
		return maintenanceAction.getId();
	}
	/**
	 * 复制出一条内容相同的维护计划
	 * @param maintenanceId
	 * @param endResult
	 * @param createdBy
	 * @return
	 */
	public String copyMaintenancePlan(String maintenanceId,MaintenanceEndResultEnum endResult,String createdBy){
		Maintenance maintenance = commonDao.findObjectById(Maintenance.class, maintenanceId);
		maintenance.setCreatedBy(createdBy);
		Date createdTime = new Date();
		maintenance.setCreatedTime(createdTime);
		String maintenanceDay = maintenance.getMaintenanceDay()==null?"":maintenance.getMaintenanceDay();
		Date maintenanceEndtime = DateHelper.shiftDay(createdTime , Integer.parseInt(maintenanceDay.equals("")?"0":maintenanceDay));
		maintenance.setMaintenanceEndtime(maintenanceEndtime);
		maintenance.setEndResult(endResult.toString());
		maintenance.setModifiedBy(null);
		maintenance.setModifiedTime(null);
		commonDao.insertObject(maintenance);
		return maintenance.getId();
	}
	/**
	 * 通过id查询维护计划实施记录
	 * @param id
	 * @return
	 */
	public MaintenanceAction findMaintenanceActionById(String id){
		return commonDao.findObjectById(MaintenanceAction.class, id);
	}
	/**
	 * 修改维护计划实施记录
	 * @param maintenanceAction
	 * @return
	 */
	public boolean updateMaintenanceAction(MaintenanceAction maintenanceAction){
		maintenanceAction.setModifiedTime(new Date());
		int i = commonDao.updateObject(maintenanceAction);
		return i>0?true:false;
	}
	/**
	 * 统计客户维护计划条数
	 * @param customerManagerId
	 * @param result
	 * @return
	 */
	public int findMaintenanceCountToday(String customerManagerId,String result){
		return maintenanceDao.findMaintenanceCountToday(customerManagerId, result,null,null);
	}
	/**
	 * 统计客户维护计划条数
	 * @param customerManagerId
	 * @param result
	 * @param day
	 * @return
	 */
	public int findMaintenanceCountByDay(String customerManagerId,String result,int day){
		if(day!=0){
			Date start = customerMarketingService.getStartTime(day);
			Date end = customerMarketingService.getEndTime(day);
			return maintenanceDao.findMaintenanceCountToday(customerManagerId, result,DateHelper.getDateFormat(start, "yyyy-MM-dd HH:mm:ss"),DateHelper.getDateFormat(end, "yyyy-MM-dd HH:mm:ss"));
		}else{
			return findMaintenanceCountToday(customerManagerId, result);
		}
		
	}
}
