package com.kyxs.cloud.personnel.api.pojo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Schema(title = "翻译对象实体")
public class TranslateObjectEntity {
    @Schema(title = "翻译信息实体")
    private List<TranslateEntity> translateInfo;

    @Schema(title = "对象信息")
    private Class<?> aClass;

    @Schema(title = "部门信息")
    private Map<Long, String> userUnits;

    @Schema(title = "岗位信息")
    private Map<Long, String> userPositions;

    @Schema(title = "员工信息")
    private Map<Long, String> userEmployees;

    @Schema(title = "字典信息（Map<code_value,dictDesc>）")
    private Map<String, String> dictions;

}
