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
import com.mytest.utils.Page;

@Service
public class FirstInfoService {

	@Autowired
	MBeanDAO mBeanDAO;
	@Autowired
	MFrameworkService mFrameworkService;

	public Page pageFirstInfoPo(Timestamp startTime, Timestamp endTime, Page page) {
		MParam mparam = new MParam();
		String hql = "";
		if (startTime != null && endTime != null)
			hql += " and dayTime>='" + startTime + "' and dayTime<='" + endTime + "' ";
		hql += " order by dayTime desc";
		mparam.setOrderbyHQL(hql);
		return mFrameworkService.listPageInfo(TFirstInfoPo.class, page, mparam);
	}

	public TFirstInfoPo getTFirstInfoPo(long id) {
		return mFrameworkService.get(TFirstInfoPo.class, String.valueOf(id));
	}

	public void updateTFirstInfoPo(TFirstInfoPo firstInfoPo) {
		mBeanDAO.saveOrUpdate(firstInfoPo);
	}

	public TFirstInfoPo getFTFirstInfoPo(Date dayTime) {
		MParam mparam1 = new MParam();
		mparam1.add("status", 1);
		mparam1.add("dayTime", dayTime);
		List<TFirstInfoPo> list = mFrameworkService.list(TFirstInfoPo.class, mparam1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
