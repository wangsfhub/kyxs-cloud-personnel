package com.kyxs.cloud.personnel.enums;

import lombok.AllArgsConstructor;

/**
 * 自定义表头枚举类
 */
@AllArgsConstructor
public enum CustomHeaderEnum {
    DEFAULT("default", "默认","CustomHeaderService"),

    EMP_INFO("empInfo", "员工花名册","EmpInfoService");

    private String moduleCode;
    private String desc;
    //服务服务名
    private String processor;

    public static CustomHeaderEnum get(String moduleCode) {
        if (moduleCode == null) {
            return null;
        }
        for (CustomHeaderEnum customHeaderEnum : CustomHeaderEnum.values()) {
            if (moduleCode.equals(customHeaderEnum.getModuleCode())) {
                return customHeaderEnum;
            }
        }
        return null;
    }


    public String getModuleCode() {
        return moduleCode;
    }

    public String getProcessor() {
        return processor;
    }

    public String getDesc() {
        return desc;
    }
}
