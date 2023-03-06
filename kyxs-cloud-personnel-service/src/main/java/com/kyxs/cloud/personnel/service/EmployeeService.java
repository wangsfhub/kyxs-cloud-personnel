package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.personnel.api.pojo.entity.Employee;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;

import java.util.List;
import java.util.Map;

public interface EmployeeService extends IService<Employee> {
    Page<List<Employee>> queryListByPage(PageQuery pageQuery);

    Map<Long, String> getEmployees(Long cusId);

}
