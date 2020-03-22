package com.couragehe.souzhi.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.couragehe.souzhi.service.SearchService;

/**
 * @PackageName:com.couragehe.souzhi.search.service
 * @ClassName:SearchServiceImpl
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/3/22 15:07
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Override
    public String index() {
        return "index";
    }
}
