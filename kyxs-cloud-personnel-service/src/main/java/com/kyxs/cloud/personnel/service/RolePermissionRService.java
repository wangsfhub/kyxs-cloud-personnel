package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.personnel.api.pojo.entity.RolePermissionR;

import java.util.List;

public interface RolePermissionRService extends IService<RolePermissionR> {

    List<RolePermissionR> getListByRoleIds(Long cusId, List<Long> roleIds);
}
