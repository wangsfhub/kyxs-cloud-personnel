package com.kyxs.cloud.personnel.api.util;

import java.lang.reflect.Method;
/**
 * list转树
 * @author wangsf
 * @date 2023-3-4
 */
public class EnumUtils {
    /**
     * 根据code获取枚举类的值
     * @param enumType 枚举类
     * @param code 枚举值
     * @param <C> 枚举值类
     * @param <T> 对应值类
     * @return 返回整个枚举
     */
    public static <C, T extends IEnum<Integer, String>> T getEnumValue(Class<T> enumType, C code) {
        try {
            //如果是枚举类型 则调用values方法
            if (enumType.isEnum()) {
                Method values = enumType.getDeclaredMethod("values");
                T[] ts = (T[]) values.invoke(null);
                for (T t : ts) {
                    if (code.equals(t.getCode())) {
                        return t;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据code获取枚举Code
     *
     * @param enumType 枚举类型
     * @param code 枚举值
     * @param <C> 编码类
     * @param <T> 对应值类
     * @return 对应值
     */
    public static <C, T extends IEnum<Object, String>> String getMessageByCode(Class<T> enumType, C code) {
        try {
            if (enumType.isEnum()) {
                Method m = enumType.getDeclaredMethod("values");
                T[] ts = (T[]) m.invoke(null);
                for (T t : ts) {
                    if (code.equals(t.getCode())) {
                        return t.getMessage();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public interface IEnum<C, M> { //如果是成员方法共通，可以抽象为接口或把属性也加上变为抽象类，这里以接口为例子
        /**
         * 获取枚举值
         * @return 枚举值
         */
        C getCode();

        /**
         * 获取对应值
         * @return 对应值
         */
        M getMessage();
    }

}
