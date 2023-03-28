package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.personnel.api.pojo.entity.CodeItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CodeItemMapper extends BaseMapper<CodeItem> {
    List<CodeItem> getCodeItems();
}
