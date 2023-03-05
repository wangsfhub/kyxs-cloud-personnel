package com.kyxs.cloud.personnel.api.pojo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import javafx.util.Pair;
import lombok.Data;

import java.lang.reflect.Field;

@Data
@Schema(title = "翻译实体")
public class TranslateEntity {
    @Schema(title="翻译类型")
    private Integer translateType;

    @Schema(title="翻译字段，key-需要翻译的字段 value-接收翻译值字段")
    private Pair<String, String> pair;

    @Schema(title="数据字典类型(对应dictionary表code字段)")
    private String[] dictionaryType;

    @Schema(title="枚举类")
    private Class enumClass;

    @Schema(title="翻译字段信息")
    private Field field;

    @Schema(title="翻译结果字段信息")
    private Field translateField;

    @Schema(title="是否批量翻译字符串")
    private Boolean whetherStr;

    @Schema(title="日期格式")
    private String datePartten;
}
