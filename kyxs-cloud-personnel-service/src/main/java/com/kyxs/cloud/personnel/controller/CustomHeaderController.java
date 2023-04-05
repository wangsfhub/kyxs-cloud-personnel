package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.CustomHeaderDto;
import com.kyxs.cloud.personnel.api.pojo.dto.HeaderItemDto;
import com.kyxs.cloud.personnel.api.pojo.entity.CustomHeader;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.api.enums.CustomHeaderEnum;
import com.kyxs.cloud.personnel.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "自定义表头-涉及各业务场景")
@RestController
@RequestMapping("/custom-header")
@RequiredArgsConstructor
public class CustomHeaderController extends BaseController {

    private final CustomHeaderService customHeaderService;

    private final Map<String, CustomHeaderCommonService> customHeaderCommonServiceMap;

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
            throw new BusinessException("参数有误，请确认");
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

        List<CustomHeader> customHeaders = customHeaderService.list((new LambdaQueryWrapper<CustomHeader>().eq(CustomHeader::getCusId,userInfo.getCusId()).eq(CustomHeader::getUserId,userInfo.getUserId()).eq(CustomHeader::getModuleCode, CustomHeaderEnum.EMP_INFO.getModuleCode())));
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
            throw new BusinessException("参数有误，请确认");
        }
        //查询所有
        List<InfoItem> infoItems = customHeaderCommonServiceMap.get(customHeaderEnum.getProcessor()).getAllHeaderList();
        //已选
        List<InfoItem> checkedList = new ArrayList();
        List<CustomHeader> customHeaders = customHeaderService.list(new LambdaQueryWrapper<CustomHeader>().eq(CustomHeader::getCusId,userInfo.getCusId()).eq(CustomHeader::getUserId,userInfo.getUserId()).eq(CustomHeader::getModuleCode, CustomHeaderEnum.EMP_INFO.getModuleCode()));
        if(CollectionUtils.isEmpty(customHeaders)) {
            //转驼峰返回
            infoItems.forEach(infoItem -> {
                infoItem.setItemCode(StringUtils.underlineToCamel(infoItem.getItemCode()));
            });
            return R.ok(infoItems);
        }
        String[] checkedHeaders =  customHeaders.get(0).getHeaders().split(",");
        infoItems.forEach(infoItem -> {
            if (Arrays.asList(checkedHeaders).contains(infoItem.getItemCode())){
                infoItem.setItemCode(StringUtils.underlineToCamel(infoItem.getItemCode()));
                checkedList.add(infoItem);
            }
        });
        return R.ok(checkedList);
    }
}
