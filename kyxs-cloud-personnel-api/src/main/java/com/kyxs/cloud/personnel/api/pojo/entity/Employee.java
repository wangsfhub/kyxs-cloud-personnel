package com.kyxs.cloud.personnel.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "员工信息")
@TableName("employee")
public class Employee {
    @Schema(title = "主键")
    @TableId(value = "id")
    private Long id;

    @Schema(title = "租户ID")
    @TableField(value = "tenant_id")
    private Long tenantId;

    @Schema(title = "客户ID")
    @TableField(value = "cus_id")
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
    @TableField(value = "company_id")
    private Long companyId;

    @Schema(title = "所属部门")
    @TableField(value = "dept_id")
    private Long deptId;


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
}
