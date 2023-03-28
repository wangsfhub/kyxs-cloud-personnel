package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.personnel.api.pojo.entity.RolePermissionR;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionRMapper extends BaseMapper<RolePermissionR> {

    List<RolePermissionR> getListByRoleIds(@Param("cusId") Long cusId, @Param("roleIds")List<Long> roleIds);
}
