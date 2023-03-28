package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.personnel.api.pojo.dto.RoleCopyDto;
import com.kyxs.cloud.personnel.api.pojo.dto.RoleDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends IService<Role> {
    Page<List<Role>> queryListByPage(PageQuery pageQuery);

    Map<Long, String> getRoles(Long cusId);

    void copy(RoleCopyDto roleCopyDto);

    void addOrUpdate(RoleDto roleDto);
}
