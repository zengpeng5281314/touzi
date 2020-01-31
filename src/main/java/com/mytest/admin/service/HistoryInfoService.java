package com.mytest.admin.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.MParam;
import com.base.dao.MBeanDAO;
import com.base.service.MFrameworkService;
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
		if (startTime != null && endTime != null)
			mparam.setOrderbyHQL(" and dayTime>='" + startTime + "' and dayTime<='" + endTime + "'");
		return mFrameworkService.listPageInfo(THistoryInfoPo.class, page, mparam);
	}
	
	public THistoryInfoPo getTHistoryInfoPo(long id){
		return mFrameworkService.get(THistoryInfoPo.class, String.valueOf(id));
	}
	
	public void updateTHistoryInfoPo(THistoryInfoPo historyInfoPo){
		mBeanDAO.saveOrUpdate(historyInfoPo);
	}
	
}
