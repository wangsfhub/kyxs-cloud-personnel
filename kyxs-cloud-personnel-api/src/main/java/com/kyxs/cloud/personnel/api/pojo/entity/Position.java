package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyxs.cloud.personnel.api.annotation.Translate;
import com.kyxs.cloud.personnel.api.constant.TranslateConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(title = "岗位信息")
@TableName("position")
public class Position {
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

    @Schema(title = "岗位名称")
    @TableField(value = "post_name")
    private String postName;

    @Schema(title = "所属部门")
    @TableField(value = "department")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long department;

    @Schema(title = "所属部门名称")
    @TableField(exist = false)
    @Translate(code = "department", type = TranslateConstant.EMP_DEPT)
    private String deptName;

    @Schema(title = "岗位类别")
    @TableField(value = "post_type")
    private String postType;

    @Schema(title = "岗位状态")
    @TableField(value = "post_status")
    private String postStatus;

    @Schema(title = "编制人数")
    @TableField(value = "head_count")
    private Integer headCount;

    @Schema(title = "岗位工资")
    @TableField(value = "post_salary")
    private BigDecimal postSalary;

    @Schema(title = "岗位描述")
    @TableField(value = "post_desc")
    private String postDesc;


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
