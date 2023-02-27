package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.personnel.api.pojo.entity.OrgInfo;
import com.kyxs.cloud.personnel.mapper.OrgInfoMapper;
import com.kyxs.cloud.personnel.service.MsgService;
import com.kyxs.cloud.personnel.service.OrgInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service()
@Slf4j
public class OrgInfoServiceImpl extends ServiceImpl<OrgInfoMapper, OrgInfo> implements OrgInfoService {

}
