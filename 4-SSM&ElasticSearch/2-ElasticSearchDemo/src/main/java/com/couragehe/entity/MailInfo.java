package com.couragehe.entity;

import java.util.Date;
import java.util.Properties;

import org.omg.CORBA.DATA_CONVERSION;

/**
 * 封装发送邮件信息(并持久化到数据库)
 *
 * @author CourageHe
 */
public class MailInfo {
    // 邮件发送者的地址 
    private String fromAddress;
    // 邮件接收者的地址
    private String[] toAddress;
    // 邮件主题  
    private String subject;
    // 邮件的文本内容 
    private String content;
    //邮件发送时间
    private Date date;
    // 邮件附件的文件名 
    private String[] attachFileNames;

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String[] getToAddress() {
        return toAddress;
    }

    public void setToAddress(String[] toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }


}
