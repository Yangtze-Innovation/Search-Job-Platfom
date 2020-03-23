package com.couragehe.spider;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.UrlUtils;
import us.codecraft.webmagic.utils.WMCollections;

public class JobCollectDownloader implements Downloader, Closeable  {
	private static Logger logger = Logger.getLogger(JobCollectDownloader.class);
	CloseableHttpClient httpClient;
	protected static Map<String, String>cookieMap = new HashMap<String, String>();
	protected static String Referer = null;
	/**
	 * 开始下载（核心）
	 */
	public Page download(Request request, Task task) {
		Site site = null;
		if(task != null) {
			site= task.getSite();
		}
		Set<Integer>acceptStatCode;
		String charset = null;
		Map<String, String>headers = null;
		if(site != null) {
			acceptStatCode = site.getAcceptStatCode();
			charset = site.getCharset();
			headers = site.getHeaders();
		}else {
			acceptStatCode  = WMCollections.newHashSet(200);
		}
		int statusCode = 0;
		logger.info("downloading page"+request.getUrl());
		httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(request.getUrl());
		this.addHeader(httpGet);
		//请求头添加Cookie		this.addHeader(httpGet);
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			//Cookie收集与更新
			statusCode = response.getStatusLine().getStatusCode();			
			//无论下载成功与否 皆处理页面
			Page page = handleResponse(request, charset, response, task);
			handleCookie(response);
			Referer = request.getUrl();
			onSuccess(request);
			response.close();
			httpClient.close();
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public void handleCookie(CloseableHttpResponse response) throws ClientProtocolException, IOException {
			
			Header[]headers = response.getHeaders("Set-Cookie");
			if(headers != null) {
				for(Header header : headers) {
					String value = header.getValue();
					String name = value.substring(0,value.indexOf("="));
					cookieMap.put(name, value);
				}
			}	
	}
		
	public void addHeader(HttpGet httpGet ) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> entry : cookieMap.entrySet()) {
			  builder.append(entry.getValue());
		}
		String cookie = builder.toString();
		httpGet.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
		httpGet.addHeader("Accept-Language","zh-CN,zh;q=0.9");
		httpGet.addHeader("Connection", "keep-alive");
		httpGet.addHeader("Upgrade-Insecure-Requests","1");
		httpGet.addHeader("Host", "www.lagou.com");
		httpGet.addHeader("Cookie","user_trace_token=20191028013839-e998e208-5f87-439e-b9bd-9731609762e6"
        		+ "; _ga=GA1.2.1849630556.1572197924"
        		+ "; LGUID=20191028013841-9e0ac787-f8e0-11e9-a126-525400f775ce"
        		+ "; _qddaz=QD.9zj584.btoopl.k29oq6k6"
        		+ "; index_location_city=%E5%85%A8%E5%9B%BD"
        		+ "; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216e1118fd124c0-0177553e6e442a-7711439-1327104-16e1118fd1349d%22%2C%22%24device_id%22%3A%2216e1118fd124c0-0177553e6e442a-7711439-1327104-16e1118fd1349d%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%7D%7D"
        		+ "; _gid=GA1.2.74910697.1572418200"
        		+ "; JSESSIONID=ABAAABAAAGFABEF07D0170EB574D7751E2977EB457BD56F"
        		+ "; WEBTJ-ID=20191030160831-16e1bb585c910e-0115d107fdbc28-7711439-1327104-16e1bb585ca13f; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1572405212,1572411566,1572418201,1572422912"
        		+ "; _gat=1"
        		+ "; LGSID=20191030165656-39af1f75-faf3-11e9-a60f-5254005c3644"
        		+ "; PRE_UTM=; PRE_HOST=; PRE_SITE="
        		+ "; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2Fzhaopin%2FJava%2F"
        		+ "; X_MIDDLE_TOKEN=863ed5315ea0bf7bcead9b19d0f4cd55"
        		+ "; SEARCH_ID=2aee4e9043384220a444c4016d8b7895"
        		+ "; X_HTTP_TOKEN=11a8b2341f9f609d0395242751ac0ecc3c7d953bc6"
        		+ "; LGRID=20191030165850-7ddbc85e-faf3-11e9-a1c0-525400f775ce"
        		+ "; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1572425932");
//		if(cookie != null)httpGet.addHeader("Cookie",cookie);
		if(Referer !=null)httpGet.addHeader("Referer", Referer);
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		
	}
    /**
              * 网页下载成功回调
     * 
     * @param request
     */
    protected void onSuccess(Request request) {
    
    }
    
    
	public void setThread(int threadNum) {

	}

	public void close() throws IOException {
		httpClient.close();
	}
	  /**
     * 处理请求结果 并封装成 page
     * 
     * @param request
     * @param charset
     * @param httpResponse
     * @param task
     * @return
     * @throws IOException
     */
    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task)
            throws IOException {
        String content = getContent(charset, httpResponse);
        Page page = new Page();
        page.setRawText(content);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        return page;
    }

    /**
     * 根据编码处理网页
     * 
     * @param charset
     * @param httpResponse
     * @return
     * @throws IOException
     */
    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
        if (charset == null) {
            byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            String htmlCharset = getHtmlCharset(httpResponse, contentBytes);
            if (htmlCharset != null) {
                return new String(contentBytes, htmlCharset);
            } else {
                logger.warn("Charset autodetect failed, use " + Charset.defaultCharset()
                        + " as charset. Please specify charset in Site.setCharset()");
                return new String(contentBytes);
            }
        } else {
            return IOUtils.toString(httpResponse.getEntity().getContent(), charset);
        }
    }

    /**
     * 从网页中获取网页编码 并对网页进行编码
     * 
     * @param httpResponse
     * @param contentBytes
     * @return
     * @throws IOException
     */
    protected String getHtmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        String charset;
        // charset
        String value = httpResponse.getEntity().getContentType().getValue();
        charset = UrlUtils.getCharset(value);
        if (StringUtils.isNotBlank(charset)) {
            logger.debug("Auto get charset:" + charset);
            return charset;
        }
        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset.name());
        if (StringUtils.isNotEmpty(content)) {
            Document document = Jsoup.parse(content);
            Elements links = document.select("meta");
            for (Element link : links) {
                String metaContent = link.attr("content");
                String metaCharset = link.attr("charset");
                if (metaContent.indexOf("charset") != -1) {
                    metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
                    charset = metaContent.split("=")[1];
                    break;
                }
                // 2.2、html5 <meta charset="UTF-8" />
                else if (StringUtils.isNotEmpty(metaCharset)) {
                    charset = metaCharset;
                    break;
                }
            }
        }
        logger.debug("Auto get charset: " + charset);
        return charset;
    }
//https://my.oschina.net/whitejavadog/blog/2885492
}
