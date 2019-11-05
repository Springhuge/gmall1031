package com.jihu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jihu.gmall.bean.PmsBaseSaleAttr;
import com.jihu.gmall.bean.PmsProductInfo;
import com.jihu.gmall.manage.mapper.PmsBaseSaleAttrMapper;
import com.jihu.gmall.manage.mapper.PmsProductInfoMapper;
import com.jihu.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;

    @Autowired
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo productInfo = new PmsProductInfo();
        productInfo.setCatalog3Id(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.select(productInfo);
        return pmsProductInfos;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = pmsBaseSaleAttrMapper.selectAll();
        return pmsBaseSaleAttrs;
    }
}
