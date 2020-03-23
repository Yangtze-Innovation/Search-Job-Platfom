package com.couragehe.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MyConn {
	private WebDriver driver;
	private int state;
	protected static final int BUSY =1;
	protected static final int FREE=0;
	public WebDriver getDriver() {
		return driver;
	}
	protected void setState(int state) {
		this.state = state;
	}
	public MyConn(WebDriver  newDriver) {
		this.driver = newDriver;
	}
	protected   void close() {
		driver.quit();
	}
	protected  static WebDriver getWebDriver () {
		ChromeOptions options = new ChromeOptions();
		List<String>list = new ArrayList<String>();
		list.add( "F:\\java\\SearchJobPlatform\\2-WebMagic\\chromedriver.exe");
		list.add("--headless");
		list.add("--disable-gpu");
		list.add("blink-settings=imagesEnabled=false");
		options.addArguments(list);

		WebDriver driver = new ChromeDriver(options);

//		ChromeOptions options = new ChromeOptions();
//		String ip = "183.129.207.86:11206";
//		options.addArguments("--proxy-server=http://" + ip);
		System.setProperty("webdriver.chrome.driver",  "F:\\java\\SearchJobPlatform\\2-WebMagic\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver(options);
//		WebDriver driver = new ChromeDriver();
		 //设置必要参数
//        DesiredCapabilities dcaps = new DesiredCapabilities();
//        //ssl证书支持
//        dcaps.setCapability("acceptSslCerts", true);
//        //截屏支持
//        dcaps.setCapability("takesScreenshot", false);
//        //css搜索支持
//        dcaps.setCapability("cssSelectorsEnabled", true);
//        //js支持
//        dcaps.setJavascriptEnabled(true);
//        //驱动支持
//        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"E:\\迅雷下载\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
////		System.setProperty("phantomjs.binary.path",  "E:\\迅雷下载\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
//		WebDriver driver = new PhantomJSDriver(dcaps);
//		driver.get(url);
//		Set<Cookie> cookies = driver.manage().getCookies();
//		System.out.println(driver.manage().getCookies());
//		System.out.println(driver.getTitle());
//		System.out.println(driver.getCurrentUrl());
//		driver.close();
		return driver;
	}
	
}
