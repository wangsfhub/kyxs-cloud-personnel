package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.personnel.api.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    PageQuery queryListByPage(PageQuery pageQuery);

    List<Role> getRolesByScopeIds(@Param("cusId") Long cusId, @Param("scopeIds")List<Long> scopeIds);
}
