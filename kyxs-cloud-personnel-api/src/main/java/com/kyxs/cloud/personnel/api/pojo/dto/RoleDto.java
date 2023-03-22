package com.kyxs.cloud.personnel.api.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Schema(title = "角色信息")
public class RoleDto {
    @Schema(title = "主键")
    private Long id;

    @Schema(title = "租户ID")
    private Long tenantId;

    @Schema(title = "客户ID")
    private Long cusId;

    @NotBlank(message = "角色名称不能为空！")
    @Length(min = 2, max = 15,message = "角色名称长度在2～15个字符")
    @Schema(title = "角色名称")
    private String roleName;

    @NotBlank(message = "角色类别不能为空！")
    @Schema(title = "角色类别")
    private String roleType;

    @Schema(title = "角色状态")
    private String roleStatus;

    @Schema(title = "角色描述")
    @Length(max = 100,message = "角色描述长度100个字符以内")
    private String roleDesc;
}
