package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kyxs.cloud.core.base.annotation.RepeatSubmit;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.mybatisplus.PageQueryDTO;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.EmployeeDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Employee;
import com.kyxs.cloud.personnel.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "员工管理")
@RestController
@RequestMapping("/emp")
public class EmployeeController extends BaseController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    @Operation(summary = "新增/编辑")
    @RepeatSubmit
    public R save(@Validated @RequestBody EmployeeDto employeeDto){
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        employee.setTenantId(userInfo.getTenantId());
        employee.setCusId(userInfo.getCusId());
        if(employee.getId()!=null){
            employeeService.updateById(employee);
        }else{
            employee.setId(IdWorker.getId());
            employeeService.save(employee);
        }
        return R.ok();
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