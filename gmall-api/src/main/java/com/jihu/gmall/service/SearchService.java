package com.jihu.gmall.service;

import com.jihu.gmall.bean.PmsSearchParam;
import com.jihu.gmall.bean.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
