package com.jihu.gmall.service;

import com.jihu.gmall.bean.PmsBaseSaleAttr;
import com.jihu.gmall.bean.PmsProductInfo;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    List<PmsBaseSaleAttr> baseSaleAttrList();
}
