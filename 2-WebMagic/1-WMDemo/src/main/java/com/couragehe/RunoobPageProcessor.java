package com.couragehe;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;


/**
 * 菜鸟教程markdown转换
 * Created by bekey on 2017/6/6.
 */
public class RunoobPageProcessor implements PageProcessor {
    private static String name = null;
    private static String regex = null;

    // 抓取网站的相关配置，包括编码、重试次数、抓取间隔、超时时间、请求消息头、UA信息
    private Site site= Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(3000).addHeader("Accept-Encoding", "/")
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");

    public Site getSite() {
        return site;
    }

    
    //此处为处理函数
    public void process(Page page) {
        Html html = page.getHtml();
//        String name = page.getUrl().toString().substring();
        if(name == null ||regex == null){
            String url = page.getRequest().getUrl();
            name = url.substring(url.lastIndexOf('/',url.lastIndexOf('/')-1)+1,url.lastIndexOf('/'));
            regex = "http://www.runoob.com/"+name+"/.*";
        }
        //添加访问
        page.addTargetRequests(html.links().regex(regex).all());
        //获取文章主内容
        Document doc = html.getDocument();
        Element article = doc.getElementById("content");
        //获取markdown文本
        String document = Service.markdown(article);
        //处理保存操作
        String fileName = article.getElementsByTag("h1").get(0).text().replace("/","").replace("\\","") + ".md";
        page.putField("fileName",fileName);
        page.putField("content",document);
        page.putField("dir",name);

    }
}
