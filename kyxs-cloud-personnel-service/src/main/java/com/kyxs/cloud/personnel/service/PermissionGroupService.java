package com.kyxs.cloud.personnel.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.personnel.api.pojo.dto.PermissionGroupDto;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionGroup;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionMenu;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionOrgScope;

import java.util.List;

public interface PermissionGroupService extends IService<PermissionGroup> {

    void saveInfo(PermissionGroupDto permissionGroupDto);
    Page<List<PermissionGroup>> queryListByPage(PageQuery pageQuery);

    List<PermissionOrgScope> queryOrgScopeByGroupId(Long cusId, Long groupId);

    List<PermissionMenu> queryPermissionMenuByGroupId(Long cusId, Long groupId);

    List<PermissionGroup> getGroupsByRoleIds(Long cusId, List<Long> roleIds);
}
