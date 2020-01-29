package com.mytest.web.controller.touzi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mytest.utils.SessionStorage;

public class TZReptileMain {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
//		DesiredCapabilities caps = setDownloadsPath();// 更改默认下载路径
//		WebDriver driver = new ChromeDriver(caps);
		WebDriver driver = new ChromeDriver();
		driver.get("http://h5.dsx.wanxinkj.com/dev/manageh5/index.html");
		WebElement loginName = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[1]/div[2]/div/input"));
		loginName.sendKeys("taojin");
		WebElement loginPwd = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div[2]/div[2]/div/input"));
		loginPwd.sendKeys("qwer.123456");
		try {
			WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div/button"));
			loginBtn.click();
			Thread.sleep(2000);

			SessionStorage localStorage = new SessionStorage(driver);
			String sef = localStorage.getItemFromLocalStorage("token");
			System.out.println(sef);
			sef = "Bearer " + sef;

			// 首页
			doGet("https://api.dsxzt.com/admin/access/v1/statistics/index?pageIndex=0&pageSize=10000&regChannel=ALL&startDate=&endDate=",
					sef);
			// 会员列表
			doGet("https://api.dsxzt.com/admin/access/v1/statistics/userlist?pageIndex=1&pageSize=10000&startDate=&endDate=&phone=&regChannel=ALL",
					sef);

			// 历史数据
			doGet("https://api.dsxzt.com/admin/access/v1/statistics/history?pageIndex=1&pageSize=10000&startDate=&endDate=",
					sef);

			driver.quit();
		} catch (Throwable e11) {
			e11.printStackTrace();
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
				// 打印响应内容
				System.out.println("Response content: " + EntityUtils.toString(entity));
				return EntityUtils.toString(entity);
			}
			System.out.println("------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	// 单独重构成一个方法，然后调用
//	public static DesiredCapabilities setDownloadsPath() {
//		String downloadsPath = "D:\\dataSource\\outputReport\\Downloads";
//		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//		chromePrefs.put("download.default_directory", downloadsPath);
//		ChromeOptions options = new ChromeOptions();
//		options.setExperimentalOption("prefs", chromePrefs);
//		DesiredCapabilities caps = new DesiredCapabilities();
//		caps.setCapability(ChromeOptions.CAPABILITY, options);
//		return caps;
//	}

}
