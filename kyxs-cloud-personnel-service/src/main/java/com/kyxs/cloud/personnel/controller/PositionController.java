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
import com.kyxs.cloud.personnel.api.pojo.dto.PositionDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Position;
import com.kyxs.cloud.personnel.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "岗位管理")
@RestController
@RequestMapping("/post")
public class PositionController extends BaseController {
    @Autowired
    private PositionService positionService;

    @PostMapping("/save")
    @Operation(summary = "新增/编辑")
    @RepeatSubmit
    public R save(@Validated @RequestBody PositionDto positionDto){
        Position position = modelMapper.map(positionDto, Position.class);
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        position.setTenantId(userInfo.getTenantId());
        position.setCusId(userInfo.getCusId());
        if(position.getId()!=null){
            positionService.updateById(position);
        }else{
            position.setId(IdWorker.getId());
            positionService.save(position);
        }
        return R.ok();
    }

    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public R list(@RequestBody PageQueryDTO pageQueryDTO){
        return R.ok(positionService.queryListByPage(new PageQuery(pageQueryDTO)));
    }

    @PostMapping("/update/status")
    @Operation(summary = "启用禁用")
    public R updateStatus( @RequestBody PositionDto positionDto){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(positionDto.getId()!=null&& StringUtils.isNotEmpty(positionDto.getPostStatus())){
            Position position = new Position();
            position.setCusId(userInfo.getCusId());
            position.setId(positionDto.getId());
            position.setPostStatus(positionDto.getPostStatus());
            positionService.updateById(position);
        }
        return R.ok();
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除")
    public R delete( @PathVariable String id){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        positionService.remove(new LambdaQueryWrapper<Position>().eq(Position::getId,Long.parseLong(id)).eq(Position::getCusId,userInfo.getCusId()));
        return R.ok();
    }
    @GetMapping("/all/{cusId}")
    @Operation(summary = "查询所有员工翻译使用")
    public Map<Long,String> getPositions(@PathVariable(value = "cusId") Long cusId){
        return positionService.getPositions(cusId);
    }
}
