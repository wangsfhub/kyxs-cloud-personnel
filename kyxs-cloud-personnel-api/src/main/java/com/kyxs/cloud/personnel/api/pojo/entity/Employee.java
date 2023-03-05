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
@Schema(title = "员工信息")
@TableName("employee")
public class Employee {
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

    @Schema(title = "员工编号")
    @TableField(value = "emp_no")
    private String empNo;

    @Schema(title = "员工姓名")
    @TableField(value = "emp_name")
    private String empName;

    @Schema(title = "证件类型")
    @TableField(value = "id_type")
    private String idType;

    @Schema(title = "证件号码")
    @TableField(value = "id_card")
    private String idCard;

    @Schema(title = "所属公司")
    @TableField(value = "company")
    private Long company;

    @Schema(title = "所属公司名称")
    @Translate(code = "company", type = TranslateConstant.EMP_DEPT)
    @TableField(exist = false)
    private String compName;

    @Schema(title = "所属部门")
    @TableField(value = "department")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long department;

    @Schema(title = "所属部门名称")
    @Translate(code = "department", type = TranslateConstant.EMP_DEPT)
    @TableField(exist = false)
    private String deptName;

    @Schema(title = "岗位")
    @TableField(value = "position")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long position;

    @Schema(title = "岗位名称")
    @Translate(code = "position", type = TranslateConstant.EMP_DEPT)
    @TableField(exist = false)
    private String positionName;

    @Schema(title = "手机号")
    @TableField(value = "phone")
    private Integer phone;

    @Schema(title = "邮箱")
    @TableField(value = "email")
    private String email;

    @Schema(title = "性别")
    @TableField(value = "gender")
    private String gender;

    @Schema(title = "年龄")
    @TableField(value = "age")
    private String age;

    @Schema(title = "国籍")
    @TableField(value = "nationality")
    private String nationality;

    @Schema(title = "出生日期")
    @TableField(value = "birthday")
    private String birthday;

    @Schema(title = "入职时间")
    @TableField(value = "entry_time")
    private String entryTime;

    @Schema(title = "人员状态")
    @TableField(value = "emp_status")
    private String empStatus;

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
