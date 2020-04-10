package com.couragehe.souzhi.crawler.task.website;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;

public interface WebsiteSpider {

    /**
     * 从page解析职务
     * @param page
     */
    public  void parseJobInfo(Page page);


    /**
     * 从page中进行深度、广度爬取，即添加url
     * @param page
     */
    public  void addPageUrl(Page page);

    /**
     * 爬取的信息持久化至数据库
     * @param resultItems
     */
    public void jobInfoSave(ResultItems resultItems) ;

    }
