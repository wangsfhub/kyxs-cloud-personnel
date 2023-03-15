package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.personnel.api.pojo.entity.CustomHeader;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;

import java.util.List;
import java.util.Map;

public interface CustomHeaderService extends IService<CustomHeader> {
    //保存自定义表头
    void saveHeader(CustomHeader customHeader);

    List<InfoItem> getCurrentHeader(String moduleCode,List<InfoItem> infoItems);

}
