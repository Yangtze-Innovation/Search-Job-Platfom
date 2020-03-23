package com.couragehe.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

/*
 * httpclient使用了单独的一个HttpMethod子类来处理文件的上传，
 * 这个类就是MultipartPostMethod，该类已经封装了文件上传的细节，
 * 我们要做的仅仅是告诉它我们要上传文件的全路径即可，下面这里将给出关于模拟上传方式的代码
 */
public class CommonsHttpClient2{
	/**
     * 上传文件到指定URL
     * @param file
     * @param url
     * @return
     * @throws IOException
     */
    public static String doUploadFile(File file, String url) throws IOException {
        String response = "";
        if (!file.exists()) {
            return "file not exists";
        }
        PostMethod postMethod = new PostMethod(url);
        try {
            //----------------------------------------------
            // FilePart：用来上传文件的类,file即要上传的文件
            FilePart fp = new FilePart("file", file);
            Part[] parts = { fp };

            // 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
            MultipartRequestEntity mre = new MultipartRequestEntity(parts,
                    postMethod.getParams());
            postMethod.setRequestEntity(mre);
            //---------------------------------------------
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams()
                    .setConnectionTimeout(50000);// 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
            int status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                response = stringBuffer.toString();
            } else {
                response = "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return response;
    }
}
