package com.couragehe.souzhi.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.couragehe.souzhi.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @PackageName:com.couragehe.souzhi.search.controller
 * @ClassName:SearchController
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/3/23 13:41
 */
@Controller
public class SearchController {
    @Reference
    SearchService searchService;

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        String msg = searchService.index();
        return msg;
    }

}
