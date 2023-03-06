package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "子标项明细信息")
@TableName("info_item")
public class InfoItem {
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

    @Schema(title = "子标项")
    @TableField(value = "set_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long setId;

    @Schema(title = "项目名称")
    @TableField(value = "item_name")
    private String itemName;

    @Schema(title = "项目编码")
    @TableField(value = "item_code")
    private String itemCode;

    @Schema(title = "项目类型")
    @TableField(value = "item_type")
    private Integer itemType;

    @Schema(title = "项目状态")
    @TableField(value = "item_status")
    private String itemStatus;

    @Schema(title = "项目描述")
    @TableField(value = "item_desc")
    private String itemDesc;

    @Schema(title = "项目排序")
    @TableField(value = "item_sort")
    private Integer itemSort;

    @Schema(title = "是否系统项")
    @TableField(value = "is_sys")
    private String is_sys;

    @Schema(title = "是否必填")
    @TableField(value = "is_notnull")
    private String isNotnull;

    @Schema(title = "是否支持过滤")
    @TableField(value = "is_filter")
    private String isFilter;

    @Schema(title = "是否支持排序")
    @TableField(value = "is_sort")
    private String isSort;

    @Schema(title = "默认值")
    @TableField(value = "default_value")
    private String defaultValue;

    @Schema(title = "代码项ID")
    @TableField(value = "code_set_id")
    private String codeSetId;
}
