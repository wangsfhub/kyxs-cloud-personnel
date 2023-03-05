package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.personnel.api.pojo.entity.CodeItem;

import java.util.List;

public interface CodeItemMapper extends BaseMapper<CodeItem> {
    List<CodeItem> getCodeItems();
}
