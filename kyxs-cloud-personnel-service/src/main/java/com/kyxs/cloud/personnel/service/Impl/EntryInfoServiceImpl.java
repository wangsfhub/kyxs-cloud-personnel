package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.entity.EntryInfo;
import com.kyxs.cloud.personnel.api.pojo.entity.EntryInfoItem;
import com.kyxs.cloud.personnel.api.pojo.entity.EntryInfoSet;
import com.kyxs.cloud.personnel.api.util.TranslateUtils;
import com.kyxs.cloud.personnel.mapper.EntryInfoMapper;
import com.kyxs.cloud.personnel.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EntryInfoServiceImpl extends ServiceImpl<EntryInfoMapper, EntryInfo> implements EntryInfoService {
    @Autowired
    private EntryInfoItemService entryInfoItemService;

    @Autowired
    private EntryInfoSetService entryInfoSetService;
    @Override
    public Page<List<EntryInfo>> queryListByPage(PageQuery pageQuery) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        QueryWrapper<EntryInfo> queryWrapper=new QueryWrapper();
        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("emp_name");
        columns.add("id_type");
        columns.add("id_card");
        columns.add("company");
        columns.add("department");
        columns.add("position");
        columns.add("entry_status");
        columns.add("full_status");
        columns.add("entry_time");
        columns.add("confirm_time");
        columns.add("emp_status");
        columns.add("create_time");
        queryWrapper.select(columns.toArray(new String[columns.size()]));
        queryWrapper.eq("cus_id",userInfo.getCusId()).orderByDesc("create_time");
        pageQuery =baseMapper.selectPage(pageQuery,queryWrapper);
        //翻译
        TranslateUtils.translateList(pageQuery.getRecords());
        return pageQuery;
    }

    @Override
    @Transactional
    public void saveEntrySetInfo(List<EntryInfoSet> entryInfoSets) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(CollectionUtils.isNotEmpty(entryInfoSets)){
            //1.先删除
            entryInfoSetService.remove(new LambdaQueryWrapper<EntryInfoSet>().eq(EntryInfoSet::getCusId,userInfo.getCusId()));
            entryInfoItemService.remove(new LambdaQueryWrapper<EntryInfoItem>().eq(EntryInfoItem::getCusId,userInfo.getCusId()));
            //2.重新插入
            List<EntryInfoItem> entryInfoItemList = new ArrayList<>();
            for(int i=0;i<entryInfoSets.size();i++){
                //设置排序
                EntryInfoSet entryInfoSet = entryInfoSets.get(i);
                entryInfoSet.setId(IdWorker.getId());
                entryInfoSet.setSetSort(i+1);
                entryInfoSet.setCusId(userInfo.getCusId());
                entryInfoSet.setTenantId(userInfo.getTenantId());
                //保存子集
                entryInfoSetService.save(entryInfoSet);
                //保存项目
                List<EntryInfoItem> children = entryInfoSet.getChildren();
                for(int j=0;j<children.size();j++){
                    EntryInfoItem entryInfoItem = children.get(j);
                    entryInfoItem.setId(IdWorker.getId());
                    entryInfoItem.setItemSort(j+1);
                    entryInfoItem.setCusId(userInfo.getCusId());
                    entryInfoItem.setTenantId(userInfo.getTenantId());
                }
                entryInfoItemList.addAll(children);
            }
            if(CollectionUtils.isNotEmpty(entryInfoItemList)){
                entryInfoItemService.saveBatch(entryInfoItemList);
            }
        }
    }
}
