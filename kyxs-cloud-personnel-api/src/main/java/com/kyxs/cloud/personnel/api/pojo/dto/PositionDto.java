package com.kyxs.cloud.personnel.api.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 2, max = 15,message = "岗位名称长度在2～15个字符")
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
    @Length(max = 100,message = "岗位描述长度100个字符以内")
    private String postDesc;
}
