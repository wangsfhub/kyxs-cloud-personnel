package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoSet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface InfoItemMapper extends BaseMapper<InfoItem> {

}
