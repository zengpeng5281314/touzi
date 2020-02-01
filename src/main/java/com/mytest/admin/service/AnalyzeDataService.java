package com.mytest.admin.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.MParam;
import com.base.dao.MBeanDAO;
import com.base.service.MFrameworkService;
import com.mytest.admin.po.TFirstInfoPo;
import com.mytest.admin.po.THistoryInfoPo;
import com.mytest.admin.po.TRegistUserInfoPo;
import com.mytest.admin.po.TXZDownUserInfoPo;
import com.mytest.utils.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class AnalyzeDataService {

	@Autowired
	MBeanDAO mBeanDAO;
	@Autowired
	MFrameworkService mFrameworkService;

	public List<TFirstInfoPo> allTFirstInfoPoList(Timestamp startTime, Timestamp endTime) {
		MParam mparam = new MParam();
		mparam.add("status", 1);

		return mFrameworkService.listAll(TXZDownUserInfoPo.class, mparam);
	}

	public void analyzeTFirstInfoPoList(String content, Date dayTime) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject json = JSONObject.fromObject(content, jsonConfig);
		JSONObject data = json.getJSONObject("data");

		int registerCount = data.getInt("registerCount");
		double ticketProfitLoss = Double.valueOf(data.getString("ticketProfitLoss"));
		int rechargeUsers = data.getInt("rechargeUsers");
		double rechargeMoney = Double.valueOf(data.getString("rechargeMoney"));
		int closeOrderCount = data.getInt("closeOrderCount");
		double totalFee = Double.valueOf(data.getString("totalFee"));
		double totalProfitLoss = Double.valueOf(data.getString("totalProfitLoss"));
		double closeTotalProfitLoss = Double.valueOf(data.getString("closeTotalProfitLoss"));
		int orderUsers = data.getInt("orderUsers");
		double orderMoney = Double.valueOf(data.getString("orderMoney"));
		int cashOrderUsers = data.getInt("cashOrderUsers");
		MParam mparam = new MParam();
		mparam.add("status", 1);
		mparam.add("dayTime", dayTime);
		List<TFirstInfoPo> list = mFrameworkService.list(TFirstInfoPo.class, mparam);
		TFirstInfoPo t = null;
		if (list != null && list.size() > 0) {
			t = list.get(0);
			if (t.getRegiestNum() < registerCount)
				t.setRegiestNum(registerCount);
			if (t.getTicketProfit() < ticketProfitLoss)
				t.setTicketProfit(ticketProfitLoss);
			if (t.getRechargeNum() < rechargeUsers)
				t.setRechargeNum(rechargeUsers);
			if (t.getRechargeMoney() < rechargeMoney)
				t.setRechargeMoney(rechargeMoney);
			if (t.getCloseOutNum() < closeOrderCount)
				t.setCloseOutNum(closeOrderCount);
			if (t.getFee() < totalFee)
				t.setFee(totalFee);
			if (t.getScheduledTotal() < totalProfitLoss)
				t.setScheduledTotal(totalProfitLoss);
			if (t.getUnsubscribeTotal() < closeTotalProfitLoss)
				t.setUnsubscribeTotal(closeTotalProfitLoss);
			if (t.getUnsubscribeNum() < orderUsers)
				t.setUnsubscribeNum(orderUsers);
			if (t.getUnsubscribeMoney() < orderMoney)
				t.setUnsubscribeMoney(orderMoney);
			if (t.getMoneyNum() < cashOrderUsers)
				t.setMoneyNum(cashOrderUsers);
			t.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		} else {
			t = new TFirstInfoPo();
			t.setRegiestNum(registerCount);
			t.setRegiestNumRate(1.00d);
			t.setDayTime(dayTime);
			t.setTicketProfit(ticketProfitLoss);
			t.setRechargeNum(rechargeUsers);
			t.setRechargeNumRate(1.00d);
			t.setRechargeMoney(rechargeMoney);
			t.setRechargeMoneyRate(1.00d);
			t.setCloseOutNum(closeOrderCount);
			t.setCloseOutNumRate(1.00d);
			t.setFee(totalFee);
			t.setFeeRate(1.00d);
			t.setScheduledTotal(totalProfitLoss);
			t.setUnsubscribeTotal(closeTotalProfitLoss);
			t.setUnsubscribeNum(orderUsers);
			t.setUnsubscribeMoney(orderMoney);
			t.setMoneyNum(cashOrderUsers);
			t.setCreateTime(new Timestamp(System.currentTimeMillis()));
			t.setStatus(1);
		}
		mBeanDAO.saveOrUpdate(t);

		MParam mparam1 = new MParam();
		mparam1.add("status", 1);
		mparam1.add("dayTime", dayTime);
		List<TFirstInfoPo> list1 = mFrameworkService.list(TFirstInfoPo.class, mparam1);
		if (list1 != null && list1.size() > 0) {
			TFirstInfoPo firstInfoPo = list1.get(0);
			if (firstInfoPo.getRegiestNum() < registerCount) {
				firstInfoPo.setRegiestNum(registerCount);
				mBeanDAO.saveOrUpdate(firstInfoPo);
			}
		}

	}

	public void analyzeTRegistUserInfoPoList(String registContent) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject json = JSONObject.fromObject(registContent, jsonConfig);
		JSONObject data = json.getJSONObject("data");
		JSONArray list = data.getJSONArray("list");
		for (int i = 0; i < list.size(); i++) {
			JSONObject res = list.getJSONObject(i);
			String balance = res.getString("balance");
			String createTime = res.getString("createTime");
			String lastLoginTime = res.getString("lastLoginTime");
			String phone = res.getString("phone");
			String regChannel = res.getString("regChannel");
			double totalRechargeMoney = Double.valueOf(res.getString("totalRechargeMoney"));
			String username = res.getString("username");
			MParam mparam = new MParam();
			mparam.add("status", 1);
			mparam.add("phone", phone);
			List<TRegistUserInfoPo> listTRegistUserInfoPo = mFrameworkService.list(TRegistUserInfoPo.class, mparam);
			TRegistUserInfoPo registUserInfoPo = null;
			if (listTRegistUserInfoPo != null && listTRegistUserInfoPo.size() > 0) {
				registUserInfoPo = listTRegistUserInfoPo.get(0);
				registUserInfoPo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			} else {
				registUserInfoPo = new TRegistUserInfoPo();
				registUserInfoPo.setPhone(phone);
				registUserInfoPo.setStatus(1);
			}
			registUserInfoPo.setChannelName(regChannel);
			registUserInfoPo.setLoginTime(Timestamp.valueOf(lastLoginTime));
			registUserInfoPo.setName(username);
			registUserInfoPo.setOverMoney(Double.valueOf(balance));
			registUserInfoPo.setRegistTime(Timestamp.valueOf(createTime));
			registUserInfoPo.setStatus(1);
			registUserInfoPo.setTotal_money(totalRechargeMoney);
			try {
				mBeanDAO.saveOrUpdate(registUserInfoPo);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public void analyzeHistoryInfoPoList(String historyContent, Date dayTime) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject json = JSONObject.fromObject(historyContent, jsonConfig);
		JSONObject data = json.getJSONObject("data");
		JSONObject totalStatistics = data.getJSONObject("totalStatistics");
		int cashOrderUsers = totalStatistics.getInt("cashOrderUsers");
		double rechargeMoney = Double.valueOf(totalStatistics.getString("rechargeMoney"));
		int rechargeUsers = totalStatistics.getInt("rechargeUsers");
		double rechargeUsersPecent = Double.valueOf(totalStatistics.getString("rechargeUsersPecent"));
		int rechargers = totalStatistics.getInt("rechargers");
		int registerCount = totalStatistics.getInt("registerCount");
		double ticketUserPecent = Double.valueOf(totalStatistics.getString("ticketUserPecent"));
		int ticketUsers = totalStatistics.getInt("ticketUsers");
		double totalFee = Double.valueOf(totalStatistics.getString("totalFee"));

		// Date dayTime = new Date(new java.util.Date().getTime());
		MParam mparam = new MParam();
		mparam.add("status", 1);
		mparam.add("dayTime", dayTime);
		List<THistoryInfoPo> listTHistoryInfoPo = mFrameworkService.list(THistoryInfoPo.class, mparam);
		THistoryInfoPo historyInfoPo = null;
		if (listTHistoryInfoPo != null && listTHistoryInfoPo.size() > 0) {
			historyInfoPo = listTHistoryInfoPo.get(0);
			historyInfoPo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		} else {
			historyInfoPo = new THistoryInfoPo();
			historyInfoPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			historyInfoPo.setDayTime(dayTime);
			historyInfoPo.setStatus(1);
		}
		if (historyInfoPo.getMoneyRechargeNum() < cashOrderUsers)
			historyInfoPo.setMoneyRechargeNum(cashOrderUsers);
		if (historyInfoPo.getFristMoney() < rechargeMoney)
			historyInfoPo.setFristMoney(rechargeMoney);
		if (historyInfoPo.getFristNum() < rechargeUsers)
			historyInfoPo.setFristNum(rechargeUsers);
		if (historyInfoPo.getRegiestNum() < registerCount)
			historyInfoPo.setRegiestNum(registerCount);
		if (historyInfoPo.getUseTicktNum() < ticketUsers)
			historyInfoPo.setUseTicktNum(ticketUsers);

		mBeanDAO.saveOrUpdate(historyInfoPo);

	}

}
