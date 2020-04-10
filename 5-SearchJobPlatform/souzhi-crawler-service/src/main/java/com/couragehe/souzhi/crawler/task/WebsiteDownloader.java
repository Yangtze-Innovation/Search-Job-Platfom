package com.couragehe.souzhi.crawler.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.couragehe.souzhi.crawler.task.website.LagouSpider;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientGenerator;
import us.codecraft.webmagic.downloader.HttpClientRequestContext;
import us.codecraft.webmagic.downloader.HttpUriRequestConverter;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.CharsetUtils;
import us.codecraft.webmagic.utils.HttpClientUtils;
/**
 * 页面下载
 */
public class WebsiteDownloader implements Downloader {
	//日志输出
    private Logger logger = LoggerFactory.getLogger(getClass());
    //用来保存根据站点域名生成的httpClient实例，以便重用
    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();
    //辅助类。用来生成httpclient实例
    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();
    
    private ProxyProvider proxyProvider;

    private boolean responseHeader = true;
    //拉勾爬取
    private static CookieStore lagouCookieStore = new BasicCookieStore();
    private static CloseableHttpClient lagouHttpClient = HttpClients.custom().setDefaultCookieStore(lagouCookieStore).build();

    public void setHttpUriRequestConverter(HttpUriRequestConverter httpUriRequestConverter) {
        this.httpUriRequestConverter = httpUriRequestConverter;
    }

    public void setProxyProvider(ProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }
    //获取httpclient实例
    private CloseableHttpClient getHttpClient(Site site) {
        if (site == null) {
            return httpClientGenerator.getClient(null);
        }
        //通过site获取域名
        String domain = site.getDomain();
        //通过域名判断是否在httpClients这个HttpClient实例，
        //如果存在则重用，否则通过httpClientGenerator重建一个新的实例，然后加入到httpClients这个map中
        CloseableHttpClient httpClient = httpClients.get(domain);
    	if (httpClient == null) {
        	//确保线程安全，使用了线程安全的双重判断机制（两个httpClient）
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }

   
    //这里的Task参数其实就是Spider实例
    public Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        CloseableHttpResponse httpResponse = null;
        //通过site来设置字符集、请求头，以及允许接收的相应状态码
        CloseableHttpClient httpClient = getHttpClient(task.getSite());
        //设置代理，首先判断site是否有设置代理池
        Proxy proxy = proxyProvider != null ? proxyProvider.getProxy(task) : null;
        //获取HttpUriRequest，（httpget、httpPost）的接口，执行请求，并将响应转换成page对象返回
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request, task.getSite(), proxy);
        Page page = Page.fail();
        try {
        	//若爬取网站为lagou网api则重新定义httpclient
            if(request.getUrl().indexOf("positionAjax") != -1){
            	httpResponse = LagouSpider.lagouDownloader(request.getUrl());
            }else {
            	httpResponse = httpClient.execute(requestContext.getHttpUriRequest(), requestContext.getHttpClientContext());
           	
            }
            page = handleResponse(request, request.getCharset() != null ? request.getCharset() : task.getSite().getCharset(), httpResponse, task);
            onSuccess(request);
            logger.info("downloading page success {}", request.getUrl());
            return page;
        } catch (IOException e) {
            logger.warn("download page {} error", request.getUrl(), e);
            onError(request);
            return page;
        } finally {
        	//资源回收处理，回收代理入池，回收httpClient的Connection等
            if (httpResponse != null) {            	
                //ensure the connection is released back to pool
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
            if (proxyProvider != null && proxy != null) {
                proxyProvider.returnProxy(proxy, page, task);
            }
        }
    }
    
    
	
    public void setThread(int thread) {
        httpClientGenerator.setPoolSize(thread);
    }
    //将下载的内容装欢为page对象
    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws IOException {
        
    	byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        String contentType = httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity().getContentType().getValue();
        Page page = new Page();
        page.setBytes(bytes);
        if (!request.isBinaryContent()){
            if (charset == null) {
            	//检测字符集，若无法检查则选择默认字符集
                charset = getHtmlCharset(contentType, bytes);
            }
            page.setCharset(charset);
            page.setRawText(new String(bytes, charset));
        }
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setDownloadSuccess(true);
        if (responseHeader) {
            page.setHeaders(HttpClientUtils.convertHeaders(httpResponse.getAllHeaders()));
        }
        return page;
    }

    /**
     * 首先判断httpResponse.getEntity().getContentType().getValue()是否含有
     * 比如charset=utf-8；否则用Jsoup解析内容，判断是提取meta标签，然后判断针对html4或html5中
     * <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />分情况判断出字符编码。
     * 如果服务端返回的不是完整的html内容(不包含head的)，甚至不是html内容(比如json)，
     * 那么就会导致判断失败，返回默认jvm编码值.
     * 最好自己手动给site设置字符编码
     * @param contentType
     * @param contentBytes
     * @return
     * @throws IOException
     */
    private String getHtmlCharset(String contentType, byte[] contentBytes) throws IOException {
        String charset = CharsetUtils.detectCharset(contentType, contentBytes);
        if (charset == null) {
        	charset = Charset.defaultCharset().name();
            logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
        }
        return charset;
    }

    /**
     * 这两个方法都是空实现（主要原因是在Spider中已经通过调用Listener(监听器)来处理状态）
     * 网页下载成功回调  
     */
    protected void onSuccess(Request request) {
    }

    /**
     * 网页下载失败回调
     */
    protected void onError(Request request) {
    }


}
