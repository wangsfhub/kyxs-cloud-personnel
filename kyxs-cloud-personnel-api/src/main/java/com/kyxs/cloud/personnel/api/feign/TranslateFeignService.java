package com.kyxs.cloud.personnel.api.feign;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(contextId = "translateFeign", value = "kyxs-cloud-personnel",url = "http://127.0.0.1:8888")
public interface TranslateFeignService {

    @GetMapping("/dept/all/{cusId}")
    @Operation(summary = "获取客户所有部门")
    Map<Long, String> getDepartments(@PathVariable(value = "cusId") Long cusId);

    @GetMapping("/post/all/{cusId}")
    @Operation(summary = "获取客户所有岗位")
    Map<Long, String> getPositions(@PathVariable(value = "cusId") Long cusId);

    @GetMapping("/emp/all/{cusId}")
    @Operation(summary = "获取客户所有员工")
    Map<Long, String> getEmployees(@PathVariable(value = "cusId") Long cusId);
}

