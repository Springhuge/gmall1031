package com.jihu.gmall.service;

import com.jihu.gmall.bean.PmsBaseSaleAttr;
import com.jihu.gmall.bean.PmsProductImage;
import com.jihu.gmall.bean.PmsProductInfo;
import com.jihu.gmall.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);
}
