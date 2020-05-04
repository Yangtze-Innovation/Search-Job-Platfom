package com.couragehe.souzhi.bean;

import java.io.Serializable;

/**
 * @PackageName:com.couragehe.souzhi.bean
 * @ClassName:PositionSearchParam
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/4/25 22:47
 */
public class PositionSearchParam implements Serializable {
    //搜索关键词
    String keyword;
    //工作城市
    String workCity;
    //学历要求
    String educationRequire;
    //来源网站
    String originWebsite;
    //工作性质
    String workNature;
    //起点
    int from;
    //规模
    int size;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getEducationRequire() {
        return educationRequire;
    }

    public void setEducationRequire(String educationRequire) {
        this.educationRequire = educationRequire;
    }

    public String getOriginWebsite() {
        return originWebsite;
    }

    public void setOriginWebsite(String originWebsite) {
        this.originWebsite = originWebsite;
    }

    public String getWorkNature() {
        return workNature;
    }

    public void setWorkNature(String workNature) {
        this.workNature = workNature;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
