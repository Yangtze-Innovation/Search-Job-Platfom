package com.couragehe.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

class Login {
    public static String loginurl = "https://security.kaixin001.com/login/login_post.php";
    static Cookie[] cookies = {};

    static HttpClient httpClient = new HttpClient();
    
    static String email = "524235428@qq.com";//你的email
    static String psw = "123456";//你的密码
    // 消息发送的action

    public static void getUrlContent()
            throws Exception {

        HttpClientParams httparams = new HttpClientParams();
        httparams.setSoTimeout(30000);
        httpClient.setParams(httparams);

        httpClient.getHostConfiguration().setHost("www.kaixin001.com", 80);

        httpClient.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        PostMethod login = new PostMethod(loginurl);
        login.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");

	        NameValuePair Email = new NameValuePair("loginemail", email);// 邮箱
	        NameValuePair password = new NameValuePair("password", psw);// 密码
        // NameValuePair code = new NameValuePair( "code"
        // ,"????");//有时候需要验证码，暂时未解决

        NameValuePair[] data = { Email, password };
        login.setRequestBody(data);

        httpClient.executeMethod(login);
        int statuscode = login.getStatusCode();
        System.out.println(statuscode + "-----------");
        String result = login.getResponseBodyAsString();
        System.out.println(result+"++++++++++++");

        cookies = httpClient.getState().getCookies();
        System.out.println("==========Cookies============");
        int i = 0;
        for (Cookie c : cookies) {
            System.out.println(++i + ":   " + c);
        }
        httpClient.getState().addCookies(cookies);

        // 当state为301或者302说明登陆页面跳转了，登陆成功了
        if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY)
                || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
                || (statuscode == HttpStatus.SC_SEE_OTHER)
                || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
            // 读取新的 URL 地址
            Header header = login.getResponseHeader("location");
            // 释放连接
            login.releaseConnection();
            System.out.println("获取到跳转header>>>" + header);
            if (header != null) {
                String newuri = header.getValue();
                if ((newuri == null) || (newuri.equals("")))
                    newuri = "/";
                GetMethod redirect = new GetMethod(newuri);
                // ////////////
                redirect.setRequestHeader("Cookie", cookies.toString());
                httpClient.executeMethod(redirect);
                System.out.println("Redirect:"
                        + redirect.getStatusLine().toString());
                redirect.releaseConnection();

            } else
                System.out.println("Invalid redirect");
        } else {
            // 用户名和密码没有被提交，当登陆多次后需要验证码的时候会出现这种未提交情况
            System.out.println("用户没登陆");
            System.exit(1);
        }

    }

    public static void sendMsg() throws Exception {
        // 登录后发消息
        System.out.println("*************发消息***********");

        String posturl = "http://www.kaixin001.com/msg/post.php";
        PostMethod poster = new PostMethod(posturl);

        poster.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");
        poster.setRequestHeader("Cookie", cookies.toString());

        NameValuePair uids = new NameValuePair("uids", "89600585");// 发送的好友对象的id,此处换成你的好友id
        NameValuePair content = new NameValuePair("content", "你好啊!");// 需要发送的信息的内容
        NameValuePair liteeditor_0 = new NameValuePair("liteeditor_0", "你好啊!");// 需要发送的信息的内容
        NameValuePair texttype = new NameValuePair("texttype", "plain");
        NameValuePair send_separate = new NameValuePair("send_separate", "0");
        NameValuePair service = new NameValuePair("service", "0");
        NameValuePair[] msg = { uids, content, texttype, send_separate, service,liteeditor_0 };

        poster.setRequestBody(msg);
        httpClient.executeMethod(poster);

        String result = poster.getResponseBodyAsString();
        System.out.println(result+"++++++++++++");
        //System.out.println(StreamOut(result, "iso8859-1"));
        int statuscode = poster.getStatusCode();
        System.out.println(statuscode + "-----------");
        if(statuscode == 301 || statuscode == 302){
            // 读取新的 URL 地址
            Header header = poster.getResponseHeader("location");
            System.out.println("获取到跳转header>>>" + header);
            if (header != null) {
                String newuri = header.getValue();
                if ((newuri == null) || (newuri.equals("")))
                    newuri = "/";
                GetMethod redirect = new GetMethod(newuri);
                // ////////////
                redirect.setRequestHeader("Cookie", cookies.toString());
                httpClient.executeMethod(redirect);
                System.out.println("Redirect:"
                        + redirect.getStatusLine().toString());
                redirect.releaseConnection();

            } else
                System.out.println("Invalid redirect");
        }
        
            poster.releaseConnection();
    }

    public static String StreamOut(InputStream txtis, String code)
            throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(txtis,
                code));
        String tempbf;
        StringBuffer html = new StringBuffer(100);
        while ((tempbf = br.readLine()) != null) {
            html.append(tempbf + "\n");
        }
        return html.toString();

    }
}