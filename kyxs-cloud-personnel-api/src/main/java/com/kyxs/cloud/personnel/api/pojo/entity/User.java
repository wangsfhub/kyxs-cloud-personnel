package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "用户")
@TableName("employee")
public class User {
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

    @Schema(title = "所属部门")
    @TableField(value = "department")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long department;

    @Schema(title = "用户ID")
    @TableField(value = "user_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @Schema(title = "用户姓名")
    @TableField(value = "emp_name")
    private String empName;

    @Schema(title = "证件类型")
    @TableField(value = "id_type")
    private String idType;

    @Schema(title = "证件号码")
    @TableField(value = "id_card")
    private String idCard;

    @Schema(title = "手机")
    @TableField(value = "phone")
    private String phone;

    @Schema(title = "邮箱")
    @TableField(value = "email")
    private String email;

    @Schema(title = "用户状态")
    @TableField(value = "user_status")
    private String userStatus;

    @Schema(title = "是否注册")
    @TableField(value = "is_register")
    private String isRegister;

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

    @Schema(title = "所属角色")
    @TableField(exist = false)
    private List<Role> roles;

    @Schema(title = "拥有权限组")
    @TableField(exist = false)
    private List<PermissionGroup> groups;
}
