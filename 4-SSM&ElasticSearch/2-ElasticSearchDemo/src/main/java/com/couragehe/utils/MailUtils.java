package com.couragehe.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.junit.Before;
import org.junit.Test;

import com.couragehe.entity.MailInfo;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MailUtils {
    private static Session session;
    private static MailInfo mailInfo;


    static {

        Properties config = new Properties();
        InputStream in = MailUtils.class.getClassLoader().getResourceAsStream("mail/mail.properties");
        try {
            config.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化 连接参数
        //发件人账号、密钥
        final String emailAccount = config.getProperty("mail.account");
        final String emailPassword = config.getProperty("mail.password");
        String emailHost = config.getProperty("mail.host");
        String emailPort = config.getProperty("mail.port");


        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", emailHost);
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", emailPort);
        props.setProperty("mail.smtp.socketFactory.port", emailPort);
        props.setProperty("mail.smtp.auth", "true");

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        session = Session.getDefaultInstance(props, new Authenticator() {
            //身份认证
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount, emailPassword); //账户 授权码
            }
        });

    }

    /**
     * 以HTML格式发送邮件
     *
     * @param mailInfo 待发送邮件信息
     * @throws Exception
     */
    public static void send(MailInfo mail) throws Exception {
        mailInfo = mail;
        // 1、根据session创建一个邮件消息
        MimeMessage mailMessage = new MimeMessage(session);

        //2、设置收发件对象
        MailUtils.setFromTo(mailMessage);

        // 3、设置邮件消息的主题
        mailMessage.setSubject(mailInfo.getSubject(), "UTF-8");

        // 4、设置邮件消息发送的时间
        mailMessage.setSentDate(mailInfo.getDate());

        //5、动态模板生成邮箱模板
        //5.1 填充数据map
        Map<String, String> map = new HashMap<String, String>();
        map.put("bgColor", "#f40");
        map.put("First", "第一个");
        map.put("Second", "第二个");
        map.put("Third", "第三个");
        map.put("Fourth", "第四个");
        //5.2 生成动态html内容
        String mailBody = MailUtils.transforHtml(map, "mail.ftl");
        //5.3 创建邮件主要内容容器
        Multipart mainPart = new MimeMultipart();
        //5.4 添加邮箱所需的图片id
        MailUtils.setImage(mainPart);
        // 5.5 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        html.setContent(mailBody, "text/html; charset=utf-8");
        mainPart.addBodyPart(html);

        // 4、将MiniMultipart对象设置为邮件内容
        mailMessage.setContent(mainPart);

        // 5、发送邮件
        Transport.send(mailMessage);
    }

    /**
     * 2、设置收发件对象(可多发)
     *
     * @param mailMessage
     * @throws Exception
     */
    private static void setFromTo(MimeMessage mailMessage) throws Exception {
        //1、创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getFromAddress());

        //设置自定义发件人昵称
        String nickFrom = MimeUtility.encodeText("职能搜索平台");
        // 设置邮件消息的发送者
        mailMessage.setFrom(new InternetAddress(nickFrom + "<" + from + ">"));


        // 2、创建邮件的接收者地址，并设置到邮件消息中
        Address[] tos = new Address[mailInfo.getToAddress().length];//多发
        // 设置邮件消息的收件者
        int i = 0;
        for (String mailTO : mailInfo.getToAddress()) {
            //设置收件人名字
            String nickTo = MimeUtility.encodeText("酷酷的用户");
            Address to = new InternetAddress(nickTo + "<" + mailTO + ">");
            //添加收件人地址
            tos[i] = to;
            i++;
        }
        // Message.RecipientType.TO属性表示接收者的类型为TO
        mailMessage.setRecipients(Message.RecipientType.TO, tos);
    }

    /**
     * 设置图片id
     *
     * @param mainPart
     */
    private static void setImage(Multipart mainPart) {

        //1、读取文件路径
        String imagePath = MailUtils.class.getResource("/mail/images/cjdx.jpg").getPath();
        //2、创建图片容器部分
        MimeBodyPart image = new MimeBodyPart();
        //3、读取源文件流并处理
        DataHandler dh = new DataHandler(new FileDataSource(imagePath));//图片路径
        try {
            //4、图片信息放入容器
            image.setDataHandler(dh);
            // 5、创建图片的一个ID表示用于显示在邮件中显示
            image.setContentID("icon-alarm");
            //6、加入主容器部分
            mainPart.addBodyPart(image);
        } catch (MessagingException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    /**
     * 将模板转换为html并初始化对应的的值
     *
     * @param dataMap      动态赋值map
     * @param templateName 模板名
     * @return 将生成的html转换为String返回
     */
    public static String transforHtml(Map<String, String> dataMap, String templateName) {
        try {
            //创建配置实例 
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
            //设置编码
            configuration.setDefaultEncoding("UTF-8");
            //ftl模板文件统一放至 com.lun.template 包下面
            File file = new File(MailUtils.class.getClassLoader().getResource("mail/").getFile());
            configuration.setDirectoryForTemplateLoading(file);
//            configuration.setClassForTemplateLoading(MailUtils.class, "classpath:mail/");
            //获取模板 
            Template template = configuration.getTemplate(templateName);
            //将模板和数据模型合并生成文件
            StringWriter stringWriter = new StringWriter();
            //生成html
            template.process(dataMap, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
//            log.error("html create fail, exception:", e);
        }
        return null;
    }

    @Test
    public void Test() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("bgColor", "#f40");
        map.put("First", "第一个");
        map.put("Second", "第二个");
        map.put("Third", "第三个");
        map.put("Fourth", "第四个");
        System.out.println(transforHtml(map, "mail.ftl"));

        //两种动态生成的方法
//    	String line = createHtml(map, "mail.ftl");
        //填充html模板中的五个参数
//    	String htmlText = MessageFormat.format(line, "#f40", "第一个", "第一个", "第一个", "第二个");

//    	System.out.println(htmlText);

    }

    @SuppressWarnings("restriction")
    @Before
    public void Init() {
        //设置SSL连接、邮件环境
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        //建立邮件会话
        session = Session.getDefaultInstance(props, new Authenticator() {
            //身份认证
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("524235428@qq.com", "onhrxudtxbkubibh"); //账户 授权码
            }
        });
    }

    @Test
    public void TestSend() {

        //建立邮件对象
        MimeMessage message = new MimeMessage(session);
        //设置邮件的发件人、收件人、主题
        try {
            //附带发件人名字
            //设置自定义发件人昵称  
            String nickFrom = MimeUtility.encodeText("职能搜索平台");
            String nickTo = MimeUtility.encodeText("酷酷的用户");
            //设置发信人
            message.setFrom(new InternetAddress(nickFrom + "<524235428@qq.com>"));

            Address[] tos = {new InternetAddress(nickTo + "<couragehe135@163.com>"), new InternetAddress(nickTo + "<couragehe135@163.com>")};
            message.setRecipients(Message.RecipientType.TO, tos);
            message.setSubject("多发出！！！");
            Map<String, String> map = new HashMap<String, String>();
            map.put("bgColor", "#f40");
            map.put("First", "第一个");
            map.put("Second", "第二个");
            map.put("Third", "第三个");
            map.put("Fourth", "第四个");
            String mailBody = transforHtml(map, "mail.ftl");

//            String mailBody = TemplateFactory.genrateHtmlFromFtl(templateName, map);
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            html.setContent(mailBody, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);

            MailUtils.setImage(mainPart);

            // 4、将MiniMultipart对象设置为邮件内容
            message.setContent(mainPart);

            message.saveChanges();
            message.setSentDate(new Date());
            message.saveChanges();
            //发送邮件
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
//    https://blog.csdn.net/qq_16207635/article/details/100565853
//    https://blog.csdn.net/ll837448792/article/details/93620939
//    http://www.shaoqun.com/a/89502.html
    //类读取webapp下的文件
//    https://blog.csdn.net/weixin_34302561/article/details/92614076
}
