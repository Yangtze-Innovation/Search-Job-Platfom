package com.couragehe.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.mysql.jdbc.Driver;

import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.thread.CountableThreadPool;
/**
 * 驱动连接池化，加速抓取速度
 * @author CourageHe
 *
 */
public class ConnPoolUtils extends HttpClientDownloader{
	private static HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
	static {
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
	    httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
	    	new Proxy("59.38.63.207",9797)
		    ,
		    new Proxy("183.129.207.86",11206)
		    ));
	}
	
		//MyConn连接池
	private List<MyConn> freeConns = new ArrayList<MyConn>();
	private List<MyConn> busyConns = new ArrayList<MyConn>();
	
	private int max=6;
	private int min=2;
	private int current=0;
	
	private static ConnPoolUtils  instance;
	//初始化为开始最少两个连接
	private ConnPoolUtils () {
		while(this.min>this.current) {
			this.freeConns.add(this.createConn());
		}
	}
	//加上同步锁
	private synchronized MyConn createConn() {
		WebDriver driver = MyConn.getWebDriver();
		MyConn conn = new MyConn(driver);
		this.current++;
		return conn;
	}
	public static ConnPoolUtils  getInstance() {
		if(instance == null) 
			instance= new ConnPoolUtils ();
		return instance;
	}
	public MyConn getMyConn() {
		MyConn MyConn = this.getFreeMyConn();
		if(MyConn !=null) {
			return MyConn; 
		}else {
			return this.getNewMyConn();
		}
	}
	public void setFree(MyConn conn) {
		this.busyConns.remove(conn);
		conn.setState(MyConn.FREE);
		this.freeConns.add(conn);
	}
	//关闭所有连接
	public void closeAllDriver() {
		for(MyConn conn : freeConns) {
			conn.close();
			current--;
		}
		for(MyConn conn : busyConns) {
			conn.close();
			this.current--;
		}
		
	}
	//请求到了错误的页面就关闭浏览器
	public synchronized  void closeErrorDriver(MyConn conn) {
		conn.getDriver().quit();
		this.busyConns.remove(conn);
		current--;
	}
	
	private  MyConn getFreeMyConn() {
		if(freeConns.size()>0) {
			MyConn MyConn = freeConns.remove(0);
			MyConn.setState(MyConn.BUSY);
			this.busyConns.add(MyConn);
			return MyConn;
		}else {
			return null;
		}
	}
	private  MyConn getNewMyConn() {
		if(this.current<this.max) {
			MyConn MyConn = this.createConn();
			MyConn.setState(MyConn.BUSY);
			this.busyConns.add(MyConn);
			return MyConn;
			}else {
				//若暂无空闲且新建数已达上限
				//则该线程休眠3秒继续请求连接
				try {
					Thread.sleep(3000);
					return this.getMyConn();					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}
		
	}


	public String toString() {
		return "当前连接数："+this.current+"\t空闲连接数:"+this.freeConns.size()+"\t繁忙连接数:"+this.busyConns.size();
	}
	
	public static HttpClientDownloader getHttpClientDownloader() {
		return httpClientDownloader;
	}
	public void setHttpClientDownloader(HttpClientDownloader httpClientDownloader) {
//		this.httpClientDownloader = httpClientDownloader;
	}
}
