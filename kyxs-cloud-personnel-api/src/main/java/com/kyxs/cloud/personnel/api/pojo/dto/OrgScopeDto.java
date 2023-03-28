package com.kyxs.cloud.personnel.api.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(title = "机构人员范围对象")
public class OrgScopeDto {

    @Schema(title ="选中对象id")
    private String id;
    @Schema(title ="选中对象名称")
    private String name;
    @Schema(title = "部门：department，雇员：employee")
    private String type;
}
