package com.jihu.gmall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jihu.gmall.bean.PmsSearchParam;
import com.jihu.gmall.bean.PmsSearchSkuInfo;
import com.jihu.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) {

        String dslStr = getSearchDsl(pmsSearchParam);
        System.out.println(dslStr);
        //用api执行复杂查询
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        Search search = new Search.Builder(dslStr).addIndex("gmall1031").addType("PmsSkuInfo").build();

        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<PmsSearchSkuInfo,Void>> hits = execute.getHits(PmsSearchSkuInfo.class);

        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            PmsSearchSkuInfo source = hit.source;

            Map<String,List<String>> highlight = hit.highlight;
            if(highlight != null){
                String skuName = highlight.get("skuName").get(0);
                source.setSkuName(skuName);
            }
            pmsSearchSkuInfos.add(source);
        }
        System.out.println(pmsSearchSkuInfos.size());
        return pmsSearchSkuInfos;
    }

    /**
     *
     * @param pmsSearchParam
     * @return es 搜索查询的json串
     */
    private String getSearchDsl(PmsSearchParam pmsSearchParam) {

        String[] skuAttrValueList = pmsSearchParam.getValueId();
        String keyword = pmsSearchParam.getKeyword();
        String catalogId3 = pmsSearchParam.getCatalog3Id();

        //jest的dsl工具类
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //filter
        if(StringUtils.isNotBlank(catalogId3)){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalogId3",catalogId3);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if(skuAttrValueList != null){
            for (String s : skuAttrValueList) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",s);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }

        //must
        if(StringUtils.isNotBlank(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName",keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }

        //query
        searchSourceBuilder.query(boolQueryBuilder);

        //from
        searchSourceBuilder.from(0);

        //size
        searchSourceBuilder.size(20);

        //highlight
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlight(highlightBuilder);

        //aggs
        TermsBuilder grouby_attr = AggregationBuilders.terms("groubby_attr").field("skuAttrValueList.valueId");
        searchSourceBuilder.aggregation(grouby_attr);

        //sort
        searchSourceBuilder.sort("id",SortOrder.DESC);
        return searchSourceBuilder.toString();
    }
}
