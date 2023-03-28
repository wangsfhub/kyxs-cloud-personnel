package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "权限组菜单表")
@TableName("permission_menu")
public class PermissionMenu {
    @Schema(title = "主键")
    @TableId(value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(title = "租户ID")
    @TableField(value = "tenant_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    @Schema(title = "客户ID")
    @TableField(value = "cus_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cusId;

    @Schema(title = "分组ID")
    @TableField(value = "group_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long groupId;

    @Schema(title = "菜单ID")
    @TableField(value = "menu_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long menuId;

    @Schema(title = "菜单名称")
    @TableField(exist = false)
    private String menuName;

    @Schema(title = "操作权限id")
    @TableField(value = "operation_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String operationId;

    @Schema(title = "创建者")
    @TableField(value = "creator")
    private String creator;

    @Schema(title = "创建时间")
    @TableField(value = "create_time")
    private String createTime;

    @Schema(title = "操作者")
    @TableField(value = "operator")
    private String operator;

    @Schema(title = "更新时间")
    @TableField(value = "update_time")
    private String updateTime;
}
