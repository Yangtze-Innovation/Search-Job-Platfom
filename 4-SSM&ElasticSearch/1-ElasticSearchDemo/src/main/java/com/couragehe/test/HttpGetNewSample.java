package com.couragehe.test;
import java.io.IOException;
import java.io.InputStream;
 
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
 
public class HttpGetNewSample {
 
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String url="https://m.lagou.com/search.json?city=%E5%85%A8%E5%9B%BD&positionName=java&pageNo=1&pageSize=15";
      
        //1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        //2.使用get方法
        HttpGet httpGet = new HttpGet(url);
        //3.设置请求头
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("cookie", "");
        httpGet.setHeader("Host", "m.lagou.com");
        httpGet.setHeader("Referer", "https://m.lagou.com/search.html");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
        
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
 
        try {
            //4.执行请求，获取响应
            response = client.execute(httpGet);
 
            //看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            //5.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
 
            //6.将其打印到控制台上面
            //方法一：使用EntityUtils
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity, "utf-8"));
            }
            //释放流
            EntityUtils.consume(entity);
            
            //方法二  :使用inputStream
           /* if (entity != null) {
                inputStream = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            }*/
 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
 
        }
 
    }
 
}