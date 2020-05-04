package com.couragehe.souzhi.search.controller;

import com.couragehe.souzhi.base.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * @PackageName:com.couragehe.souzhi.base
 * @ClassName:AppErrorController
 * @Description: web页面错误 处理
 * @Autor:CourageHe
 * @Date: 2020/4/23 23:10
 */
@Controller
public class AppErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    private ErrorAttributes errorAttributes;

    public String getErrorPath() {
        return ERROR_PATH;
    }
    @Autowired
    public AppErrorController(ErrorAttributes errorAttributes){
        this.errorAttributes = errorAttributes;
    }

    /**
     * Web页面错误处理
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value=ERROR_PATH,produces="text/html")
    public String errorPageHandler(HttpServletRequest request, HttpServletResponse response){
        int status = response.getStatus();
        switch (status){
            case 403:
                return "403";
            case 404:
                return "404";
            case 500:
                return "500";
        }
        return "index";
    }

    /**
     * 除web页面外的错误处理，比如json/xml等
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ApiResponse errorApiHandler(HttpServletRequest request, HttpServletResponse response){
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);

        Map<String,Object> attr = this.errorAttributes.getErrorAttributes(requestAttributes,false);
        int status = getStatus(request);
        return ApiResponse.ofMessage(status,String.valueOf(attr.getOrDefault("message","error")));
    }
    private  int getStatus(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status != null) {
            return status;
        }
        return 500;
    }

}
