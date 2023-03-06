package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kyxs.cloud.core.base.annotation.RepeatSubmit;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.mybatisplus.PageQueryDTO;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.constant.BaseConstant;
import com.kyxs.cloud.personnel.api.pojo.dto.CustomHeaderDto;
import com.kyxs.cloud.personnel.api.pojo.dto.EmployeeDto;
import com.kyxs.cloud.personnel.api.pojo.dto.HeaderItemDto;
import com.kyxs.cloud.personnel.api.pojo.entity.CustomHeader;
import com.kyxs.cloud.personnel.api.pojo.entity.Department;
import com.kyxs.cloud.personnel.api.pojo.entity.Employee;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.enums.CustomHeaderEnum;
import com.kyxs.cloud.personnel.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "自定义表头-涉及各业务场景")
@RestController
@RequestMapping("/custom-header")
public class CustomHeaderController extends BaseController {

    @Autowired
    private CustomHeaderService customHeaderService;

    @Autowired
    private Map<String, CustomHeaderCommonService> customHeaderCommonServiceMap;

    @PostMapping("/save")
    @Operation(summary = "自定义表头保存")
    public R save(@Validated @RequestBody CustomHeaderDto customHeaderDto){
        CustomHeader customHeader = modelMapper.map(customHeaderDto,CustomHeader.class);
        if(CustomHeaderEnum.get(customHeader.getModuleCode())==null){
            return R.failed("参数有误，请确认");
        }
        customHeaderService.saveHeader(customHeader);
        return R.ok();
    }
    @GetMapping("/all/{moduleCode}")
    @Operation(summary = "员工信息自定义表头数据、包括已选、所有、未选")
    public R getCustomData(@PathVariable(value = "moduleCode") String moduleCode){
        CustomHeaderEnum customHeaderEnum = CustomHeaderEnum.get(moduleCode);
        if(customHeaderEnum==null){
            return R.failed("参数有误，请确认");
        }
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        Map<String,List<HeaderItemDto>> map = new HashMap<>();
        //查询所有
        List<InfoItem> infoItems = customHeaderCommonServiceMap.get(customHeaderEnum.getProcessor()).getAllHeaderList();
        List<HeaderItemDto> allList = new ArrayList();
        Map<String,String> itemMap = new HashMap();
        infoItems.forEach(infoItem -> {
            HeaderItemDto headerItemDto = new HeaderItemDto();
            headerItemDto.setItemCode(infoItem.getItemCode());
            headerItemDto.setItemName(infoItem.getItemName());
            allList.add(headerItemDto);
            itemMap.put(infoItem.getItemCode(),infoItem.getItemName());
        });
        //已选

        List<CustomHeader> customHeaders = customHeaderService.list(new LambdaQueryWrapper<CustomHeader>().eq(CustomHeader::getCusId,userInfo.getCusId()).eq(CustomHeader::getUserId,userInfo.getUserId()).eq(CustomHeader::getModuleCode, CustomHeaderEnum.EMP_INFO.getModuleCode()));
        map.put("allList",allList);
        if(CollectionUtils.isNotEmpty(customHeaders)){
            List<HeaderItemDto> checkedList = new ArrayList();
            CustomHeader customHeader = customHeaders.get(0);
            String[] headers = customHeader.getHeaders().split(",");
            for (String itemCode:headers){
                HeaderItemDto headerItemDto = new HeaderItemDto();
                headerItemDto.setItemCode(itemCode);
                if(itemMap.containsKey(itemCode)){
                    headerItemDto.setItemName(itemMap.get(itemCode));
                }
                checkedList.add(headerItemDto);
            }
            map.put("checkedList",checkedList);
            //未选
            List<HeaderItemDto> unCheckedList = new ArrayList();
            allList.forEach(infoItem -> {
                if(!Arrays.asList(headers).contains(infoItem.getItemCode())){
                    unCheckedList.add(infoItem);
                }
            });
            map.put("unCheckedList",unCheckedList);
        }else{
            map.put("checkedList",new ArrayList());
            //未选
            map.put("unCheckedList",allList);
        }
        return R.ok(map);
    }
    @GetMapping("/current/{moduleCode}")
    @Operation(summary = "员工信息自定义表头数据查询")
    public R current(@PathVariable(value = "moduleCode") String moduleCode){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        CustomHeaderEnum customHeaderEnum = CustomHeaderEnum.get(moduleCode);
        if(customHeaderEnum==null){
            return R.failed("参数有误，请确认");
        }
        //查询所有
        List<InfoItem> infoItems = customHeaderCommonServiceMap.get(customHeaderEnum.getProcessor()).getAllHeaderList();
        //已选
        List<InfoItem> checkedList = new ArrayList();
        List<CustomHeader> customHeaders = customHeaderService.list(new LambdaQueryWrapper<CustomHeader>().eq(CustomHeader::getCusId,userInfo.getCusId()).eq(CustomHeader::getUserId,userInfo.getUserId()).eq(CustomHeader::getModuleCode, CustomHeaderEnum.EMP_INFO.getModuleCode()));
        if(CollectionUtils.isEmpty(customHeaders)) {
            return R.ok(infoItems);
        }
        String[] checkedHeaders =  customHeaders.get(0).getHeaders().split(",");
        infoItems.forEach(infoItem -> {
            if (Arrays.asList(checkedHeaders).contains(infoItem.getItemCode())){
                checkedList.add(infoItem);
            }
        });
        return R.ok(checkedList);
    }
}
