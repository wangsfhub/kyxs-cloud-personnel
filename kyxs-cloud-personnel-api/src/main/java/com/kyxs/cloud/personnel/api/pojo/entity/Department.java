package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "部门信息")
@TableName("department")
public class Department {
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

    @Schema(title = "部门编号")
    @TableField(value = "dept_no")
    private String deptNo;

    @Schema(title = "部门名称")
    @TableField(value = "dept_name")
    private String deptName;

    @Schema(title = "上级组织")
    @TableField(value = "super_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long superId;

    @Schema(title = "组织类别")
    @TableField(value = "dept_type")
    private String deptType;

    @Schema(title = "编制人数")
    @TableField(value = "head_count")
    private Integer headCount;

    @Schema(title = "负责人")
    @TableField(value = "leader")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long leader;

    @Schema(title = "负责人姓名")
    @TableField(exist = false)
    private String leaderName;

    @Schema(title = "部门描述")
    @TableField(value = "dept_desc")
    private String deptDesc;

    @Schema(title = "部门排序")
    @TableField(value = "dept_sort")
    private Integer deptSort;

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

    @Schema(title = "子集")
    @TableField(exist = false)
    private List<Department> children;
}
