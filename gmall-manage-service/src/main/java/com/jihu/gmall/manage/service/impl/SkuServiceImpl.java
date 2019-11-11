package com.jihu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.jihu.gmall.bean.PmsSkuAttrValue;
import com.jihu.gmall.bean.PmsSkuImage;
import com.jihu.gmall.bean.PmsSkuInfo;
import com.jihu.gmall.bean.PmsSkuSaleAttrValue;
import com.jihu.gmall.manage.mapper.PmsSkuImageMapper;
import com.jihu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.jihu.gmall.manage.mapper.PmsskuAttrValueMapper;
import com.jihu.gmall.manage.mapper.PmsskuInfoMapper;
import com.jihu.gmall.service.SkuService;
import com.jihu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private PmsskuInfoMapper pmsskuInfoMapper;

    @Autowired
    private PmsskuAttrValueMapper pmsskuAttrValueMapper;

    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        //插入skuInfo
        int i = pmsskuInfoMapper.insert(pmsSkuInfo);

        //插入平台属性关联
        List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValues) {
            pmsSkuAttrValue.setSkuId(pmsSkuInfo.getId());
            pmsskuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        //插入销售属性关联
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValues) {
            pmsSkuSaleAttrValue.setSkuId(pmsSkuInfo.getId());
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        //插入图片信息
        List<PmsSkuImage> pmsSkuImages = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : pmsSkuImages) {
            pmsSkuImage.setSkuId(pmsSkuInfo.getId());
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }
    }

    public PmsSkuInfo getSkuByIdFromDb(String skuId){
        //商品对象
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsskuInfoMapper.selectOne(pmsSkuInfo);

        //sku商品集合
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(pmsSkuImages);
        return skuInfo;
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId) {

        PmsSkuInfo skuInfo = new PmsSkuInfo();
        //连接缓存
        Jedis jedis = redisUtil.getJedis();
        try {
            //查询缓存
            String skuKey = "sku:"+skuId+"info";
            String skuJson = jedis.get(skuKey);

            if(StringUtils.isNotBlank(skuJson)){
                skuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
            }else{
                //如果缓存中没有，查询缓存
                skuInfo = getSkuByIdFromDb(skuId);

                if(skuInfo != null){
                    //mysql查询结果存入redis
                    jedis.set("sku:"+skuId+":info",JSON.toJSONString(skuInfo));
                }else{
                    //数据库不存在该sku
                    //为了防止缓存穿透，null值设置给redis
                    //3分钟以内缓存不会达到数据库中
                    jedis.setex("sku:"+skuId+":info",60*3,JSON.toJSONString(""));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return skuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {

        List<PmsSkuInfo> pmsSkuInfos = pmsskuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return pmsSkuInfos;
    }
}
