package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.entity.Department;
import com.kyxs.cloud.personnel.api.pojo.entity.DepartmentChange;
import com.kyxs.cloud.personnel.mapper.DepartmentMapper;
import com.kyxs.cloud.personnel.mapper.EmployeeMapper;
import com.kyxs.cloud.personnel.service.DepartmentService;

import com.kyxs.cloud.personnel.util.ListToTreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Override
    public List<Department> getOrgList(Department department) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        department.setCusId(userInfo.getCusId());
        List<Department> departments = baseMapper.selectTree(department);
        return departments;
    }

    @Override
    public Map<Long, String> getDepartments(Long cusId) {
        Map<Long, String> map = new HashMap<>();
        try {
            List<Department> departments = baseMapper.selectList(new LambdaQueryWrapper<Department>().eq(Department::getCusId,cusId));
            for (Department department : departments) {
                map.put(department.getId(), department.getDeptName());
            }
        } catch (Exception e) {
            throw new BusinessException("用户单位信息获取失败");
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveChangeInfo(DepartmentChange departmentChange) {
        //添加变更记录
        baseMapper.saveChangeInfo(departmentChange);
        //更新部门的上级组织
        Department department = new Department();
        department.setId(departmentChange.getDeptId());
        department.setSuperId(departmentChange.getNewSuperId());
        baseMapper.updateById(department);
    }
}
