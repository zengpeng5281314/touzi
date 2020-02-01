package com.mytest.admin.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.MParam;
import com.base.dao.MBeanDAO;
import com.base.service.MFrameworkService;
import com.mytest.admin.po.TFirstInfoPo;
import com.mytest.admin.po.THistoryInfoPo;
import com.mytest.utils.Page;

@Service
public class HistoryInfoService {

	@Autowired
	MBeanDAO mBeanDAO;
	@Autowired
	MFrameworkService mFrameworkService;
	
	public Page pageHistoryInfoInfoPo(Timestamp startTime, Timestamp endTime,
			Page page) {
		MParam mparam = new MParam();
		String hql = "";
		if (startTime != null && endTime != null)
			hql += " and dayTime>='" + startTime + "' and dayTime<='" + endTime + "' ";
		hql += " order by dayTime desc";
		mparam.setOrderbyHQL(hql);
		
		return mFrameworkService.listPageInfo(THistoryInfoPo.class, page, mparam);
	}
	
	public THistoryInfoPo getTHistoryInfoPo(long id){
		return mFrameworkService.get(THistoryInfoPo.class, String.valueOf(id));
	}
	
	public void updateTHistoryInfoPo(THistoryInfoPo historyInfoPo){
		mBeanDAO.saveOrUpdate(historyInfoPo);
	}
	
	public THistoryInfoPo getTHistoryInfoPo(Date dayTime){
		MParam mparam1 = new MParam();
		mparam1.add("status", 1);
		mparam1.add("dayTime", dayTime);
		List<THistoryInfoPo> list = mFrameworkService.list(THistoryInfoPo.class, mparam1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
