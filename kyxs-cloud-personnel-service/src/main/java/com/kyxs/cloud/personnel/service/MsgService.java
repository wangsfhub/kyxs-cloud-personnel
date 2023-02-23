package com.kyxs.cloud.personnel.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface MsgService {
    /**
     * 获取账号信息
     */
    Map getAccount();
    /**
     * 发送
     */
    void send();
}
