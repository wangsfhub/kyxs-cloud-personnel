package com.kyxs.cloud.personnel.api.feign;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(contextId = "translateFeign", value = "kyxs-cloud-personnel",url = "http://127.0.0.1:8888")
public interface TranslateFeignService {
    @GetMapping("/emp/translate/list")
    @Operation(summary = "获取员工姓名，用于翻译")
    Map<Long, String> getTranslateEmployees(@RequestParam("cusId") Long cusId, @RequestParam("ids") List<Long> ids);
    @GetMapping("/post/translate/list")
    @Operation(summary = "获取员工岗位，用于翻译")
    Map<Long, String> getTranslatePositions(@RequestParam("cusId") Long cusId, @RequestParam("ids") List<Long> ids);
    @GetMapping("/dept/translate/list")
    @Operation(summary = "获取员工部门，用于翻译")
    Map<Long, String> getTranslateDepartments(@RequestParam("cusId") Long cusId, @RequestParam("ids") List<Long> ids);
}

