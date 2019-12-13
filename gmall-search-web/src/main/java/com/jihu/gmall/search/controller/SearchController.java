package com.jihu.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jihu.gmall.bean.*;
import com.jihu.gmall.service.AttrService;
import com.jihu.gmall.service.SearchService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class SearchController {

    @Reference
    private SearchService searchService;

    @Reference
    private AttrService attrService;

    @RequestMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, Model model) { //三级分类Id 关键字keyword
        //调用搜索服务，返回搜索结果
        List<PmsSearchSkuInfo> pmsSearchSkuInfolist = searchService.list(pmsSearchParam);
        model.addAttribute("skuLsInfoList", pmsSearchSkuInfolist);

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
        if (CollectionUtils.isNotEmpty(valueIdSet)) {
            List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getAttrValueListByValueId(valueIdSet);
            model.addAttribute("attrList", pmsBaseAttrInfos);

            //对平台属性集合进一步处理，去掉当前条件中valueId所在的属性组
            String[] delValueIds = pmsSearchParam.getValueId();
            if (delValueIds != null) {
                //面包屑
                List<PmsSearchCrumb> pmsSearchCrumbs = new ArrayList<>();
                for (String delValueId : delValueIds) {
                    Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
                    PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                    //生成面包屑的参数
                    pmsSearchCrumb.setValueId(delValueId);
                    pmsSearchCrumb.setUrlParam(getUrlParam(pmsSearchParam, delValueId));
                    while (iterator.hasNext()) {
                        PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                        for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                            String valueId = pmsBaseAttrValue.getId();
                            if (delValueId.equals(valueId)) {
                                //查询面包屑的属性值名称
                                pmsSearchCrumb.setValueName(pmsBaseAttrValue.getValueName());
                                //删除该属性值所在的属性组
                                iterator.remove();
                            }
                        }
                    }
                    pmsSearchCrumbs.add(pmsSearchCrumb);
                }
                model.addAttribute("attrValueSelectedList", pmsSearchCrumbs);
            }
        }

        //获取url
        String urlParam = getUrlParam(pmsSearchParam);
        model.addAttribute("urlParam", urlParam);
        String keyword = pmsSearchParam.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            model.addAttribute("keyword", keyword);
        }
        return "list";
    }

    /**
     * 获取url参数
     *
     * @return
     */
    public String getUrlParam(PmsSearchParam pmsSearchParam, String... delValueId) {
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String[] skuAttrValueList = pmsSearchParam.getValueId();

        String urlParam = "";

        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&keyword=" + keyword;
            } else {
                urlParam = "keyword=" + keyword;
            }
        }

        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&catalog3Id=" + catalog3Id;
            } else {
                urlParam = "catalog3Id=" + catalog3Id;
            }
        }

        if (skuAttrValueList != null) {
            for (String s : skuAttrValueList) {
                if (delValueId.length > 0) {
                    if (!s.equals(delValueId[0])) {
                        if (StringUtils.isNotBlank(urlParam)) {
                            urlParam = urlParam + "&valueId=" + s;
                        } else {
                            urlParam = "valueId=" + s;
                        }
                    }
                } else {
                    if (StringUtils.isNotBlank(urlParam)) {
                        urlParam = urlParam + "&valueId=" + s;
                    } else {
                        urlParam = "valueId=" + s;
                    }
                }
            }
        }
        return urlParam;
    }


    @RequestMapping("index")
    public String index() {
        return "index";
    }
}
