## 拉勾网爬取(自动化)

WebMagic+Selenium+ChromeDriver实现浏览器自动化抓取。

&emsp;&emsp;网页反扒做到难以破解的情况下，采用selenium模拟浏览器自动爬取可谓是一个好方法，模拟用户浏览器的操作可以更简易的破解许多问题。如果网页是动态生成的话，用selenium也是正确的选择。

#### 一、Maven依赖配置

```xml
 <dependencies>
     <!--webmagic配置-->
	<dependency>
	    <groupId>us.codecraft</groupId>
	    <artifactId>webmagic-core</artifactId>
	    <version>0.7.3</version>
	</dependency>
	<dependency>
	    <groupId>us.codecraft</groupId>
	    <artifactId>webmagic-extension</artifactId>
	    <version>0.7.3</version>
	</dependency>
     <!--mysql连接jar包-->
		<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-Java</artifactId>
	    <version>5.1.3</version>
	</dependency>
     <!--数据库连接池jar包-->
	<dependency>
	    <groupId>commons-dbcp</groupId>
	    <artifactId>commons-dbcp</artifactId>
	    <version>1.4</version>
	</dependency>
	 <dependency>
	    <groupId>apache-httpclient</groupId>
	    <artifactId>commons-httpclient</artifactId>
	    <version>3.1</version>
	</dependency>
     <!--mysql连接jar包-->
	 <dependency>
      <groupId>us.codecraft</groupId>
      <artifactId>webmagic-selenium</artifactId>
      <version>0.7.3</version>
    </dependency>
     <!--java支持的selenium包-->
    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>3.3.1</version>
    </dependency>
     <!--chromedriver驱动jar包-->
    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-chrome-driver</artifactId>
      <version>3.5.1</version>
    </dependency>
 </dependencies>
```

#### 二、ChromeDriver配置

&emsp;&emsp;鉴于数据库连接池，建立连接用时比使用所需时间多得多，因而产生了连接池的概念。chromedriver也是采用了连接池的写法，用时则取，不用则搁置即可，无需二次连接。

本文不再此赘述连接池的写法，需要查看的可以访问**：[ChromeDriver版连接池---告别缓慢]()**

```java
//chromedriver存放路径
System.setProperty("webdriver.chrome.driver","F:\\chromedriver.exe");
	WebDriver driver = new ChromeDriver();
	driver.get(url);
	driver.close();
```

其实获取driver并使用不是很难，但是要找到与自己浏览器版本配置

相符的驱动有点麻烦。下面是淘宝镜像版的连接，每一个版本种都有相应的notes.txt文件说明所适应版本。

[淘宝镜像版谷歌驱动](http://npm.taobao.org/mirrors/chromedriver/)

#### 三、拉勾网页爬取配置

##### 1、Controller类-开启应用

```java
public static void main(String[] args) {
	String url = "https://www.lagou.com/";

	Spider.create(new JobCollectPageProcessor())
		.addUrl(url)
		//.setDownloader(ConnPoolUtils.getHttpClientDownloader())
		.addPipeline(new JobCollectSavePipeline() )
		.thread(3)
		.run();
		//执行完毕 关闭所有的浏览器对象
		ConnPoolUtils.getInstance().closeAllDriver();
	}
```

##### 2、JobCollectSavePipeline 类页面处理

```java
public void process(Page page) {
	//在连接池种取出空闲的连接对象
	MyConn conn = ConnPoolUtils.getInstance().getMyConn();
	WebDriver driver  = conn.getDriver();
	driver.get(page.getRequest().getUrl());
	Html html = Html.create(driver.getPageSource());
    //访问页面过多的话会自动重定向到登陆页
	if(Pattern.matches("https://passport.lagou.com/.*", driver.getCurrentUrl())){
    	//将该页面再次请求，并加上空格，避免webmagic的去重机制去除
        page.addTargetRequest(page.getRequest().getUrl()+" ");
        //关闭该浏览器对象
        ConnPoolUtils.getInstance().closeErrorDriver(conn);
        return ;
	}else {
        //正常得到所需html
		html = Html.create(driver.getPageSource());
		//将连接对象 释放至连接池
		ConnPoolUtils.getInstance().setFree(conn);
	}
    //查看连接池使用情况
	System.out.println(ConnPoolUtils.getInstance());
    //从首页进入 采集首页的招聘方向的连接
	if(page.getRequest().getUrl().equals("https://www.lagou.com/")) {
        	String regex = "https://www.lagou.com/zhaopin/.*";
			page.addTargetRequests(html.links().regex(regex).all());
	}
    //每个招聘收索页的其他页面的连接继续请求
	page.addTargetRequests(html.$("div.pager_container").links().all());
    //将得到的每个职业的连接单独用另一个spider对象处理，因为driver加载太慢了
	List<String>jobList = html.$("li.con_list_item div.p_top").links().all();
	page.putField("joblist", jobList);
}
```

##### 3、 JobCollectSavePipeline类 -结果收集

```java
public void process(ResultItems resultItems, Task task) {
    //获取页面处理得到的招聘详情连接
    List<String> list = resultItems.get("joblist");
	//开启一个新的Spider对象进行爬取
    Spider spider = Spider.create(new JobDetailPageProcessor())
	 		//开启线程抓取
	 		.thread(5);
	for (String url: list ) {
	   	spider.addUrl(url);
	}
	//启动爬虫
	spider.run();
}
```

##### 4、JobDetailPageProcessor类 -详情页处理

```java
	public void process(Page page) {
		Html html = page.getHtml();
		//达到第三层 详情页
		JobInfo job = new JobInfo();
		job.setId(UUIDUtils.getUUID64());
//		CSS选择器
		job.setJobname(html.$("h1.name","text").get());
		job.setSalary(html.$("span.ceil-salary","text").toString());
		
		//地址解析，去除所有标签
		String address = Jsoup.clean(html.$("div[class=work_addr]").toString(), Whitelist.none());
		job.setCompanyaddress(address.substring(0,address.indexOf("查看")));
		
		//JSoup解析器
		Document doc = Jsoup.parse(html.toString());
	
		job.setCity(doc.select("dd[class=job_request] h3 span").get(1).text().replaceAll("/",""));
		job.setExperience(doc.select("dd[class=job_request] h3 span").get(2).text().replaceAll("/",""));
		job.setEducation(doc.select("dd[class=job_request] h3 span").get(3).text().replaceAll("/",""));
		
		//xpath解析器
		job.setCompanylablelist(html.xpath("//dd[@class='job-advantage']/p/text()").toString());
		job.setCompanyname(html.xpath("//h3[@class=\"fl\"]/em/text()").toString());
		
		//时间进行截取
		String time = html.xpath("//p[@class=\"publish_time\"]/text()").toString();
		job.setCreatetime(time.substring(0,time.indexOf(" ")-1));
		job.setUrl(page.getRequest().getUrl());
		
		//职位详细信息进行截取
		String detail = html.xpath("div[@class=\"job-detail\"]").toString();
		job.setDetail(detail.substring(detail.indexOf("<p>1"),detail.lastIndexOf("<p>1")-16));
		job.setWebsite("拉勾");
		
		System.out.println(job);
		if(job.getJobname()!=null) {
			JobPositionDao.addJobData(job);
		}
	}
```

本篇文章所讲述的项目文件在：[GitHub地址](https://github.com/Yangtze-Innovation/Search-Job-Platfom/tree/CourageHe/2-WebMagic/4-WebMagicSelenimu)