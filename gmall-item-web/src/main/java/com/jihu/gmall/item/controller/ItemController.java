package com.jihu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jihu.gmall.bean.PmsProductSaleAttr;
import com.jihu.gmall.bean.PmsSkuInfo;
import com.jihu.gmall.service.SkuService;

import com.jihu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @Reference
    private SkuService skuService;

    @Reference
    private SpuService spuService;

    @RequestMapping("index")
    public String index(ModelMap modelMap){

        List<String> list = new ArrayList<>();
        for(int i = 0;i < 5 ; i++){
            list.add("循环数据"+i);
        }

        modelMap.put("list",list);
        modelMap.put("hello","hello thymeleaf !!");

        modelMap.put("check","1");
        return "index";
    }

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId,ModelMap map){
        PmsSkuInfo skuInfo = skuService.getSkuById(skuId);

        //sku对象
        map.put("skuInfo",skuInfo);
        //销售属性列表
        List<PmsProductSaleAttr> pmsProductSaleAttr = spuService.spuSaleAttrListCheckBySku(skuInfo.getProductId());
        map.put("spuSaleAttrListCheckBySku",pmsProductSaleAttr);
        return "item";
    }


}
