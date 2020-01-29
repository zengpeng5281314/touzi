//package com.mytest.web.task;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.mytest.admin.po.TXZDownUserInfoPo;
//import com.mytest.admin.service.AnalyzeDataService;
//import com.mytest.admin.service.DownLoadService;
//import com.mytest.admin.service.DownLoadUserInfoService;
//import com.mytest.utils.SessionStorage;
//
//@Component
//public class ChannelDetaileTask {
//
//	Log logger = LogFactory.getLog(ChannelDetaileTask.class);
//
//	@Autowired
//	private DownLoadUserInfoService downLoadUserInfoService;
//	@Autowired
//	private AnalyzeDataService analyzeDataService;
//
//	/**
//	 * 每1分钟执行一次
//	 */
//	@Scheduled(cron = "0 */1 * * * ?")
//	public void flushChannelDetaile() {
//		List<TXZDownUserInfoPo> list = downLoadUserInfoService.allTXZDownUserInfoPoList(1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String date = sdf.format(new Date());
//		for (TXZDownUserInfoPo txzDownUserInfoPo : list) {
//			System.setProperty("webdriver.chrome.driver",
//					"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
//			// DesiredCapabilities caps = setDownloadsPath();// 更改默认下载路径
//			// WebDriver driver = new ChromeDriver(caps);
//			WebDriver driver = new ChromeDriver();
//			driver.get(txzDownUserInfoPo.getAddress());
//			WebElement loginName = driver
//					.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[1]/div[2]/div/input"));
//			loginName.sendKeys(txzDownUserInfoPo.getUserName());
//			WebElement loginPwd = driver
//					.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div[2]/div/input"));
//			loginPwd.sendKeys(txzDownUserInfoPo.getUserPwd());
//			try {
//				WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div/button"));
//				loginBtn.click();
//				Thread.sleep(1000);
//
//				SessionStorage localStorage = new SessionStorage(driver);
//				String sef = localStorage.getItemFromLocalStorage("token");
//				System.out.println(sef);
//				sef = "Bearer " + sef;
//
//				// 首页
//				String fristContent = doGet("https://api.dsxzt.com/admin/access/v1/statistics/index?pageIndex=0&pageSize=10000&regChannel=ALL&startDate="+date+"&endDate=",
//						sef);
//				analyzeDataService.analyzeTFirstInfoPoList(fristContent);
//				// 会员列表
//				String mebersContent = doGet("https://api.dsxzt.com/admin/access/v1/statistics/userlist?pageIndex=1&pageSize=100&startDate="+date+"&endDate=&phone=&regChannel=ALL",
//						sef);
//				analyzeDataService.analyzeTRegistUserInfoPoList(mebersContent);
//				
//				
//				// 历史数据
//				String historyContent = doGet("https://api.dsxzt.com/admin/access/v1/statistics/history?pageIndex=1&pageSize=10000&startDate="+date+"&endDate=",
//						sef);
//				analyzeDataService.analyzeHistoryInfoPoList(historyContent);
//
//				driver.quit();
//			} catch (Throwable e11) {
//				e11.printStackTrace();
//				driver.quit();
//			}
//		}
//	}
//
//	public static String doGet(String loginUrl, String token) {
//		try {
//
//			CloseableHttpClient httpclient1 = HttpClients.createDefault();
//
//			// 创建httpget.
//			HttpGet httpget = new HttpGet(loginUrl);
//			System.out.println("executing request " + httpget.getURI());
//			httpget.setHeader("authorization", token);
//			// 执行get请求.
//			CloseableHttpResponse response = httpclient1.execute(httpget);
//
//			// 获取响应实体
//			HttpEntity entity = response.getEntity();
//			System.out.println("--------------------------------------");
//			// 打印响应状态
//			System.out.println(response.getStatusLine());
//			if (entity != null) {
//				// 打印响应内容长度
//				System.out.println("Response content length: " + entity.getContentLength());
//				String content = EntityUtils.toString(entity);
//				// 打印响应内容
//				System.out.println("Response content: " + content);
//				return content;
//			}
//			System.out.println("------------------------------------");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//}
