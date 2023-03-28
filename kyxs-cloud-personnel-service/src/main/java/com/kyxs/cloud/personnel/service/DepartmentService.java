package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.personnel.api.pojo.entity.Department;
import com.kyxs.cloud.personnel.api.pojo.entity.DepartmentChange;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface DepartmentService extends IService<Department> {

    List<Department> getOrgList(Department department);

    Map<Long, String> getDepartments(Long cusId);

    void saveChangeInfo(DepartmentChange departmentChange);

    List<Map> getDepartmentsBySuperId(Long superId,Integer isLoadEmp);
}
