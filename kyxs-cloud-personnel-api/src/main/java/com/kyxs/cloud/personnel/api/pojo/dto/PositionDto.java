package com.kyxs.cloud.personnel.api.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(title = "岗位信息")
public class PositionDto {
    @Schema(title = "主键")
    private Long id;

    @Schema(title = "租户ID")
    private Long tenantId;

    @Schema(title = "客户ID")
    private Long cusId;

    @NotBlank(message = "岗位名称不能为空！")
    @Schema(title = "岗位名称")
    private String postName;

    @NotNull(message = "所属部门不能为空！")
    @Schema(title = "所属部门")
    private Long department;

    @NotBlank(message = "岗位类别不能为空！")
    @Schema(title = "岗位类别")
    private String postType;

    @NotBlank(message = "岗位状态不能为空！")
    @Schema(title = "岗位状态")
    private String postStatus;

    @Schema(title = "岗位工资")
    private BigDecimal postSalary;

    @Schema(title = "编制人数")
    private String headCount;


    @Schema(title = "岗位描述")
    private String postDesc;
}
