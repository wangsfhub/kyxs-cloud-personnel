package com.kyxs.cloud.personnel.api.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(title = "角色复制信息")
public class RoleCopyDto {

    @NotNull(message = "请选择复制的角色！")
    @Schema(title = "复制的角色ID")
    private Long copyRoleId;

    @NotBlank(message = "角色名称不能为空！")
    @Schema(title = "角色名称")
    @Length(min = 2, max = 15,message = "角色名称长度在2～15个字符")
    private String roleName;

    @Schema(title = "是否复制范围")
    private String isCopyScope;

    @Schema(title = "是否复制权限")
    private String isCopyPermission;

    @Schema(title = "角色描述")
    @Length(max = 100,message = "角色描述长度100个字符以内")
    private String roleDesc;
}
