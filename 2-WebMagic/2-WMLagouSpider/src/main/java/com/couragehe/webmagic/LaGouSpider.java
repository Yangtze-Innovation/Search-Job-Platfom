package com.couragehe.webmagic;

//import LaGou.LaGouPipe;
//import com.jfinal.core.Controller;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.HttpConstant;
import java.util.*;


public class LaGouSpider extends Controller implements PageProcessor  {
    int flag = 0;
    int mark = 0;
    int sun = 0;
    int sub = 0;
    int ty = 0;
    int tr = 0;
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("Accept","application/json, text/javascript, */*; q=0.01")
            .addHeader("Accept-Encoding","gzip, deflate, br")
            .addHeader("Accept-Language","zh-CN,zh;q=0.8")
            .addHeader("Connection","keep-alive")
            //.addHeader("Content-Length","23")
            .addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Cookie","user_trace_token=20191018210812-69542a99-c4c1-4213-9935-86ebc3517d9b; _ga=GA1.2.581480575.1571404096; LGUID=20191018210814-57d3cddd-f1a8-11e9-a5fb-5254005c3644; index_location_city=%E5%85%A8%E5%9B%BD; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216dee7b2996220-054c10aeb32ba2-b363e65-1327104-16dee7b299a395%22%2C%22%24device_id%22%3A%2216dee7b2996220-054c10aeb32ba2-b363e65-1327104-16dee7b299a395%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%7D%7D; JSESSIONID=ABAAABAAAIAACBIBC3052AC3F8A0506C2756A3F279D143C; WEBTJ-ID=20191025112052-16e00ee5e41b-00f011078bbca-b363e65-1327104-16e00ee5e422cf; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1571930798,1571962166,1571969759,1571973653; LGSID=20191025112052-72dc4755-f6d6-11e9-a072-525400f775ce; PRE_UTM=; PRE_HOST=; PRE_SITE=; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2Fjobs%2Flist_%25E7%2582%2592%25E9%25A5%25AD%3FlabelWords%3D%26fromSearch%3Dtrue%26suginput%3D; TG-TRACK-CODE=index_navigation; SEARCH_ID=8dab100a5c3749b2876c58851336de5d; X_HTTP_TOKEN=e9d06f90977f552d781479175117e6d34776edec79; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1571974188; LGRID=20191025112947-b1d9bb6a-f6d7-11e9-a074-525400f775ce"+ "")
            .addHeader("Host","www.lagou.com")
            .addHeader("Origin","https://www.lagou.com")
            .addHeader("Referer","https://www.lagou.com/jobs/list_Java")
            .addHeader("User-Agent","-Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3095.5 Mobile Safari/537.36")
            .addHeader("X-Anit-Forge-Code","0")
            .addHeader("X-Anit-Forge-Token","None")
            .addHeader("X-Requested-With","XMLHttpRequest");

    public void process(Page page)
    {	
    	System.out.println(page.getJson());
        this.processBeiJing(page);
//        this.processTianJin(page);
//        this.processChengDu(page);
//        this.processDaLian(page);
//        this.processShenYang(page);
//        this.processXiAn(page);
        page.putField("positionname",new JsonPathSelector("$.content.positionResult.result[*].positionName").selectList(page.getRawText()));
        page.putField("workYear",new JsonPathSelector("$.content.positionResult.result[*].workYear").selectList(page.getRawText()));
        page.putField("salary",new JsonPathSelector("$.content.positionResult.result[*].salary").selectList(page.getRawText()));
        page.putField("address",new JsonPathSelector("$.content.positionResult.result[*].city").selectList(page.getRawText()));
        page.putField("district",new JsonPathSelector("$.content.positionResult.result[*].district").selectList(page.getRawText()));
        page.putField("createTime",new JsonPathSelector("$.content.positionResult.result[*].createTime").selectList(page.getRawText()));
        page.putField("companyName",new JsonPathSelector("$.content.positionResult.result[*].companyFullName").selectList(page.getRawText()));
        page.putField("discription",new JsonPathSelector("$.content.positionResult.result[*].secondType").selectList(page.getRawText()));

    }
    public static void main(String []args)
    {
        Spider.create(new LaGouSpider())
//                .addPipeline(new LaGouPipe())
                .addUrl("https://www.lagou.com/jobs/positionAjax.json?px=default&city=北京&needAddtionalResult=false&isSchoolJob=0")
                .thread(2)
                .run();
//        renderText("爬取完成");
    }





    //爬取北京的java职位信息
    public void processBeiJing(Page page)
    {
        if(flag==0)
        {

            Request [] requests = new Request[30];
            Map<String,Object> map = new HashMap<String, Object>();
            for(int i=0;i<requests.length;i++)
            {
                requests[i] = new Request("https://www.lagou.com/jobs/positionAjax.json?px=default&city=北京&needAddtionalResult=false&isSchoolJob=0");
                requests[i].setMethod(HttpConstant.Method.POST);
                if(i==0)
                {
                    map.put("first","true");
                    map.put("pn",i+1);
                    map.put("kd","java");
                    requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                    page.addTargetRequest(requests[i]);
                }
                else
                {
                    map.put("first","false");
                    map.put("pn",i+1);
                    map.put("kd","java");
                    requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                    page.addTargetRequest(requests[i]);
                }
            }

            flag++;
        }
    }
    //爬取天津的java职位信息
    public void processTianJin(Page page)
    {
        if(mark==0)
        {
            Request [] requests = new Request[9];
            Map<String,Object> map = new HashMap<String, Object>();
            for(int i =0 ;i<requests.length;i++)
            {
                requests[i] = new Request("https://www.lagou.com/jobs/positionAjax.json?px=default&city=天津&needAddtionalResult=false&isSchoolJob=0");
                if(mark==0)
                {
                    map.put("first",true);
                    map.put("pn",i+1);
                    map.put("kd","java");
                    requests[i].setMethod(HttpConstant.Method.POST);
                    requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                    page.addTargetRequest(requests[i]);

                }
                else
                {
                    map.put("first",false);
                    map.put("kd","java");
                    map.put("pn",i+1);
                    requests[i].setMethod(HttpConstant.Method.POST);
                    requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                    page.addTargetRequest(requests[i]);
                }
            }
            mark++;
        }
    }
    public void processChengDu(Page page)
    {
        if(sun==0)
        {

            Request [] requests = new Request[1];
            Map<String,Object> map = new HashMap<String, Object>();
            for(int i=0;i<requests.length;i++)
            {
                requests[i] = new Request("https://www.lagou.com/jobs/positionAjax.json?px=default&city=成都&needAddtionalResult=false&isSchoolJob=0");
                requests[i].setMethod(HttpConstant.Method.POST);
                if(i==0)
                {
                    map.put("first","true");
                    map.put("pn",i+1);
                    map.put("kd","java");
                    requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                    page.addTargetRequest(requests[i]);
                }
                else
                {
                    map.put("first","false");
                    map.put("pn",i+1);
                    map.put("kd","java");
                    requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                    page.addTargetRequest(requests[i]);
                }
            }

            sun++;
        }

    }
    public void processXiAn(Page page)
    { if(sub==0)
    {

        Request [] requests = new Request[1];
        Map<String,Object> map = new HashMap<String, Object>();
        for(int i=0;i<requests.length;i++)
        {
            requests[i] = new Request("https://www.lagou.com/jobs/positionAjax.json?px=default&city=西安&needAddtionalResult=false&isSchoolJob=0");
            requests[i].setMethod(HttpConstant.Method.POST);
            if(i==0)
            {
                map.put("first","true");
                map.put("pn",i+1);
                map.put("kd","java");
                requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                page.addTargetRequest(requests[i]);
            }
            else
            {
                map.put("first","false");
                map.put("pn",i+1);
                map.put("kd","java");
                requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                page.addTargetRequest(requests[i]);
            }
        }

        sub++;
    }

    }
    public void processDaLian(Page page)
    { if(tr==0)
    {

        Request [] requests = new Request[1];
        Map<String,Object> map = new HashMap<String, Object>();
        for(int i=0;i<requests.length;i++)
        {
            requests[i] = new Request("https://www.lagou.com/jobs/positionAjax.json?px=default&city=大连&needAddtionalResult=false&isSchoolJob=0");
            requests[i].setMethod(HttpConstant.Method.POST);
            if(i==0)
            {
                map.put("first","true");
                map.put("pn",i+1);
                map.put("kd","java");
                requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                page.addTargetRequest(requests[i]);
            }
            else
            {
                map.put("first","false");
                map.put("pn",i+1);
                map.put("kd","java");
                requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                page.addTargetRequest(requests[i]);
            }
        }

        tr++;
    }

    }
    public void processShenYang(Page page)
    { if(ty==0)
    {

        Request [] requests = new Request[1];
        Map<String,Object> map = new HashMap<String, Object>();
        for(int i=0;i<requests.length;i++)
        {
            requests[i] = new Request("https://www.lagou.com/jobs/positionAjax.json?px=default&city=沈阳&needAddtionalResult=false&isSchoolJob=0");
            requests[i].setMethod(HttpConstant.Method.POST);
            if(i==0)
            {
                map.put("first","true");
                map.put("pn",i+1);
                map.put("kd","java");
                requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                page.addTargetRequest(requests[i]);
            }
            else
            {
                map.put("first","false");
                map.put("pn",i+1);
                map.put("kd","java");
                requests[i].setRequestBody(HttpRequestBody.form(map,"utf-8"));
                page.addTargetRequest(requests[i]);
            }
        }

        ty++;
    }

    }


    public Site getSite()
    {
        return site;
    }
  
}
