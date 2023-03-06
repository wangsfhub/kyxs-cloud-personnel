package com.kyxs.cloud.personnel.api.enums;

import lombok.AllArgsConstructor;

/**
 * 消息发送枚举类
 */
@AllArgsConstructor
public enum MsgProcessorEnum {
    EMAIL("email", "邮件服务", "EmailMsgService"),
    SMS("sms", "短信服务", "SmsMsgService"),
    WECHAT("wechat", "微信服务", "WechatMsgService");
    private String type;
    private String desc;
    //服务服务名
    private String processor;

    public static MsgProcessorEnum get(String type) {
        if (type == null) {
            return null;
        }
        for (MsgProcessorEnum dataProcessorEnum : MsgProcessorEnum.values()) {
            if (type.equals(dataProcessorEnum.getType())) {
                return dataProcessorEnum;
            }
        }
        return null;
    }


    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getProcessor() {
        return processor;
    }
}
