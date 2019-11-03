## 拉勾网爬取(HttpClient)

#### 一、概述

&emsp;&emsp;Java许多框架都是以httpclient作为基础的，比如Webmagic。因此学会httpClient的爬取是至关重要的。业界成熟的爬虫框架会对httpClient做一个基本的封装，使爬虫更益于上手，避免了繁杂重复的工作。但是大多数网站的反爬措施多种多样，框架是难以考虑进去的，因此重写框架的下载部分是很常见的。

#### 二、分析拉勾网页

&emsp;&emsp;这是一个最近在钻研的项目，爬取拉勾网的招聘信息。拉勾网是一个主打互联网招聘的招聘平台，在招聘网站种热度排名第七，可想而知网站的反扒措施也是做得相当厉害的！！！

##### 1、拉勾坑的总结

&emsp;&emsp;拉勾的坑可不是一般的大！！！不信大家可以尝试一下，无论是直接get搜索链接还是在浏览器复制Cookie进行爬取访问，都只能显示四五条结果后便认定为非法访问即无法正常显示页面。

##### 2、分析网页搜索框

<img src="..\Resource\images\1.1拉勾搜索主页.png" alt="拉勾搜索主页" style="zoom:67%;" />

&emsp;&emsp;该网页为搜索页面，输入关键字会重组请求请求地址`https://www.lagou.com/jobs/list_java?labelWords=&fromSearch=true&suginput=`,比如**java**就是该链接的组合，该网页下有十五条职业信息，总共页数视总数而定。

<img src="..\..\SearchJobPlatform\Resource\images\1.2请求职位api.png" style="zoom:67%;" />

&emsp;&emsp;该图为请求职位的api链接，返回json数据包含上面十五条职业的信息。该请求为post请求，body携带的参数**kd：**关键字，**pn：**页数，**first：**true;

##### 3、千万遍尝试

&emsp;&emsp;知道上述的请求解析并不能成功得到信息，经历过多次尝试爬取。最终得此良方，图二请求携带的**Cookie**为访问搜索页面中服务器设置的Cookie值，如果没有则无法得到职位信息。如果仅用访问搜索页面第一次得到的Cookie值，后面api访问四五次之后就不能使用了，最适合的是每访问一次搜搜页面，然后请求api数据，然后清除Cookie继而再次循环！！！

#### 三、代码实例

```java
protected String urlHome="https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=";
protected String urlAjax="https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false&page=2";
protected CookieStore cookieStore = new BasicCookieStore();	
CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
	for(int i=1;i<200;i++) {
//			CloseableHttpClient httpClient = HttpClients.createDefault();
			
		getCookie(httpClient);
		int page = i;
		String keyword = "JAVA";
		HttpPost httpPost = new HttpPost(urlAjax);
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		nameValuePairList.add(new BasicNameValuePair("first","true"));
		nameValuePairList.add(new BasicNameValuePair("pn",page+""));
		nameValuePairList.add(new BasicNameValuePair("kd",keyword));
		//post添加请求body
		HttpEntity entity = new  UrlEncodedFormEntity(nameValuePairList, "utf-8");
		httpPost.setEntity(entity);
			//添加请求头
		httpPost.addHeader("Host", "www.lagou.com");
		httpPost.addHeader("Accept","'application/json, text/javascript, */*; q=0.01");
		httpPost.addHeader("Referer","'https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
		httpPost.addHeader("User-Agent", "Mozilla/5.0(X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		CloseableHttpResponse response  = httpClient.execute(httpPost);
		//输出得到的结果
		System.out.println(EntityUtils.toString(response.getEntity()));
		//关闭HttpEntity输出流
		EntityUtils.consume(response.getEntity());
		System.out.println(page);
	}
}
//获取搜索页的Cookie
public void getCookie(CloseableHttpClient httpClient) {
	HttpGet httpGet = new HttpGet(urlHome);
	//清除Cookie起到复用的效果
	cookieStore.clear();
	httpGet.addHeader("Host", "www.lagou.com");
	httpGet.addHeader("Accept","'application/json, text/javascript, */*; q=0.01");
	httpGet.addHeader("Referer","'https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
	httpGet.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
	try {
		CloseableHttpResponse response = httpClient.execute(httpGet);
		System.out.println(EntityUtils.toString(response.getEntity()));;
	} catch (Exception e) {
		e.printStackTrace();
	}
	 List<Cookie> cookies = cookieStore.getCookies();
	for(Cookie cookie : cookies) {
		System.out.println(cookie.getName()+":"+cookie.getValue());			
	}
}
```



