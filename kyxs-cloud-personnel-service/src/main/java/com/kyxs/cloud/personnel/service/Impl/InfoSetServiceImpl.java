package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoSet;
import com.kyxs.cloud.personnel.mapper.InfoSetMapper;
import com.kyxs.cloud.personnel.service.InfoItemService;
import com.kyxs.cloud.personnel.service.InfoSetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InfoSetServiceImpl extends ServiceImpl<InfoSetMapper, InfoSet> implements InfoSetService {
    @Autowired
    private InfoItemService infoItemService;
    @Override
    public List<InfoSet> getInfoSets() {
        List<InfoSet> list =  baseMapper.getInfoSets();
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(infoSet -> {
                infoSet.setChildren(infoItemService.list(new LambdaQueryWrapper<InfoItem>().eq(InfoItem::getCusId,'1').eq(InfoItem::getItemStatus,"1").orderByAsc(InfoItem::getItemSort)));
            });
        }
        return list;
    }
}
