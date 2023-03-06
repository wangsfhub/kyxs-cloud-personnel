package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kyxs.cloud.core.base.annotation.RepeatSubmit;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.mybatisplus.PageQueryDTO;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.EmployeeDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Department;
import com.kyxs.cloud.personnel.api.pojo.entity.Employee;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.service.DepartmentService;
import com.kyxs.cloud.personnel.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "员工管理")
@RestController
@RequestMapping("/emp")
public class EmployeeController extends BaseController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/save")
    @Operation(summary = "新增/编辑")
    @RepeatSubmit
    public R save(@Validated @RequestBody EmployeeDto employeeDto){
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        employee.setTenantId(userInfo.getTenantId());
        employee.setCusId(userInfo.getCusId());
        //获取公司ID
        List<Department> departments = departmentService.list(new LambdaQueryWrapper<Department>().eq(Department::getCusId,userInfo.getCusId()).eq(Department::getSuperId,-1));
        if(CollectionUtils.isNotEmpty(departments)){
            employee.setCompany(departments.get(0).getId());
        }
        if(employee.getId()!=null){
            employeeService.updateById(employee);
        }else{
            employee.setId(IdWorker.getId());
            employeeService.save(employee);
        }
        return R.ok();
    }
    @GetMapping("/header")
    @Operation(summary = "表头查询")
    public R header(){
        List<InfoItem> headers = new ArrayList<>();
        InfoItem infoItem = new InfoItem();
        infoItem.setItemCode("postName");
        infoItem.setItemName("岗位名称");
        infoItem.setIsFilter("1");
        infoItem.setItemType(1);
        headers.add(infoItem);
        infoItem = new InfoItem();
        infoItem.setItemCode("postType");
        infoItem.setItemName("岗位类型");
        infoItem.setIsFilter("0");
        infoItem.setItemType(9);
        infoItem.setCodeSetId("6");
        headers.add(infoItem);
        return R.ok(headers);
    }
    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public R list(@RequestBody PageQueryDTO pageQueryDTO){
        return R.ok(employeeService.queryListByPage(new PageQuery(pageQueryDTO)));
    }
    @GetMapping("/all/{cusId}")
    @Operation(summary = "查询所有员工翻译使用")
    public Map<Long,String> getEmployees(@PathVariable(value = "cusId") Long cusId){
        return employeeService.getEmployees(cusId);
    }

}
