package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "入职登记信息项目")
@TableName("entry_info_item")
public class EntryInfoItem {
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

    @Schema(title = "项目ID")
    @TableField(value = "item_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long itemId;

    @Schema(title = "项目名称")
    @TableField(value = "item_name")
    private String itemName;

    @Schema(title = "项目排序")
    @TableField(value = "item_sort")
    private Integer itemSort;

    @Schema(title = "是否显示")
    @TableField(value = "is_show")
    private String isShow;

    @Schema(title = "是否必填")
    @TableField(value = "is_notnull")
    private String isNotnull;

}
