package com.mytest.web.controller.touzi;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
import com.mytest.admin.po.TXZDownUserInfoPo;
import com.mytest.admin.service.AnalyzeDataService;
import com.mytest.admin.service.DownLoadUserInfoService;
import com.mytest.admin.service.FirstInfoService;
import com.mytest.admin.service.HistoryInfoService;
import com.mytest.admin.service.RegistInfoService;
import com.mytest.admin.service.UserInfoService;
import com.mytest.utils.Arith;
import com.mytest.utils.JsonDateValueProcessor;
import com.mytest.utils.Page;
import com.mytest.utils.SessionStorage;
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
	@Autowired
	private DownLoadUserInfoService downLoadUserInfoService;
	@Autowired
	private AnalyzeDataService analyzeDataService;
	
	@RequestMapping("/test")
	@Transactional
	public void test(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
			@RequestParam(defaultValue = "", required = false, value = "str") String str,
			@RequestParam(defaultValue = "", required = false, value = "str1") String str1,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<TXZDownUserInfoPo> list = downLoadUserInfoService.allTXZDownUserInfoPoList(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (TXZDownUserInfoPo txzDownUserInfoPo : list) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			// DesiredCapabilities caps = setDownloadsPath();// 更改默认下载路径
			// WebDriver driver = new ChromeDriver(caps);
			WebDriver driver = new ChromeDriver();
			driver.get(txzDownUserInfoPo.getAddress());
			WebElement loginName = driver
					.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[1]/div[2]/div/input"));
			loginName.sendKeys(txzDownUserInfoPo.getUserName());
			WebElement loginPwd = driver
					.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div[2]/div/input"));
			loginPwd.sendKeys(txzDownUserInfoPo.getUserPwd());
			try {
				WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div/button"));
				loginBtn.click();
				Thread.sleep(1000);

				SessionStorage localStorage = new SessionStorage(driver);
				String sef = localStorage.getItemFromLocalStorage("token");
				System.out.println(sef);
				sef = "Bearer " + sef;

				// 起始时间
				str = "2020-09-10";
				// 结束时间
				str1 = "2020-09-14";
				Calendar start = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					start.setTime(format.parse(str));
					end.setTime(format.parse(str1));
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				while (start.before(end)) {
					String startTime = format.format(start.getTime());
					System.out.println(startTime);
					java.sql.Date dateNo = new java.sql.Date(start.getTime().getTime());
					start.add(Calendar.DAY_OF_MONTH, 1);
					String endTime = format.format(start.getTime());
					System.out.println(endTime);

					// 首页
					String fristContent = doGet(
							"http://47.102.51.23/admin/access/v1/statistics/index?pageIndex=0&pageSize=20&regChannel=ALL&startDate="
									+ startTime + "&endDate="+endTime,
							sef);
					analyzeDataService.analyzeTFirstInfoPoList(fristContent,dateNo);
					
					// 历史数据
					String historyContent = doGet(
							"http://47.102.51.23/admin/access/v1/statistics/history?pageIndex=1&pageSize=10000&startDate="
									+ startTime + "&endDate="+endTime,
							sef);
					analyzeDataService.analyzeHistoryInfoPoList(historyContent,dateNo);
					
					for (int i = 1; i < 40; i++) {
						// 会员列表
						String mebersContent = doGet(
								"http://47.102.51.23/admin/access/v1/statistics/userlist?pageIndex="+i+"&pageSize=50&startDate="
										+ startTime + "&endDate="+endTime+"&phone=&regChannel=ALL",
								sef);
						analyzeDataService.analyzeTRegistUserInfoPoList(mebersContent);
						Thread.sleep(100);
					}
//					
					
				}

				driver.quit();
			} catch (Throwable e11) {
				e11.printStackTrace();
				driver.quit();
			}
		}
	}
	
	
	
	public static String doGet(String loginUrl, String token) {
		try {

			CloseableHttpClient httpclient1 = HttpClients.createDefault();

			// 创建httpget.
			HttpGet httpget = new HttpGet(loginUrl);
			System.out.println("executing request " + httpget.getURI());
			httpget.setHeader("authorization", token);
			// 执行get请求.
			CloseableHttpResponse response = httpclient1.execute(httpget);

			// 获取响应实体
			HttpEntity entity = response.getEntity();
			System.out.println("--------------------------------------");
			// 打印响应状态
			System.out.println(response.getStatusLine());
			if (entity != null) {
				// 打印响应内容长度
				System.out.println("Response content length: " + entity.getContentLength());
				String content = EntityUtils.toString(entity);
				// 打印响应内容
				System.out.println("Response content: " + content);
				return content;
			}
			System.out.println("------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@RequestMapping("/first")
	@Transactional
	public ModelAndView first(@RequestParam(defaultValue = "0", required = false, value = "userId") long userId,
			@RequestParam(defaultValue = "", required = false, value = "startTime") String startTime,
			@RequestParam(defaultValue = "", required = false, value = "endTime") String endTime,
			@RequestParam(defaultValue = "1", required = false, value = "currentPage") int currentPage,
			@RequestParam(defaultValue = "500", required = false, value = "pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo userInfoPo = (TUserInfoPo) request.getAttribute("adminInfo");
//		if(userInfoPo==null)
//			return new ModelAndView("redirect:/login");
		// userId = userInfoPo.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp startT = null;
		Timestamp endT = null;
		if (!startTime.equals("") && !endTime.equals("")) {
			startTime = startTime + " 00:00:00";
			endTime = endTime + " 23:59:59";
			startT = new Timestamp(sdf.parse(startTime).getTime());
			endT = new Timestamp(sdf.parse(endTime).getTime());
		}else{
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf2.format(new Date(System.currentTimeMillis()));
			startTime = date + " 00:00:00";
			endTime = date + " 23:59:59";
			startT = new Timestamp(sdf.parse(startTime).getTime());
			endT = new Timestamp(sdf.parse(endTime).getTime());
		}
		Page page = new Page(currentPage, pageSize);

		Page pageList = firstInfoService.pageFirstInfoPo(startT, endT, page);
		List<TFirstInfoPo> list = (List<TFirstInfoPo>) pageList.getList();
		TFirstInfoPo tfp = new TFirstInfoPo();
		for (TFirstInfoPo tFirstInfoPo : list) {
			tfp.setRegiestNum(tfp.getRegiestNum() + Arith.mulInt(tFirstInfoPo.getRegiestNum(), tFirstInfoPo.getRegiestNumRate()));
			tfp.setCloseOutNum(tfp.getCloseOutNum() + Arith.mulInt(tFirstInfoPo.getCloseOutNum(), tFirstInfoPo.getCloseOutNumRate()));
			tfp.setFee(Arith.round(Arith.add(tfp.getFee(), Arith.mul(tFirstInfoPo.getFee(),tFirstInfoPo.getFeeRate())), 0));
			tfp.setMoneyNum(tfp.getMoneyNum() + Arith.mulInt(tFirstInfoPo.getMoneyNum(),tFirstInfoPo.getMoneyNumRate()));
			tfp.setRechargeMoney(Arith.add(tfp.getRechargeMoney(), Arith.mul(tFirstInfoPo.getRechargeMoney(),tFirstInfoPo.getRechargeMoneyRate())));
			tfp.setRechargeNum(tfp.getRechargeNum() + Arith.mulInt(tFirstInfoPo.getRechargeNum(),tFirstInfoPo.getRechargeNumRate()));
			tfp.setScheduledTotal(Arith.round(Arith.add(tfp.getScheduledTotal(), Arith.mul(tFirstInfoPo.getScheduledTotal(),tFirstInfoPo.getScheduledTotalRate())),2));
			tfp.setTicketProfit(Arith.add(tfp.getTicketProfit(), tFirstInfoPo.getTicketProfit()));
			tfp.setUnsubscribeMoney(Arith.add(tfp.getUnsubscribeMoney(), Arith.mul(tFirstInfoPo.getUnsubscribeMoney(),tFirstInfoPo.getUnsubscribeMoneyRate())));
			tfp.setUnsubscribeNum(tfp.getUnsubscribeNum() + Arith.mulInt(tFirstInfoPo.getUnsubscribeNum(),tFirstInfoPo.getUnsubscribeNumRate()));
			tfp.setUnsubscribeTotal(Arith.round(Arith.add(tfp.getUnsubscribeTotal(), Arith.mul(tFirstInfoPo.getUnsubscribeTotal(),tFirstInfoPo.getUnsubscribeTotalRate())),2));
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
		}else{
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf2.format(new Date(System.currentTimeMillis()));
			startTime = date + " 00:00:00";
			endTime = date + " 23:59:59";
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
		}else{
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf2.format(new Date(System.currentTimeMillis()));
			startTime = date + " 00:00:00";
			endTime = date + " 23:59:59";
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
			historyDTO.setRegiestNum(historyDTO.getRegiestNum() + Arith.mulInt(tHistoryInfoPo.getRegiestNum(), tHistoryInfoPo.getRegiestNumRate()) );
			historyDTO.setUseTicktNum(historyDTO.getUseTicktNum() + tHistoryInfoPo.getUseTicktNum());
		}
		historyDTO.updateRate();

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		model.put("historyDTO", JSONObject.fromObject(historyDTO, jsonConfig));
		return new ModelAndView("/touzi/historylist", model);
	}

}
