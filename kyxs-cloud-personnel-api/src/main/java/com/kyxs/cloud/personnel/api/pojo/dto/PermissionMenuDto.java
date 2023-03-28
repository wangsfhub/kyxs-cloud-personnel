package com.kyxs.cloud.personnel.api.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(title = "权限菜单信息")
public class PermissionMenuDto {
    @Schema(title = "菜单id")
    private Long id;

    @Schema(title = "菜单名称名称")
    private String name;

    @Schema(title = "操作权限，多个逗号分隔")
    private List<String> checkedOperator;
}
