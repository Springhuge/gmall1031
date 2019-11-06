package com.jihu.gmall.gmallmanageweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jihu.gamll.common.util.PmsUploadUtil;
import com.jihu.gmall.bean.PmsBaseSaleAttr;
import com.jihu.gmall.bean.PmsProductInfo;
import com.jihu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@CrossOrigin
public class SpuController {

    @Reference
    private SpuService spuService;

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){
        List<PmsProductInfo>  pmsProductInfos = spuService.spuList(catalog3Id);
        return  pmsProductInfos;
    }

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){

        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = spuService.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        //将图片上传到分布式的文件存储系统

        //将图片的存储路径返回给页面
        String imurl = PmsUploadUtil.uploadImage(multipartFile);
        return imurl;
    }
}
