package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "权限组信息")
@TableName("permission_group")
public class PermissionGroup {
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

    @Schema(title = "权限组名称")
    @TableField(value = "group_name")
    private String groupName;

    @Schema(title = "权限组类型")
    @TableField(value = "group_type")
    private String groupType;

    @Schema(title = "权限组描述")
    @TableField(value = "group_desc")
    private String groupDesc;

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


    @Schema(title = "选中机构名称，多个逗号分隔")
    @TableField(exist = false)
    private String orgNames;

    @Schema(title = "选中菜单名称，多个逗号分隔")
    @TableField(exist = false)
    private String menuNames;

}
