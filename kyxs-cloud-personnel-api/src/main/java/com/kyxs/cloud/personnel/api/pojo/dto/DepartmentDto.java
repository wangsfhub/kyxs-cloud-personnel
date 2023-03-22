package com.kyxs.cloud.personnel.api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 20,message = "部门编码长度20个字符以内")
    private String deptNo;

    @NotBlank(message = "部门名称不能为空！")
    @Length(min = 2, max = 15,message = "部门名称长度在2～15个字符")
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
    @Length(max = 100,message = "组织描述长度100个字符以内")
    private String deptDesc;
}
