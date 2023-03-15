package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoSet;

import java.util.List;

public interface InfoSetMapper extends BaseMapper<InfoSet> {
    @InterceptorIgnore(tenantLine = "1")
    List<InfoSet> getInfoSets();
}
