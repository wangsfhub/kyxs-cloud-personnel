package com.kyxs.cloud.personnel.api.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionOrgScope;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(title = "权限组信息")
public class PermissionGroupDto {
    @Schema(title = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(title = "租户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    @Schema(title = "客户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cusId;

    @NotBlank(message = "权限组名称不能为空！")
    @Length(min = 2, max = 15,message = "岗位名称长度在2～15个字符")
    @Schema(title = "权限组名称")
    private String groupName;

    @Schema(title = "权限组类型")
    private String groupType;

    @Schema(title = "权限组描述")
    @Length(max = 100,message = "权限组描述长度100个字符以内")
    private String groupDesc;

    @Schema(title = "有权机构")
    @NotNull(message = "请选择有权机构！")
    private List<String> groupOrgs;

    @Schema(title = "有权机构")
    private List<PermissionOrgScope> orgScopes;

    @Schema(title = "权限组描述")
    @NotNull(message = "请选择菜单权限！")
    private List<PermissionMenuDto> groupMenus;
}
