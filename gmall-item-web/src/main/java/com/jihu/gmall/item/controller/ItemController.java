package com.jihu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.jihu.gmall.bean.PmsProductSaleAttr;
import com.jihu.gmall.bean.PmsSkuInfo;
import com.jihu.gmall.bean.PmsSkuSaleAttrValue;
import com.jihu.gmall.service.SkuService;

import com.jihu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
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
    public String item(@PathVariable String skuId, ModelMap map, HttpServletRequest request){

        String remoteAddr = request.getRemoteAddr();
        //request.getHeader("");//nginx负载均衡
        PmsSkuInfo skuInfo = skuService.getSkuById(skuId,remoteAddr);

        //sku对象
        map.put("skuInfo",skuInfo);
        //销售属性列表
        List<PmsProductSaleAttr> pmsProductSaleAttr = spuService.spuSaleAttrListCheckBySku(skuInfo.getProductId(),skuId);
        map.put("spuSaleAttrListCheckBySku",pmsProductSaleAttr);

        //查询当前sku的spu的其他sku的集合的hash表
        HashMap<String, String> skuSaleAttrHash = new HashMap<>();
        List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(skuInfo.getProductId());
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            String k = "";
            String v = pmsSkuInfo.getId();

            List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValues) {
              k += pmsSkuSaleAttrValue.getSaleAttrValueId() + "|";
            }
            skuSaleAttrHash.put(k,v);
        }

        //将sku销售属性hash表放到页面
        String skuSaleAttrHashStr = JSON.toJSONString(skuSaleAttrHash);
        map.put("skuSaleAttrHashStr",skuSaleAttrHashStr);
        
        return "item";
    }


}
