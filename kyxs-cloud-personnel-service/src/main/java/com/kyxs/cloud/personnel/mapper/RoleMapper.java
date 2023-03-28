package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.personnel.api.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    PageQuery queryListByPage(PageQuery pageQuery);
}
