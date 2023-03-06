package com.kyxs.cloud.personnel.api.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "自定义表头项目信息")
public class HeaderItemDto {

    @Schema(title = "模块编号")
    private String itemCode;

    @Schema(title = "自定义头")
    private String itemName;
}
