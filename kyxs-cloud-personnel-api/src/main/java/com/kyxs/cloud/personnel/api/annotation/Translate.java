package com.kyxs.cloud.personnel.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Translate {
    // 需要翻译的字段名称
    String code() default "";


    /**
     * 数据字典类型(对应dictionary表code字段，多个以英文状态下的逗号分割，数据字典翻译必传！！！)
     *
     * @return
     */
    String[] dictionaryType() default "";

    /**
     * 是否批量翻译字符串,多个值用英文状态下逗号","进行分割 如 1,2,3
     *
     * @return
     */
    boolean whetherStr() default false;

    /**
     * 需要翻译的枚举信息,需implements EnumUtils.IEnum
     *
     * @return
     */
    Class EnumClaz() default Void.class;

    /**
     * 翻译类型
     * @return
     */
    int type();
}
