package com.kyxs.cloud.personnel.api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "角色信息")
public class UserDto {
    @Schema(title = "主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(title = "租户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    @Schema(title = "客户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cusId;

    @Schema(title = "用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @Schema(title = "用户姓名")
    private String empName;

    @Schema(title = "证件类型")
    private String idType;

    @Schema(title = "证件号码")
    private String idCard;

    @Schema(title = "手机")
    private String phone;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "用户状态")
    private String userStatus;

    @Schema(title = "是否注册")
    private String isRegister;

}
