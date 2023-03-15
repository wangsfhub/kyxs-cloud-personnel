package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.enums.CustomHeaderEnum;
import com.kyxs.cloud.personnel.api.pojo.dto.HeaderItemDto;
import com.kyxs.cloud.personnel.api.pojo.entity.CustomHeader;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.mapper.CustomHeaderMapper;
import com.kyxs.cloud.personnel.service.CustomHeaderCommonService;
import com.kyxs.cloud.personnel.service.CustomHeaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    /**
     * 自定义表头查询时调用
     * moduleCode 模块编号
     * infoItems 所有项目，如果没有自定义时，默认显示所有
     */
    @Override
    public List<InfoItem> getCurrentHeader(String moduleCode,List<InfoItem> infoItems) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //已选
        List<InfoItem> checkedList = new ArrayList();
        List<CustomHeader> customHeaders = baseMapper.selectList(new LambdaQueryWrapper<CustomHeader>().eq(CustomHeader::getCusId,userInfo.getCusId()).eq(CustomHeader::getUserId,userInfo.getUserId()).eq(CustomHeader::getModuleCode, CustomHeaderEnum.EMP_INFO.getModuleCode()));
        if(CollectionUtils.isEmpty(customHeaders)) {
            return infoItems;
        }
        String[] checkedHeaders =  customHeaders.get(0).getHeaders().split(",");
        infoItems.forEach(infoItem -> {
            if (Arrays.asList(checkedHeaders).contains(infoItem.getItemCode())){
                checkedList.add(infoItem);
            }
        });
        return checkedList;
    }


}
