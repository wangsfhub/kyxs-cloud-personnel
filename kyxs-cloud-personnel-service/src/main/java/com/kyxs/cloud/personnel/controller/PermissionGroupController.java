package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kyxs.cloud.core.base.annotation.RepeatSubmit;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.mybatisplus.PageQueryDTO;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.PermissionGroupDto;
import com.kyxs.cloud.personnel.api.pojo.dto.PermissionMenuDto;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionGroup;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionMenu;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionOrgScope;
import com.kyxs.cloud.personnel.service.PermissionGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Tag(name = "权限组管理")
@RestController
@RequestMapping("/permissionGroup")
public class PermissionGroupController extends BaseController {
    @Autowired
    private PermissionGroupService permissionGroupService;

    @PostMapping("/save")
    @Operation(summary = "新增/编辑")
    @RepeatSubmit
    public R save(@Validated @RequestBody PermissionGroupDto permissionGroupDto){
        permissionGroupService.saveInfo(permissionGroupDto);
        return R.ok();
    }

    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public R list(@RequestBody PageQueryDTO pageQueryDTO){
        return R.ok(permissionGroupService.queryListByPage(new PageQuery(pageQueryDTO)));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "列表查询")
    public R detail(@PathVariable Long id){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<PermissionGroup> list = permissionGroupService.list(new LambdaQueryWrapper<PermissionGroup>().eq(PermissionGroup::getCusId,userInfo.getCusId()).eq(PermissionGroup::getId,id));
        if(CollectionUtils.isNotEmpty(list)){
            PermissionGroup permissionGroup = list.get(0);
            PermissionGroupDto permissionGroupDto = modelMapper.map(permissionGroup,PermissionGroupDto.class);
            //查询机构权限
            List<PermissionOrgScope> orgScopes = permissionGroupService.queryOrgScopeByGroupId(userInfo.getCusId(),permissionGroup.getId());
            permissionGroupDto.setOrgScopes(orgScopes);
            //查询菜单权限
            List<PermissionMenu> menus = permissionGroupService.queryPermissionMenuByGroupId(userInfo.getCusId(),permissionGroup.getId());
            if(CollectionUtils.isNotEmpty(menus)){
                List<PermissionMenuDto> menuDtos = new ArrayList<>();
                menus.forEach(menu->{
                    PermissionMenuDto permissionMenuDto = new PermissionMenuDto();
                    permissionMenuDto.setId(menu.getMenuId());
                    permissionMenuDto.setCheckedOperator(Arrays.asList(menu.getOperationId().split(",")));
                    menuDtos.add(permissionMenuDto);
                });
                permissionGroupDto.setGroupMenus(menuDtos);
            }
            return R.ok(permissionGroupDto);
        }
        return R.failed();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除")
    public R delete(@PathVariable String id){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        permissionGroupService.remove(new LambdaQueryWrapper<PermissionGroup>().eq(PermissionGroup::getId,Long.parseLong(id)).eq(PermissionGroup::getCusId,userInfo.getCusId()));
        return R.ok();
    }
    @GetMapping("/list/all")
    @Operation(summary = "查询所有")
    public R getListAll(){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<PermissionGroup> list = permissionGroupService.list(new LambdaQueryWrapper<PermissionGroup>().eq(PermissionGroup::getCusId,userInfo.getCusId()));
        return R.ok(list);
    }
}
