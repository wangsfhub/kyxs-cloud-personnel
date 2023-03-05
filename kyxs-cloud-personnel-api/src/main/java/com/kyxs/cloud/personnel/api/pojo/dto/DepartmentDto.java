package com.kyxs.cloud.personnel.api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(title = "部门信息")
public class DepartmentDto {
    @Schema(title = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(title = "租户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    @Schema(title = "客户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cusId;

    @Schema(title = "部门编码")
    private String deptNo;

    @NotBlank(message = "部门名称不能为空！")
    @Schema(title = "部门名称")
    private String deptName;

    @NotNull(message = "上级部门不能为空！")
    @Schema(title = "上级部门")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long superId;

    @NotBlank(message = "部门类别不能为空！")
    @Schema(title = "部门类别")
    private String deptType;

    @Schema(title = "编制人数")
    private String headCount;

    @Schema(title = "负责人")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long leader;

    @Schema(title = "组织描述")
    private String deptDesc;
}
