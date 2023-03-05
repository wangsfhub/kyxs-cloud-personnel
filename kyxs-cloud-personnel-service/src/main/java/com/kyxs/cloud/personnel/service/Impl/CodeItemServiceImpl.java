package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.personnel.api.pojo.entity.CodeItem;
import com.kyxs.cloud.personnel.mapper.CodeItemMapper;
import com.kyxs.cloud.personnel.service.CodeItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CodeItemServiceImpl extends ServiceImpl<CodeItemMapper, CodeItem> implements CodeItemService {

    @Override
    public List<CodeItem> getCodeItems() {
        return baseMapper.getCodeItems();
    }
}
