package com.mytest.web.controller.touzi;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
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
@RequestMapping("/tz")
public class TZMainController extends BaseController {

	Log logger = LogFactory.getLog(TZMainController.class);
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
		TFirstInfoPo tfp = new TFirstInfoPo();
		for (TFirstInfoPo tFirstInfoPo : list) {
			tfp.setCloseOutNum(tfp.getCloseOutNum() + tFirstInfoPo.getCloseOutNum());
			tfp.setFee(Arith.add(tfp.getFee(), tFirstInfoPo.getFee()));
			tfp.setMoneyNum(tfp.getMoneyNum() + tFirstInfoPo.getMoneyNum());
			tfp.setRechargeMoney(Arith.add(tfp.getRechargeMoney(), tFirstInfoPo.getRechargeMoney()));
			tfp.setRechargeNum(tfp.getRechargeNum() + tFirstInfoPo.getRechargeNum());
			tfp.setScheduledTotal(Arith.add(tfp.getScheduledTotal(), tFirstInfoPo.getScheduledTotal()));
			tfp.setTicketProfit(Arith.add(tfp.getTicketProfit(), tFirstInfoPo.getTicketProfit()));
			tfp.setUnsubscribeMoney(Arith.add(tfp.getUnsubscribeMoney(), tFirstInfoPo.getUnsubscribeMoney()));
			tfp.setUnsubscribeNum(tfp.getUnsubscribeNum() + tFirstInfoPo.getUnsubscribeNum());
			tfp.setUnsubscribeTotal(Arith.add(tfp.getUnsubscribeTotal(), tFirstInfoPo.getUnsubscribeTotal()));
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		model.put("firstInfoPo", JSONObject.fromObject(tfp, jsonConfig));
		return new ModelAndView("/touzi/first", model);
	}

	@RequestMapping("/regist")
	@Transactional
	public ModelAndView regist(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
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
		Page pageList = registInfoService.pageRegistInfoInfoPo(startT, endT, page);
		List<TRegistUserInfoPo> list = (List<TRegistUserInfoPo>) pageList.getList();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		model.put("registInfoList", JSONArray.fromObject(list, jsonConfig));
		model.put("total", pageList.getTotal());
		model.put("currentPage", currentPage);
		model.put("beforePage", currentPage <= 1 ? 1 : currentPage - 1);
		model.put("nextPage", pageList.getPagecount() > currentPage ? currentPage + 1 : currentPage);
		model.put("startTime", startT);
		model.put("endTime", endT);
		return new ModelAndView("/touzi/registlist", model);
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
		HistoryDTO historyDTO = new HistoryDTO();
		for (THistoryInfoPo tHistoryInfoPo : list) {
			historyDTO.setFristMoney(Arith.add(historyDTO.getFristMoney(), tHistoryInfoPo.getFristMoney()));
			historyDTO.setFristNum(historyDTO.getFristNum() + tHistoryInfoPo.getFristNum());
			historyDTO.setMoneyRechargeNum(historyDTO.getMoneyRechargeNum() + tHistoryInfoPo.getMoneyRechargeNum());
			historyDTO.setRegiestNum(historyDTO.getRegiestNum() + tHistoryInfoPo.getRegiestNum());
			historyDTO.setUseTicktNum(historyDTO.getUseTicktNum() + tHistoryInfoPo.getUseTicktNum());
		}
		historyDTO.updateRate();

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		model.put("historyDTO", JSONObject.fromObject(historyDTO, jsonConfig));
		return new ModelAndView("/touzi/historylist", model);
	}

}
