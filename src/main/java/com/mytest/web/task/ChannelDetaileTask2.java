package com.mytest.web.task;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mytest.admin.service.AnalyzeDataService;

@Component
public class ChannelDetaileTask2 {

	Log logger = LogFactory.getLog(ChannelDetaileTask2.class);

	@Autowired
	private AnalyzeDataService analyzeDataService;

//	/**
//	 * 每天凌晨1点执行一次
//	 */
//	@Scheduled(cron = "0 0 1 * * ?")
//	public void flushChannelDetaile() {
//		Calendar start = Calendar.getInstance();
//		java.sql.Date dateNo = new java.sql.Date(start.getTime().getTime());
//		analyzeDataService.saveTFirstInfoPo(dateNo);
//		analyzeDataService.saveTHistoryInfoPo(dateNo);
//
//	}

}
