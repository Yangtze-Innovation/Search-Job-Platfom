package com.couragehe.souzhi.base;

import java.io.Serializable;

/**
 * @PackageName:com.couragehe.souzhi.base
 * @ClassName:SearchResponse
 * @Description: 搜索数据封装
 * @Autor:CourageHe
 * @Date: 2020/4/26 0:22
 */
public class SearchResponse implements Serializable {

    private int pageSize;
    private long total;
    private Object data;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
