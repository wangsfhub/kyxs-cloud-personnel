package com.kyxs.cloud.personnel.api.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(title = "员工信息")
public class EmployeeDto {
    @Schema(title = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(title = "租户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    @Schema(title = "客户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cusId;

    @Schema(title = "员工编号")
    private String empNo;

    @NotBlank(message = "员工姓名不能为空！")
    @Schema(title = "员工姓名")
    private String empName;

    @NotBlank(message = "证件类型不能为空！")
    @Schema(title = "证件类型")
    private String idType;

    @NotBlank(message = "证件号码不能为空！")
    @Schema(title = "证件号码")
    private String idCard;

    @Schema(title = "所属公司")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long company;

    @NotNull(message = "所属部门不能为空！")
    @Schema(title = "所属部门")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long department;

    @Schema(title = "岗位")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long position;

    @Schema(title = "手机号")
    private String phone;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "性别")
    private String gender;

    @Schema(title = "年龄")
    private String age;

    @Schema(title = "国籍")
    private String nationality;

    @Schema(title = "出生日期")
    private String birthday;

    @NotBlank(message = "入职时间不能为空！")
    @Schema(title = "入职时间")
    private String entryTime;

    @NotBlank(message = "人员状态不能为空！")
    @Schema(title = "人员状态")
    private String empStatus;
}
