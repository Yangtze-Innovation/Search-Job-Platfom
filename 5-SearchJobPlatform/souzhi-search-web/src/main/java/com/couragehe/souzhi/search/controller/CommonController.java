package com.couragehe.souzhi.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.couragehe.souzhi.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @PackageName:com.couragehe.souzhi.search.controller
 * @ClassName:SearchController
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/3/23 13:41
 */
@Controller
public class CommonController {

    @Reference
    SearchService searchService;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/404")
    public String notFoundPage() {
        return "404";
    }

    @GetMapping("/403")
    public String accessError() {
        return "403";
    }

    @GetMapping("/500")
    public String internalError() {
        return "500";
    }

    @GetMapping("/logout/page")
    public String logoutPage() {
        return "logout";
    }



}
