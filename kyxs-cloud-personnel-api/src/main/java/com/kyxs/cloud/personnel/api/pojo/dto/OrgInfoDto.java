package com.kyxs.cloud.personnel.api.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(title = "机构信息")
public class OrgInfoDto {
    @Schema(title = "主键")
    private Long id;

    @Schema(title = "租户ID")
    private Long tenantId;

    @Schema(title = "客户ID")
    private Long cusId;

    @Schema(title = "组织编码")
    private String orgNo;

    @NotBlank(message = "组织名称不能为空！")
    @Schema(title = "组织名称")
    private String orgName;

    @NotBlank(message = "上级组织不能为空！")
    @Schema(title = "上级组织")
    private String superId;

    @NotBlank(message = "组织类别不能为空！")
    @Schema(title = "组织类别")
    private String orgType;

    @Schema(title = "编制人数")
    private String headCount;

    @Schema(title = "负责人")
    private String chargePerson;

    @Schema(title = "组织描述")
    private String orgDesc;
}
