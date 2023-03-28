package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.personnel.api.pojo.entity.RolePermissionR;
import com.kyxs.cloud.personnel.mapper.RolePermissionRMapper;
import com.kyxs.cloud.personnel.service.RolePermissionRService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RolePermissionRServiceImpl extends ServiceImpl<RolePermissionRMapper, RolePermissionR> implements RolePermissionRService {

    @Override
    public List<RolePermissionR> getListByRoleIds(Long cusId, List<Long> roleIds) {
        return baseMapper.getListByRoleIds(cusId,roleIds);
    }
}
