package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kyxs.cloud.core.base.annotation.RepeatSubmit;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.mybatisplus.PageQueryDTO;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.RoleCopyDto;
import com.kyxs.cloud.personnel.api.pojo.dto.RoleDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Role;
import com.kyxs.cloud.personnel.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    @Operation(summary = "新增/编辑")
    @RepeatSubmit
    public R save(@Validated @RequestBody RoleDto roleDto){
        Role role = modelMapper.map(roleDto, Role.class);
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        role.setTenantId(userInfo.getTenantId());
        role.setCusId(userInfo.getCusId());
        if(role.getId()!=null){
            roleService.updateById(role);
        }else{
            role.setRoleStatus("1");//启用
            role.setId(IdWorker.getId());
            roleService.save(role);
        }
        return R.ok();
    }
    @PostMapping("/copy")
    @Operation(summary = "角色复制")
    @RepeatSubmit
    public R copy(@Validated @RequestBody RoleCopyDto roleCopyDto){
        roleService.copy(roleCopyDto);
        return R.ok();
    }
    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public R list(@RequestBody PageQueryDTO pageQueryDTO){
        return R.ok(roleService.queryListByPage(new PageQuery(pageQueryDTO)));
    }

    @PostMapping("/update/status")
    @Operation(summary = "启用禁用")
    public R updateStatus( @RequestBody RoleDto roleDto){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(roleDto.getId()!=null&& StringUtils.isNotEmpty(roleDto.getRoleStatus())){
            Role role = new Role();
            role.setCusId(userInfo.getCusId());
            role.setId(roleDto.getId());
            role.setRoleStatus(roleDto.getRoleStatus());
            roleService.updateById(role);
        }
        return R.ok();
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除")
    public R delete( @PathVariable String id){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        roleService.remove(new LambdaQueryWrapper<Role>().eq(Role::getId,Long.parseLong(id)).eq(Role::getCusId,userInfo.getCusId()));
        return R.ok();
    }
    @GetMapping("/all/{cusId}")
    @Operation(summary = "查询所有员工翻译使用")
    public Map<Long,String> getPositions(@PathVariable(value = "cusId") Long cusId){
        return roleService.getRoles(cusId);
    }
    @GetMapping("/list/all")
    @Operation(summary = "查询所有角色，下拉框显示")
    public R all(){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<Role> list = roleService.list(new LambdaQueryWrapper<Role>().eq(Role::getCusId,userInfo.getCusId()).orderByDesc(Role::getCreateTime));
        return R.ok(list);
    }

}
