package com.kyxs.cloud.personnel.api.enums;

import lombok.AllArgsConstructor;

/**
 * 自定义表头枚举类
 */
@AllArgsConstructor
public enum CustomHeaderEnum {
    EMP_INFO("empInfo", "员工花名册");

    private String moduleCode;
    private String desc;

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

    public String getDesc() {
        return desc;
    }

}
