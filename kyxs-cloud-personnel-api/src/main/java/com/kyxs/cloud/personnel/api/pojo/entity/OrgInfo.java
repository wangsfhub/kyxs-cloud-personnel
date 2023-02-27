package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "机构信息")
@TableName("org_info")
public class OrgInfo {
    @Schema(title = "主键")
    @TableId(value = "id")
    private Long id;

    @Schema(title = "租户ID")
    @TableField(value = "tenant_id")
    private Long tenantId;

    @Schema(title = "客户ID")
    @TableField(value = "cus_id")
    private Long cusId;

    @Schema(title = "组织编码")
    @TableField(value = "org_no")
    private String orgNo;

    @Schema(title = "组织名称")
    @TableField(value = "org_name")
    private String orgName;

    @Schema(title = "上级组织")
    @TableField(value = "super_id")
    private String superId;

    @Schema(title = "组织类别")
    @TableField(value = "org_type")
    private String orgType;

    @Schema(title = "编制人数")
    @TableField(value = "head_count")
    private String headCount;

    @Schema(title = "负责人")
    @TableField(value = "charge_person")
    private String chargePerson;

    @Schema(title = "组织描述")
    @TableField(value = "org_desc")
    private String orgDesc;

}
