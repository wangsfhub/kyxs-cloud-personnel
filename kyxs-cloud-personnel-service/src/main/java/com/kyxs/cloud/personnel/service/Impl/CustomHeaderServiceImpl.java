package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.entity.CustomHeader;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.mapper.CustomHeaderMapper;
import com.kyxs.cloud.personnel.service.CustomHeaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CustomHeaderService")
@Slf4j
public class CustomHeaderServiceImpl extends ServiceImpl<CustomHeaderMapper, CustomHeader> implements CustomHeaderService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHeader(CustomHeader customHeader) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //1.删除
        baseMapper.delete(new LambdaQueryWrapper<CustomHeader>().eq(CustomHeader::getCusId,userInfo.getCusId()).eq(CustomHeader::getUserId,userInfo.getUserId()).eq(CustomHeader::getModuleCode,customHeader.getModuleCode()));
        //2.保存
        customHeader.setId(IdWorker.getId());
        customHeader.setCusId(userInfo.getCusId());
        customHeader.setTenantId(userInfo.getTenantId());
        customHeader.setUserId(userInfo.getUserId());
        baseMapper.insert(customHeader);
    }

    @Override
    public List<InfoItem> getAllHeaderList() {
        return null;
    }
}
