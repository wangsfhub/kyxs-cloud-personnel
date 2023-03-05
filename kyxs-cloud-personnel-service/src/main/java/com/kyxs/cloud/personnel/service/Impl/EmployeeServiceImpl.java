package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.entity.Employee;
import com.kyxs.cloud.personnel.api.pojo.entity.Position;
import com.kyxs.cloud.personnel.mapper.EmployeeMapper;
import com.kyxs.cloud.personnel.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public Page<List<Employee>> queryListByPage(PageQuery pageQuery) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        pageQuery =baseMapper.selectPage(pageQuery,new LambdaQueryWrapper<Employee>().eq(Employee::getCusId,userInfo.getCusId()).orderByDesc(Employee::getCreateTime));
        return pageQuery;
    }

    @Override
    public Map<Long, String> getEmployees(Long cusId) {
        Map<Long, String> map = new HashMap<>();
        try {
            List<Employee> employees = baseMapper.selectList(new LambdaQueryWrapper<Employee>().eq(Employee::getCusId,cusId));
            for (Employee employee : employees) {
                map.put(employee.getId(), employee.getEmpName());
            }
        } catch (Exception e) {
            throw new BusinessException("员工信息信息获取失败");
        }
        return map;
    }
}
