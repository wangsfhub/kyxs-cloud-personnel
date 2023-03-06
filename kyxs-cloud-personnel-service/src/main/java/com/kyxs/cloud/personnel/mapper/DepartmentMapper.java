package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.personnel.api.pojo.entity.Department;
import com.kyxs.cloud.personnel.api.pojo.entity.DepartmentChange;

import java.util.List;
import java.util.Map;

public interface DepartmentMapper extends BaseMapper<Department> {
    List<Department> selectTree(Department department);
    List<Department> selectChildrenTree(Department department);
    void saveChangeInfo(DepartmentChange departmentChange);
}
