package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionGroup;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionMenu;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionOrgScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PermissionGroupMapper extends BaseMapper<PermissionGroup> {
    void insertPermissionOrgScope(@Param("orgScopes") List<PermissionOrgScope> orgScopes);

    void insertPermissionMenu(@Param("permissionMenus")List<PermissionMenu> permissionMenus);

    void deletePermissionOrgScopeByGroupId(@Param("cusId")Long cusId, @Param("groupId")Long groupId);

    void deletePermissionMenuByGroupId(@Param("cusId")Long cusId, @Param("groupId")Long groupId);

    List<PermissionOrgScope> queryOrgScopeByGroupId(@Param("cusId")Long cusId, @Param("groupId")Long groupId);

    List<PermissionMenu> queryPermissionMenuByGroupId(@Param("cusId")Long cusId, @Param("groupId")Long groupId);

    List<PermissionOrgScope> queryOrgScopeByGroupIds(@Param("cusId")Long cusId, @Param("groupIds")List<Long> groupIds);

    List<PermissionMenu> queryPermissionMenuByGroupIds(@Param("cusId")Long cusId, @Param("groupIds")List<Long> groupIds);

    List<PermissionGroup> getGroupsByRoleIds(@Param("cusId")Long cusId, @Param("roleIds")List<Long> roleIds);
}
