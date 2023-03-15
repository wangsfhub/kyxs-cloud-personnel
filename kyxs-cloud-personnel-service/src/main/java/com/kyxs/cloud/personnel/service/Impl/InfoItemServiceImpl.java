package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoSet;
import com.kyxs.cloud.personnel.mapper.InfoItemMapper;
import com.kyxs.cloud.personnel.service.InfoItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InfoItemServiceImpl extends ServiceImpl<InfoItemMapper, InfoItem> implements InfoItemService {

}
