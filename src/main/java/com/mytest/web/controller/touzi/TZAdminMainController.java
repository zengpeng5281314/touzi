package com.mytest.web.controller.touzi;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mytest.admin.dto.HistoryDTO;
import com.mytest.admin.po.TFirstInfoPo;
import com.mytest.admin.po.THistoryInfoPo;
import com.mytest.admin.po.TRegistUserInfoPo;
import com.mytest.admin.po.TUserInfoPo;
import com.mytest.admin.service.FirstInfoService;
import com.mytest.admin.service.HistoryInfoService;
import com.mytest.admin.service.RegistInfoService;
import com.mytest.admin.service.UserInfoService;
import com.mytest.utils.Arith;
import com.mytest.utils.JsonDateValueProcessor;
import com.mytest.utils.Page;
import com.mytest.web.controller.base.BaseController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/tz/admin")
public class TZAdminMainController extends BaseController {

	Log logger = LogFactory.getLog(TZAdminMainController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private FirstInfoService firstInfoService;
	@Autowired
	private HistoryInfoService historyInfoService;
	@Autowired
	private RegistInfoService registInfoService;

	@RequestMapping("/first")
	@Transactional
	public ModelAndView first(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
			@RequestParam(defaultValue = "", required = false, value = "startTime") String startTime,
			@RequestParam(defaultValue = "", required = false, value = "endTime") String endTime,
			@RequestParam(defaultValue = "1", required = false, value = "currentPage") int currentPage,
			@RequestParam(defaultValue = "50", required = false, value = "pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		// userId = userInfoPo.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp startT = null;
		Timestamp endT = null;
		if (!startTime.equals("") && !endTime.equals("")) {
			startTime = startTime + " 00:00:00";
			endTime = endTime + " 23:59:59";
			startT = new Timestamp(sdf.parse(startTime).getTime());
			endT = new Timestamp(sdf.parse(endTime).getTime());
		}
		Page page = new Page(currentPage, pageSize);

		Page pageList = firstInfoService.pageFirstInfoPo(startT, endT, page);

		List<TFirstInfoPo> list = (List<TFirstInfoPo>) pageList.getList();
		for (TFirstInfoPo tFirstInfoPo : list) {
			tFirstInfoPo.setFee(Arith.round(tFirstInfoPo.getFee(),0));
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		model.put("firstInfoPoList", JSONArray.fromObject(list, jsonConfig));
		// return new ModelAndView("/touzi/firstadmin", model);

		return new ModelAndView("/touzi/firstadmin", model);
	}

	@RequestMapping("/editfirstadminshow")
	@Transactional
	public ModelAndView editfirstadminshow(@RequestParam(defaultValue = "0", required = false, value = "id") long id,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		// userId = userInfoPo.getId();
		TFirstInfoPo firstInfoPo = firstInfoService.getTFirstInfoPo(id);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		model.put("firstInfoPo", JSONObject.fromObject(firstInfoPo, jsonConfig));
		return new ModelAndView("/touzi/editfirstadmin", model);
	}

	@RequestMapping("/editfirstadmin")
	@Transactional
	@ResponseBody
	public String editfirstadmin(
			@RequestParam(defaultValue = "0", required = false, value = "firstInfoPoid") long firstInfoPoid,
			@RequestParam(defaultValue = "0", required = false, value = "regiestNum") int regiestNum,
			@RequestParam(defaultValue = "0.00", required = false, value = "regiestNumRate") double regiestNumRate,
			@RequestParam(defaultValue = "0.00", required = false, value = "ticketProfit") double ticketProfit,
			@RequestParam(defaultValue = "0", required = false, value = "rechargeNum") int rechargeNum,
			@RequestParam(defaultValue = "0", required = false, value = "rechargeNumRate") double rechargeNumRate,
			@RequestParam(defaultValue = "0.00", required = false, value = "rechargeMoney") double rechargeMoney,
			@RequestParam(defaultValue = "0.00", required = false, value = "rechargeMoneyRate") double rechargeMoneyRate,
			@RequestParam(defaultValue = "0", required = false, value = "closeOutNum") int closeOutNum,
			@RequestParam(defaultValue = "0.00", required = false, value = "closeOutNumRate") double closeOutNumRate,
			@RequestParam(defaultValue = "0.00", required = false, value = "fee") double fee,
			@RequestParam(defaultValue = "0.00", required = false, value = "feeRate") double feeRate,
			@RequestParam(defaultValue = "0.00", required = false, value = "scheduledTotal") double scheduledTotal,
			@RequestParam(defaultValue = "0.00", required = false, value = "scheduledTotalRate") double scheduledTotalRate,
			@RequestParam(defaultValue = "0.00", required = false, value = "unsubscribeTotal") double unsubscribeTotal,
			@RequestParam(defaultValue = "0.00", required = false, value = "unsubscribeTotalRate") double unsubscribeTotalRate,
			@RequestParam(defaultValue = "0", required = false, value = "unsubscribeNum") int unsubscribeNum,
			@RequestParam(defaultValue = "0.00", required = false, value = "unsubscribeNumRate") double unsubscribeNumRate,
			@RequestParam(defaultValue = "0.00", required = false, value = "unsubscribeMoney") double unsubscribeMoney,
			@RequestParam(defaultValue = "0.00", required = false, value = "unsubscribeMoneyRate") double unsubscribeMoneyRate,
			@RequestParam(defaultValue = "0", required = false, value = "moneyNum") int moneyNum,
			@RequestParam(defaultValue = "0.00", required = false, value = "moneyNumRate") double moneyNumRate,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		// // userId = userInfoPo.getId();
		TFirstInfoPo firstInfoPo = firstInfoService.getTFirstInfoPo(firstInfoPoid);
		if (firstInfoPo == null)
			return errorJson("信息有误！请退出后重试");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		String date2 = sdf.format(firstInfoPo.getDayTime());
//		if (!date.equals(date2))
//			return errorJson("非当天数据，禁止录入");

		THistoryInfoPo historyInfoPo = historyInfoService.getTHistoryInfoPo(new Date(new java.util.Date().getTime()));
		if (firstInfoPo.getRegiestNum() <= regiestNum && historyInfoPo.getRegiestNum() <= regiestNum) {
			historyInfoPo.setRegiestNum(regiestNum);
			historyInfoPo.setRegiestNumRate(regiestNumRate);
			historyInfoService.updateTHistoryInfoPo(historyInfoPo);
			firstInfoPo.setRegiestNum(regiestNum);
			firstInfoPo.setRegiestNumRate(regiestNumRate);
		}
		if (firstInfoPo.getTicketProfit() < ticketProfit)
			firstInfoPo.setTicketProfit(ticketProfit);
		if (firstInfoPo.getRechargeNum() < rechargeNum)
			firstInfoPo.setRechargeNum(rechargeNum);
		firstInfoPo.setRechargeNumRate(rechargeNumRate);
		if (firstInfoPo.getRechargeMoney() < rechargeMoney)
			firstInfoPo.setRechargeMoney(rechargeMoney);
		firstInfoPo.setRechargeMoneyRate(rechargeMoneyRate);
		if (firstInfoPo.getCloseOutNum() < closeOutNum)
			firstInfoPo.setCloseOutNum(closeOutNum);
		firstInfoPo.setCloseOutNumRate(closeOutNumRate);
		if (firstInfoPo.getFee() < fee)
			firstInfoPo.setFee(fee);
		firstInfoPo.setFeeRate(feeRate);
		if (firstInfoPo.getScheduledTotal() < scheduledTotal)
			firstInfoPo.setScheduledTotal(scheduledTotal);
		firstInfoPo.setScheduledTotalRate(scheduledTotalRate);
		if (firstInfoPo.getUnsubscribeTotal() < unsubscribeTotal)
			firstInfoPo.setUnsubscribeTotal(unsubscribeTotal);
		firstInfoPo.setUnsubscribeTotalRate(unsubscribeTotalRate);
		if (firstInfoPo.getUnsubscribeNum() < unsubscribeNum)
			firstInfoPo.setUnsubscribeNum(unsubscribeNum);
		firstInfoPo.setUnsubscribeNumRate(unsubscribeNumRate);
		if (firstInfoPo.getUnsubscribeMoney() < unsubscribeMoney)
			firstInfoPo.setUnsubscribeMoney(unsubscribeMoney);
		if (firstInfoPo.getMoneyNum() < moneyNum)
			firstInfoPo.setMoneyNum(moneyNum);
		firstInfoPo.setMoneyNumRate(moneyNumRate);
		firstInfoPo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		firstInfoService.updateTFirstInfoPo(firstInfoPo);
		return successJson("修改成功", null);
	}

	@RequestMapping("/history")
	@Transactional
	public ModelAndView history(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
			@RequestParam(defaultValue = "", required = false, value = "startTime") String startTime,
			@RequestParam(defaultValue = "", required = false, value = "endTime") String endTime,
			@RequestParam(defaultValue = "1", required = false, value = "currentPage") int currentPage,
			@RequestParam(defaultValue = "10", required = false, value = "pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		// userId = userInfoPo.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp startT = null;
		Timestamp endT = null;
		if (!startTime.equals("") && !endTime.equals("")) {
			startTime = startTime + " 00:00:00";
			endTime = endTime + " 23:59:59";
			startT = new Timestamp(sdf.parse(startTime).getTime());
			endT = new Timestamp(sdf.parse(endTime).getTime());
		}
		Page page = new Page(currentPage, pageSize);
		Page pageList = historyInfoService.pageHistoryInfoInfoPo(startT, endT, page);
		List<THistoryInfoPo> list = (List<THistoryInfoPo>) pageList.getList();
		for (THistoryInfoPo tHistoryInfoPo : list) {
			tHistoryInfoPo.updateRate();
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		model.put("list", JSONArray.fromObject(list, jsonConfig));
		return new ModelAndView("/touzi/historyadmin", model);
	}

	@RequestMapping("/edithistoryshow")
	@Transactional
	public ModelAndView edithistoryshow(
			@RequestParam(defaultValue = "0", required = false, value = "historyInfoPoid") long historyInfoPoid,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		// userId = userInfoPo.getId();
		THistoryInfoPo historyInfoPo = historyInfoService.getTHistoryInfoPo(historyInfoPoid);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		model.put("historyInfoPo", JSONObject.fromObject(historyInfoPo, jsonConfig));
		return new ModelAndView("/touzi/edithistoryshow", model);
	}

	@RequestMapping("/edithistory")
	@Transactional
	@ResponseBody
	public String edithistory(
			@RequestParam(defaultValue = "0", required = false, value = "historyInfoPoid") long historyInfoPoid,
			@RequestParam(defaultValue = "0", required = false, value = "moneyRechargeNum") int moneyRechargeNum,
			@RequestParam(defaultValue = "0.00", required = false, value = "fristMoney") double fristMoney,
			@RequestParam(defaultValue = "0", required = false, value = "fristNum") int fristNum,
			@RequestParam(defaultValue = "0", required = false, value = "regiestNum") int regiestNum,
			@RequestParam(defaultValue = "0", required = false, value = "useTicktNum") int useTicktNum,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
		// // userId = userInfoPo.getId();
		THistoryInfoPo historyInfoPo = historyInfoService.getTHistoryInfoPo(historyInfoPoid);
		if (historyInfoPo == null)
			return errorJson("信息有误！请退出后重试");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		String date2 = sdf.format(historyInfoPo.getDayTime());
//		if (!date.equals(date2))
//			return errorJson("非当天数据，禁止录入");
		TFirstInfoPo firstInfoPo = firstInfoService.getFTFirstInfoPo(new Date(new java.util.Date().getTime()));
		if (firstInfoPo.getRegiestNum() < regiestNum && historyInfoPo.getRegiestNum() < regiestNum) {
			historyInfoPo.setRegiestNum(regiestNum);
			firstInfoPo.setRegiestNum(regiestNum);
			firstInfoService.updateTFirstInfoPo(firstInfoPo);
		}
		if (historyInfoPo.getMoneyRechargeNum() < moneyRechargeNum)
			historyInfoPo.setMoneyRechargeNum(moneyRechargeNum);
		if (historyInfoPo.getFristMoney() < fristMoney)
			historyInfoPo.setFristMoney(fristMoney);
		if (historyInfoPo.getFristNum() < fristNum)
			historyInfoPo.setFristNum(fristNum);
		if (historyInfoPo.getUseTicktNum() < useTicktNum)
			historyInfoPo.setUseTicktNum(useTicktNum);
		historyInfoPo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		historyInfoService.updateTHistoryInfoPo(historyInfoPo);
		return successJson("修改成功", null);
	}

}
