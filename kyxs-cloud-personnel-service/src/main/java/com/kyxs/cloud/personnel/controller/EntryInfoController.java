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
import com.kyxs.cloud.message.api.entity.MsgSend;
import com.kyxs.cloud.message.api.feign.MsgSendFeignService;
import com.kyxs.cloud.outreach.api.feign.WechatFeignService;
import com.kyxs.cloud.personnel.api.pojo.dto.EmployeeDto;
import com.kyxs.cloud.personnel.api.pojo.entity.*;
import com.kyxs.cloud.personnel.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "入职管理")
@RestController
@RequestMapping("/entry")
public class EntryInfoController extends BaseController {
    @Autowired
    private EntryInfoService entryInfoService;

    @Autowired
    private InfoSetService infoSetService;
    @Autowired
    private InfoItemService infoItemService;

    @Autowired
    private WechatFeignService wechatFeignService;
    @Autowired
    private MsgSendFeignService msgSendFeignService;
    @PostMapping("/list")
    @Operation(summary = "列表查询")
    public R list(@RequestBody PageQueryDTO pageQueryDTO){
        return R.ok(entryInfoService.queryListByPage(new PageQuery(pageQueryDTO)));
    }

    @GetMapping("/infoSet/list")
    @Operation(summary = "入职信息集查询")
    public R getInfoSetList(){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        return R.ok(infoSetService.getInfoSets());
    }
    @GetMapping("/infoItem/{setId}")
    @Operation(summary = "入职信息集查询")
    public R getInfoItemBySetId(@PathVariable(value = "setId") Long setId){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<InfoItem> infoItems = infoItemService.list(new LambdaQueryWrapper<InfoItem>().eq(InfoItem::getSetId,setId).eq(InfoItem::getItemStatus,"1").orderByAsc(InfoItem::getItemSort));
        return R.ok(infoItems);
    }
    @PostMapping("/infoItem/save")
    @Operation(summary = "保存入职登记信息设置")
    @RepeatSubmit
    public R save(@RequestBody List<EntryInfoSet> entryInfoSets){
        entryInfoService.saveEntrySetInfo(entryInfoSets);
        return R.ok();
    }
}
