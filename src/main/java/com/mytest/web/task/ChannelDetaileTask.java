package com.mytest.web.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mytest.admin.po.TXZDownUserInfoPo;
import com.mytest.admin.service.AnalyzeDataService;
import com.mytest.admin.service.DownLoadService;
import com.mytest.admin.service.DownLoadUserInfoService;
import com.mytest.utils.SessionStorage;

@Component
public class ChannelDetaileTask {

	Log logger = LogFactory.getLog(ChannelDetaileTask.class);

	@Autowired
	private DownLoadUserInfoService downLoadUserInfoService;
	@Autowired
	private AnalyzeDataService analyzeDataService;

	/**
	 * 每1分钟执行一次
	 */
	@Scheduled(cron = "0 */2 * * * ?")
	public void flushChannelDetaile() {
		List<TXZDownUserInfoPo> list = downLoadUserInfoService.allTXZDownUserInfoPoList(1);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String date = sdf.format(new Date());
		for (TXZDownUserInfoPo txzDownUserInfoPo : list) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			// DesiredCapabilities caps = setDownloadsPath();// 更改默认下载路径
			// WebDriver driver = new ChromeDriver(caps);
			WebDriver driver = new ChromeDriver();
			try {
				driver.get(txzDownUserInfoPo.getAddress());
				WebElement loginName = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[1]/div[2]/div/input"));
				loginName.sendKeys(txzDownUserInfoPo.getUserName());
				WebElement loginPwd = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div[2]/div/input"));
				loginPwd.sendKeys(txzDownUserInfoPo.getUserPwd());

				WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div/button"));
				loginBtn.click();
				Thread.sleep(1000);

				SessionStorage localStorage = new SessionStorage(driver);
				String sef = localStorage.getItemFromLocalStorage("token");
				System.out.println(sef);
				sef = "Bearer " + sef;

				Calendar start = Calendar.getInstance();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				String startTime = "";
				String endTime = "";
				java.sql.Date dateNo = null;
				if (start.getTime().getHours() >= 5) {
					startTime = format.format(start.getTime());
					dateNo = new java.sql.Date(start.getTime().getTime());
					start.add(Calendar.DAY_OF_MONTH, 1);
					endTime = format.format(start.getTime());
				} else {
					endTime = format.format(start.getTime());
					start.add(Calendar.DAY_OF_MONTH, -1);
					startTime = format.format(start.getTime());
					dateNo = new java.sql.Date(start.getTime().getTime());
				}

				// 首页
				String fristContent = doGet(
						"http://47.102.51.23/admin/access/v1/statistics/index?pageIndex=0&pageSize=10000&regChannel=ALL&startDate="
								+ startTime + "&endDate=" + endTime,
						sef);
				analyzeDataService.analyzeTFirstInfoPoList(fristContent, dateNo);
				// 历史数据
				String historyContent = doGet(
						"http://47.102.51.23/admin/access/v1/statistics/history?pageIndex=1&pageSize=100&startDate="
								+ startTime + "&endDate=" + endTime,
						sef);
				analyzeDataService.analyzeHistoryInfoPoList(historyContent, dateNo);
				for (int i = 1; i < 3; i++) {
					// 会员列表
					String mebersContent = doGet("http://47.102.51.23/admin/access/v1/statistics/userlist?pageIndex="
							+ i + "&pageSize=20&startDate=" + startTime + "&endDate=" + endTime
							+ "&phone=&regChannel=ALL", sef);
					analyzeDataService.analyzeTRegistUserInfoPoList(mebersContent);

				}

				// // 首页
				// String fristContent = doGet(
				// "http://47.102.51.23/admin/access/v1/statistics/index?pageIndex=0&pageSize=10000&regChannel=ALL&startDate="
				// + date + "&endDate=",
				// sef);
				// analyzeDataService.analyzeTFirstInfoPoList(fristContent);
				// // 会员列表
				// String mebersContent = doGet(
				// "http://47.102.51.23/admin/access/v1/statistics/userlist?pageIndex=1&pageSize=100&startDate="
				// + date + "&endDate=&phone=&regChannel=ALL",
				// sef);
				// analyzeDataService.analyzeTRegistUserInfoPoList(mebersContent);
				//
				// // 历史数据
				// String historyContent = doGet(
				// "http://47.102.51.23/admin/access/v1/statistics/history?pageIndex=1&pageSize=10000&startDate="
				// + date + "&endDate=",
				// sef);
				// analyzeDataService.analyzeHistoryInfoPoList(historyContent);

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

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar start1 = Calendar.getInstance();
		start1.add(Calendar.DAY_OF_MONTH, -1);
		System.out.println(format.format(start1.getTime()));
		System.out.println(start1.getTime().getHours());
		// 起始时间
		String str = "2019-11-24";
		// 结束时间
		String str1 = "2020-01-30";
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		try {
			start.setTime(format.parse(str));
			end.setTime(format.parse(str1));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		while (start.before(end)) {
			System.out.println(format.format(start.getTime()));
			start.add(Calendar.DAY_OF_MONTH, 1);
			System.out.println(format.format(start.getTime()));
		}
	}
}
