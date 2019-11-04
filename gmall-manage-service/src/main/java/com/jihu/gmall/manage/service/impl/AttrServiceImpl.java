package com.jihu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jihu.gmall.bean.PmsBaseAttrInfo;
import com.jihu.gmall.bean.PmsBaseAttrValue;
import com.jihu.gmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.jihu.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.jihu.gmall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    @Autowired
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> attrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
        return attrInfos;
    }

    @Override
    public void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        pmsBaseAttrInfoMapper.insert(pmsBaseAttrInfo);
        for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()) {
            pmsBaseAttrValueMapper.insert(pmsBaseAttrValue);
        }
    }

}
