package com.mytest.admin.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.MParam;
import com.base.dao.MBeanDAO;
import com.base.service.MFrameworkService;
import com.mytest.admin.po.TRegistUserInfoPo;
import com.mytest.utils.Page;

@Service
public class RegistInfoService {

	@Autowired
	MBeanDAO mBeanDAO;
	@Autowired
	MFrameworkService mFrameworkService;
	
	public Page pageRegistInfoInfoPo(Timestamp startTime, Timestamp endTime,
			Page page) {
		MParam mparam = new MParam();
		String hql = "";
		if (startTime != null && endTime != null)
			hql += " and registTime>='" + startTime + "' and registTime<='" + endTime + "' order by registTime desc";
		hql += " order by registTime desc";
		mparam.setOrderbyHQL(hql);
		return mFrameworkService.listPageInfo(TRegistUserInfoPo.class, page, mparam);
	}
	
}
