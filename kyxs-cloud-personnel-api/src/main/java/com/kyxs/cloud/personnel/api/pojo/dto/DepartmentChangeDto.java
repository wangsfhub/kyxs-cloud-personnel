package com.kyxs.cloud.personnel.api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(title = "部门变更信息")
public class DepartmentChangeDto {
    @Schema(title = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(title = "租户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    @Schema(title = "客户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cusId;

    @NotNull(message = "原部门不能为空！")
    @Schema(title = "原部门")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;

    @NotNull(message = "原上级部门不能为空！")
    @Schema(title = "上级部门")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long superId;

    @NotNull(message = "新上级部门不能为空！")
    @Schema(title = "上级部门")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long newSuperId;

    @NotBlank(message = "变更日期不能为空！")
    @Schema(title = "变更日期")
    private String changeDate;

    @NotBlank(message = "变更原因不能为空！")
    @Schema(title = "变更原因")
    private String changeDesc;

}
