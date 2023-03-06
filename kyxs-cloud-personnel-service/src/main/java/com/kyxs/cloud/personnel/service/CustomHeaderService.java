package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.personnel.api.pojo.entity.CustomHeader;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;

import java.util.List;

public interface CustomHeaderService extends IService<CustomHeader> {
    //保存自定义表头
    void saveHeader(CustomHeader customHeader);
    //获取自定义表头的所有项目
    List<InfoItem> getAllHeaderList();

}
