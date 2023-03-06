package com.kyxs.cloud.personnel.api.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(title = "自定义表头信息")
public class CustomHeaderDto {

    @NotBlank(message = "模块编号不能为空！")
    @Schema(title = "模块编号")
    private String moduleCode;

    @NotBlank(message = "表头数据不能为空！")
    @Schema(title = "自定义头项目")
    private String headers;



}
