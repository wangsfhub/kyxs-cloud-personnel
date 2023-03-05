package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.personnel.api.pojo.entity.CodeItem;

import java.util.List;

public interface CodeItemService extends IService<CodeItem> {

    List<CodeItem> getCodeItems();
}
