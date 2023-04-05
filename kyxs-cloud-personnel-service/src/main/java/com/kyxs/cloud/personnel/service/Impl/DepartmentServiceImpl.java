package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.entity.Department;
import com.kyxs.cloud.personnel.api.pojo.entity.DepartmentChange;
import com.kyxs.cloud.personnel.api.pojo.entity.Employee;
import com.kyxs.cloud.personnel.api.util.TranslateUtils;
import com.kyxs.cloud.personnel.mapper.DepartmentMapper;
import com.kyxs.cloud.personnel.mapper.EmployeeMapper;
import com.kyxs.cloud.personnel.service.DepartmentService;

import com.kyxs.cloud.personnel.service.EmployeeService;
import com.kyxs.cloud.personnel.util.ListToTreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Autowired
    private EmployeeService employeeService;
    @Override
    public List<Department> getOrgList(Department department) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        department.setCusId(userInfo.getCusId());
        List<Department> departments = baseMapper.selectTree(department);
        //翻译
        TranslateUtils.translateList(departments);
        return departments;
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
    /**
     * 查询部门及人员
     */
    @Override
    public List<Map> getDepartmentsBySuperId(Long superId,Integer isLoadEmp) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        Long deptId = superId;
        if(superId==null||superId==-1){
            Department department = baseMapper.selectOne(new LambdaQueryWrapper<Department>().eq(Department::getCusId,userInfo.getCusId()).eq(Department::getSuperId,-1));
            deptId = department.getId();
        }
        //查询下级部门
        List<Department> list = baseMapper.selectList(new LambdaQueryWrapper<Department>().eq(Department::getCusId,userInfo.getCusId()).eq(Department::getSuperId,superId).orderByAsc(Department::getDeptSort));

        //查询下级人员
        List<Employee> employees = null;
        if(superId!=-1&&isLoadEmp==1){
            employees = employeeService.list(new LambdaQueryWrapper<Employee>().eq(Employee::getCusId, userInfo.getCusId()).eq(Employee::getDepartment, deptId).orderByAsc(Employee::getCreateTime));
        }
        List<Map> rtnList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
           //查询是否有下级部门，是否是叶子节点
           List<Long> ids = list.stream().map(Department::getId).collect(Collectors.toList());
           List<Department> leafDepts = baseMapper.selectList(new QueryWrapper<Department>().select("id,super_id").in("super_id",ids));
           Map<Long,List<Department>> children = null;
           if(CollectionUtils.isNotEmpty(leafDepts)){
               children = leafDepts.stream().collect(Collectors.groupingBy(Department::getSuperId));
           }
            Map<Long, List<Department>> finalChildren = children;
            list.forEach(department -> {
               rtnList.add(new HashMap(){{
                   put("id",String.valueOf(department.getId()));
                   put("name",department.getDeptName());
                   put("parentId",department.getSuperId());
                   put("type","department");
                   put("isLeaf",(finalChildren !=null&& finalChildren.containsKey(department.getId()))?true:false);
               }});
           });
        }
        if(CollectionUtils.isNotEmpty(employees)){
            employees.forEach(employee -> {
                rtnList.add(new HashMap(){{
                    put("id",String.valueOf(employee.getId()));
                    put("name",employee.getEmpName());
                    put("type","employee");
                    put("isLeaf",false);
                }});
            });
        }
        return rtnList;
    }

    @Override
    public Map<Long, String> getTranslateDepartments(Long cusId, List<Long> ids) {
        Map<Long, String> map = new HashMap<>();
        try {
            List<Department> departments = baseMapper.selectList(new QueryWrapper<Department>().select("id","dept_name").eq("cus_id",cusId).in("id",ids));
            for (Department department : departments) {
                map.put(department.getId(), department.getDeptName());
            }
        } catch (Exception e) {
            throw new BusinessException("用户单位信息获取失败");
        }
        return map;
    }
}
