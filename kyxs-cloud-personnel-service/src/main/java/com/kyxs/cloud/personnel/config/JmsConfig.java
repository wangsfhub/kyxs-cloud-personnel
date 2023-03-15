package com.kyxs.cloud.personnel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangsf
 * @desc rocketMQ主题配置
 * @date 2023-03-13
 */
public class JmsConfig {
    /**
     * 主题名称 主题一般是服务器设置好 而不能在代码里去新建topic（ 如果没有创建好，生产者往该主题发送消息 会报找不到topic错误）
     */
    public final static String TOPIC = "hrcloud-topic"; //主题
    public final static String GROUP = "hrcloud-group"; //分组
    public final static String TAG_CALC = "calc"; //计算标签
    public final static String TAG_DECLARE = "declare"; //申报标签

}
