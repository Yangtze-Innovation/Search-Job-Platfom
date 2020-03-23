package com.couragehe.test;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.junit.Before;
import org.junit.Test;

import com.couragehe.entity.MailInfo;
import com.couragehe.utils.MailUtils;

public class MailTest {
    private Session session;

    @Before
    public void Init() {
        /**
         * 1、设置邮箱的一些属性，关于Properties类的介绍
         * 2、创建认证对象authenticator，使用自己的邮件账号和授权码
         * 3、获得一个session对象，用来保存认证对象
         * 4、创建邮件消息对象message
         * 	4.1、设置message的发送人，这个要和认证对象的账号一致
         *  4.2、设置message的接收人
         * 	4.3、设置邮件的主题和内容
         */

        //1 确定连接位置
        Properties props = System.getProperties();
        //1.1获取qq邮箱smtp服务器的地址，
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        //1.2是否进行权限验证。
        props.setProperty("mail.smtp.auth", "true");

        //3 建立邮件会话                             2、创建认证对象authenticat
        session = Session.getDefaultInstance(props, new Authenticator() {
            //填写自己的qq邮箱的登录帐号和授权密码，授权密码的获取，在后面会进行讲解。
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("524235428@qq.com", "onhrxudtxbkubibh"); //账户 授权码
            }
        });
    }

    @Test
    public void TestSend() throws Exception {

        //4、创建消息
        MimeMessage message = new MimeMessage(session);


        //4.1 附带发件人名字

        //设置自定义发件人昵称
        String nickFrom = MimeUtility.encodeText("职能搜索平台");
        String nickTo = MimeUtility.encodeText("酷酷的用户");
        //4.2 设置发信人
        message.setFrom(new InternetAddress(nickFrom + "<524235428@qq.com>"));
        message.setRecipients(Message.RecipientType.TO, nickTo + "<couragehe135@163.com>");
        message.setRecipients(Message.RecipientType.BCC, nickFrom + "<524235428@qq.com>");

        /**
         * 4.2 收件人 （补充）
         *         第一个参数：
         *             RecipientType.TO    代表收件人
         *             RecipientType.CC    抄送
         *             RecipientType.BCC    暗送
         *         比如A要给B发邮件，但是A觉得有必要给要让C也看看其内容，就在给B发邮件时，
         *         将邮件内容抄送给C，那么C也能看到其内容了，但是B也能知道A给C抄送过该封邮件
         *         而如果是暗送(密送)给C的话，那么B就不知道A给C发送过该封邮件。
         *     第二个参数
         *         收件人的地址，或者是一个Address[]，用来装抄送或者暗送人的名单。或者用来群发。可以是相同邮箱服务器的，也可以是不同的
         *         这里我们发送给我们的qq邮箱
         */
        //4.3设置主题和内容

        message.setSubject("通过javamail发出！！！");

        //文本部分
        message.setContent("文本邮件测试", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        //发送邮件
        Transport.send(message);
    }

    @Test
    public void Test2() {
        MailInfo mailInfo = new MailInfo();
        //设置主题
        mailInfo.setSubject("最后测试");
        //设置日期
        mailInfo.setDate(new Date());
        //设置发件人
        mailInfo.setFromAddress("524235428@qq.com");
        //设置收件人
        String[] address = {"couragehe135@163.com", "524235428@qq.com"};
        mailInfo.setToAddress(address);
        try {
            MailUtils.send(mailInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
