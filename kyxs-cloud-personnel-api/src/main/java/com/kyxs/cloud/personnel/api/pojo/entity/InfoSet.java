package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "子标项信息")
@TableName("info_set")
public class InfoSet {
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

    @Schema(title = "子标项名称")
    @TableField(value = "set_name")
    private String setName;

    @Schema(title = "存储表")
    @TableField(value = "source_table")
    private String sourceTable;

    @Schema(title = "子标类型")
    @TableField(value = "set_type")
    private String setType;

    @Schema(title = "子标状态")
    @TableField(value = "set_status")
    private String setStatus;

    @Schema(title = "子标排序")
    @TableField(value = "set_sort")
    private Integer setSort;

    @Schema(title = "子标描述")
    @TableField(value = "set_desc")
    private String setDesc;


}
