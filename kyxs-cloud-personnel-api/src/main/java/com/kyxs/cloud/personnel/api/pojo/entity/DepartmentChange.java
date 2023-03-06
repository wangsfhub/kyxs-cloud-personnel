package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "部门变更信息")
@TableName("department_change")
public class DepartmentChange {
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

    @Schema(title = "原部门")
    @TableField(value = "dept_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;

    @Schema(title = "原上级部门")
    @TableField(value = "super_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long superId;

    @Schema(title = "新上级部门")
    @TableField(value = "new_super_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long newSuperId;

    @Schema(title = "变更日期")
    @TableField(value = "change_date")
    private String changeDate;

    @Schema(title = "变更描述")
    @TableField(value = "change_desc")
    private String changeDesc;

}
