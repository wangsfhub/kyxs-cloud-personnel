package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kyxs.cloud.core.base.annotation.RepeatSubmit;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.DepartmentDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Department;
import com.kyxs.cloud.personnel.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "部门管理")
@RestController
@RequestMapping("/dept")
public class DepartmentController extends BaseController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/tree")
    @Operation(summary = "列表查询")
    public R getAllTree(){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        Department department = new Department();
        department.setSuperId(-1L);
        department.setCusId(userInfo.getCusId());
        return R.ok(departmentService.getOrgList(department));
    }

    @PostMapping("/save")
    @Operation(summary = "新增/编辑")
    @RepeatSubmit
    public R save(@Validated @RequestBody DepartmentDto departmentDto){
        Department department = modelMapper.map(departmentDto,Department.class);
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        department.setTenantId(userInfo.getTenantId());
        department.setCusId(userInfo.getCusId());
        if(department.getId()!=null){
            departmentService.updateById(department);
        }else{
            department.setId(IdWorker.getId());
            departmentService.save(department);
        }
        return R.ok();
    }

    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public R getOrgList(@RequestBody Department department){
        return R.ok(departmentService.getOrgList(department));
    }

    @GetMapping("/all/{cusId}")
    @Operation(summary = "查询所有部门翻译使用")
    public Map<Long,String> getDepartments(@PathVariable(value = "cusId") Long cusId){
        return departmentService.getDepartments(cusId);
    }
}
