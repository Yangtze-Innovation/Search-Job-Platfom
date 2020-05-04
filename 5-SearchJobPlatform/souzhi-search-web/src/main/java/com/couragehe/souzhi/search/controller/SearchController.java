package com.couragehe.souzhi.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.couragehe.souzhi.base.ApiResponse;
import com.couragehe.souzhi.base.SearchResponse;
import com.couragehe.souzhi.bean.PositionSearchParam;
import com.couragehe.souzhi.service.PositionElasticService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @PackageName:com.couragehe.souzhi.search.controller
 * @ClassName:SearchController
 * @Description:
 * @Autor:CourageHe
 * @Date: 2020/4/24 23:15
 */
@Controller
@CrossOrigin
public class SearchController {
    @Reference
    PositionElasticService positionElasticService;

    /**
     * 搜索首页
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/list")
    public String list(PositionSearchParam positionSearchParam, ModelMap modelMap){
        SearchResponse searchResponse = positionElasticService.searchPositionFromEs( positionSearchParam);
        modelMap.put("positonList",searchResponse.getData());
        modelMap.put("keyword",positionSearchParam.getKeyword());
        modelMap.put("total",searchResponse.getTotal());
        return "list";
    }
    @PutMapping("souzhi/position/saveAll")
    public ApiResponse putPositionToES(){
        boolean state = positionElasticService.saveAll();
        return ApiResponse.ofMessage(200,"成功执行新增索引请，请稍等");
    }
    /**
     *搜索请求的api接口Api
     * @param positionSearchParam
     * @return
     */
    @RequestMapping("souzhi/position/search")
    @ResponseBody
    public ApiResponse searchPositionFromEs(PositionSearchParam positionSearchParam){
        SearchResponse searchResponse = positionElasticService.searchPositionFromEs( positionSearchParam);
        ApiResponse response = null;
        if(searchResponse != null){
             response = ApiResponse.ofMessage(200,"查询成功");
        }else {
             response = ApiResponse.ofMessage(500,"服务器出bug啦！！！");
        }
        response.setData(searchResponse);
        return response;
    }




}
