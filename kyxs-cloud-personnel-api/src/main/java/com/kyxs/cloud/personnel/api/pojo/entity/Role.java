package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyxs.cloud.personnel.api.annotation.Translate;
import com.kyxs.cloud.personnel.api.constant.TranslateConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(title = "角色信息")
@TableName("roles")
public class Role {
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

    @Schema(title = "角色名称")
    @TableField(value = "role_name")
    private String roleName;

    @Schema(title = "角色类别")
    @TableField(value = "role_type")
    private String roleType;

    @Schema(title = "角色状态")
    @TableField(value = "role_status")
    private String roleStatus;

    @Schema(title = "角色描述")
    @TableField(value = "role_desc")
    private String roleDesc;

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
