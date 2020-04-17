package com.couragehe.souzhi.crawler.task.website;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;

public abstract class WebsiteSpider {
    /*
     * 接口中的变量 默认修饰为 public static final
     * 而抽象类 则可以自定义了
     * 爬取职务的计数器
     */
    static Long count = 0L;
    /**
     * 从page解析职务
     * @param page
     */
    abstract public  void parseJobInfo(Page page);


    /**
     * 从page中进行深度、广度爬取，即添加url
     * @param page
     */
    abstract public  void addPageUrl(Page page);

    /**
     * 爬取的信息持久化至数据库
     * @param resultItems
     */
    abstract public void jobInfoSave(ResultItems resultItems) ;

    }
