package com.jihu.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jihu.gmall.bean.PmsBaseAttrInfo;
import com.jihu.gmall.bean.PmsSearchParam;
import com.jihu.gmall.bean.PmsSearchSkuInfo;
import com.jihu.gmall.bean.PmsSkuAttrValue;
import com.jihu.gmall.service.AttrService;
import com.jihu.gmall.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {

    @Reference
    private SearchService searchService;

    @Reference
    private AttrService attrService;

    @RequestMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, Model model){ //三级分类Id 关键字keyword
        //调用搜索服务，返回搜索结果
        List<PmsSearchSkuInfo> pmsSearchSkuInfolist = searchService.list(pmsSearchParam);
        model.addAttribute("skuLsInfoList",pmsSearchSkuInfolist);

        //抽取检索结果所包含的平台属性集合
        Set<String> valueIdSet = new HashSet<String>();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfolist) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                String valueId = pmsSkuAttrValue.getValueId();
                valueIdSet.add(valueId);
            }
        }
        //根据valueid
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getAttrValueListByValueId(valueIdSet);
        model.addAttribute("attrList",pmsBaseAttrInfos);
        return "list";
    }


    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
