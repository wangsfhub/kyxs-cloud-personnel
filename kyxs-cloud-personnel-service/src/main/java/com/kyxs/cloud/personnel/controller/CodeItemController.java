package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kyxs.cloud.core.base.controller.BaseController;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.entity.CodeItem;
import com.kyxs.cloud.personnel.service.CodeItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "代码管理")
@RestController
@RequestMapping("/code")
public class CodeItemController extends BaseController {
    private final CodeItemService codeItemService;

    public CodeItemController(CodeItemService codeItemService) {
        this.codeItemService = codeItemService;
    }

    @GetMapping("/items")
    @Operation(summary = "查询所有系统代码值")
    public R getCodeItems(){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        List<CodeItem> list = codeItemService.getCodeItems();
        Map<String,List<CodeItem>> maps = list.stream().collect(Collectors.groupingBy(CodeItem::getSetId));
        return R.ok(maps);
    }

}
