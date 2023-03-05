package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "代码集表")
@TableName("code_item")
public class CodeItem {
    @Schema(title = "主键")
    @TableId(value = "id")
    private String id;

    @Schema(title = "租户ID")
    @TableField(value = "tenant_id")
    private Long tenantId;

    @Schema(title = "客户ID")
    @TableField(value = "cus_id")
    private Long cusId;

    @Schema(title = "代码项")
    @TableField(value = "set_id")
    private String setId;

    @Schema(title = "代码名称")
    @TableField(value = "code_name")
    private String codeName;

    @Schema(title = "代码状态")
    @TableField(value = "code_status")
    private String codeStatus;

    @Schema(title = "代码排序")
    @TableField(value = "code_sort")
    private String codeSort;
}
