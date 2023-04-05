package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kyxs.cloud.core.base.annotation.RepeatSubmit;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.mybatisplus.PageQueryDTO;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.RoleCopyDto;
import com.kyxs.cloud.personnel.api.pojo.dto.RoleDto;
import com.kyxs.cloud.personnel.api.pojo.dto.UserDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Role;
import com.kyxs.cloud.personnel.api.pojo.entity.User;
import com.kyxs.cloud.personnel.service.RoleService;
import com.kyxs.cloud.personnel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService userService;

    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public R list(@RequestBody PageQueryDTO pageQueryDTO){
        return R.ok(userService.queryListByPage(new PageQuery(pageQueryDTO)));
    }

    @PostMapping("/update/status")
    @Operation(summary = "启用禁用")
    public R updateStatus( @RequestBody UserDto userDto){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userDto.getId()!=null&& StringUtils.isNotEmpty(userDto.getUserStatus())){
            userService.update(new UpdateWrapper<User>().set("user_status",userDto.getUserStatus()).eq("cus_id",userInfo.getCusId()).eq("id",userDto.getId()));
        }
        return R.ok();
    }
    @PostMapping("/batch/enable")
    @Operation(summary = "批量启用")
    public R batchEnable( @RequestBody List<Long> ids){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        userService.update(new UpdateWrapper<User>().set("user_status","1").eq("cus_id",userInfo.getCusId()).in("id",ids));
        return R.ok();
    }
    @PostMapping("/batch/disable")
    @Operation(summary = "批量禁用")
    public R batchDisable( @RequestBody List<Long> ids){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        userService.update(new UpdateWrapper<User>().set("user_status","0").eq("cus_id",userInfo.getCusId()).in("id",ids));
        return R.ok();
    }
}
