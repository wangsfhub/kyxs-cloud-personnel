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
@Schema(title = "权限组机构范围")
@TableName("permission_org_scope")
public class PermissionOrgScope {
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

    @Schema(title = "机构ID")
    @TableField(value = "org_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orgId;

    @Schema(title = "机构名称")
    @TableField(exist = false)
    @Translate(code = "orgId", type = TranslateConstant.EMP_DEPT)
    private String orgName;

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
