package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoSet;

import java.util.List;

public interface InfoSetService extends IService<InfoSet> {

    List<InfoSet> getInfoSets();
}
