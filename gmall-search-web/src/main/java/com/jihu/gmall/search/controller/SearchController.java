package com.jihu.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jihu.gmall.bean.PmsSearchParam;
import com.jihu.gmall.bean.PmsSearchSkuInfo;
import com.jihu.gmall.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SearchController {

    @Reference
    private SearchService searchService;

    @RequestMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, Model model){ //三级分类Id 关键字keyword
        //调用搜索服务，返回搜索结果
        List<PmsSearchSkuInfo> pmsSearchSkuInfolist = searchService.list(pmsSearchParam);
        model.addAttribute("skuLsInfoList",pmsSearchSkuInfolist);
        return "list";
    }


    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
