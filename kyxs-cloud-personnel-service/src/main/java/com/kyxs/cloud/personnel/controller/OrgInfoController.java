package com.kyxs.cloud.personnel.controller;

import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.personnel.api.pojo.dto.OrgInfoDto;
import com.kyxs.cloud.personnel.api.pojo.entity.OrgInfo;
import com.kyxs.cloud.personnel.service.OrgInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "组织管理")
@RestController
@RequestMapping("/org")
public class OrgInfoController extends BaseController {
    @Autowired
    private OrgInfoService orgInfoService;

    @PostMapping("/save")
    @Operation(summary = "新增/编辑")
    public R saveInfo(@Validated @RequestBody OrgInfoDto orgInfoDto){
        OrgInfo orgInfo = modelMapper.map(orgInfoDto,OrgInfo.class);
        orgInfoService.save(orgInfo);
        return R.ok();
    }

    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public R queryUserInfoList(@RequestBody Map paramMap){
        return R.ok();
    }
}
