package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyxs.cloud.personnel.api.annotation.Translate;
import com.kyxs.cloud.personnel.api.constant.TranslateConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "角色范围信息")
@TableName("roles_scope")
public class RoleScope {
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

    @Schema(title = "角色ID")
    @TableField(value = "role_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;

    @Schema(title = "范围ID")
    @TableField(value = "scope_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long scopeId;

    @Schema(title = "范围名称，翻译")
    @TableField(exist = false)
    private String scopeName;

    @Schema(title = "员工姓名，翻译")
    @TableField(exist = false)
    @Translate(code = "scopeId", type = TranslateConstant.EMP_NAME)
    private String empName;

    @Schema(title = "部门名称，翻译")
    @TableField(exist = false)
    @Translate(code = "scopeId", type = TranslateConstant.EMP_DEPT)
    private String deptName;

    @Schema(title = "范围类型")
    @TableField(value = "scope_type")
    private String scopeType;

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
