package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kyxs.cloud.core.base.annotation.RepeatSubmit;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.DepartmentChangeDto;
import com.kyxs.cloud.personnel.api.pojo.dto.DepartmentDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Department;
import com.kyxs.cloud.personnel.api.pojo.entity.DepartmentChange;
import com.kyxs.cloud.personnel.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "部门管理")
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DepartmentController extends BaseController {
    private final DepartmentService departmentService;

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

    @PostMapping("/change")
    @Operation(summary = "部门变更")
    @RepeatSubmit
    public R saveChangeInfo(@Validated @RequestBody DepartmentChangeDto departmentChangeDto){
        DepartmentChange departmentChange = modelMapper.map(departmentChangeDto,DepartmentChange.class);
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        departmentChange.setTenantId(userInfo.getTenantId());
        departmentChange.setCusId(userInfo.getCusId());
        departmentChange.setId(IdWorker.getId());
        if(departmentChange.getSuperId()==departmentChange.getNewSuperId()){
            throw new BusinessException("新上级部门不能与原上级部门一样");
        }
        departmentService.saveChangeInfo(departmentChange);
        return R.ok();
    }
    @GetMapping("/detail/{id}")
    @Operation(summary = "列表查询")
    public R detail(@PathVariable(value = "id") String id){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        return R.ok(departmentService.getById(id));
    }
    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public R getOrgList(@RequestBody Department department){
        return R.ok(departmentService.getOrgList(department));
    }

    @GetMapping("/departments/{superId}/{isLoadEmp}")
    @Operation(summary = "查询部门下子部门及部门下的人")
    public R getDepartmentsBySuperId(@PathVariable(value = "superId") Long superId,@PathVariable(value = "isLoadEmp") Integer isLoadEmp){
        return R.ok(departmentService.getDepartmentsBySuperId(superId,isLoadEmp));
    }
    @GetMapping("/translate/list")
    @Operation(summary = "查询部门翻译使用")
    public Map<Long,String> getTranslateDepartments(@RequestParam("cusId") Long cusId, @RequestParam("ids") List<Long> ids){
        return departmentService.getTranslateDepartments(cusId,ids);
    }
}
