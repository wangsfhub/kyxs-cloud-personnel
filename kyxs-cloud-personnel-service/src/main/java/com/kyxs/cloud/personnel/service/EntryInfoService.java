package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.personnel.api.pojo.entity.EntryInfo;
import com.kyxs.cloud.personnel.api.pojo.entity.EntryInfoSet;

import java.util.List;

public interface EntryInfoService extends IService<EntryInfo> {
    Page<List<EntryInfo>> queryListByPage(PageQuery pageQuery);

    void saveEntrySetInfo(List<EntryInfoSet> entryInfoSets);
}
