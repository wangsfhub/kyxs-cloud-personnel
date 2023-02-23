package com.kyxs.cloud.personnel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("EmailMsgService")
@Slf4j
public class EmailServiceImpl implements MsgService{
    @Override
    public Map getAccount() {
        return new HashMap(){{
            put("id","111");
            put("pwd","123456");
            put("type","email");
        }};
    }

    @Override
    public void send() {
        log.info("邮件发送成功------------");
    }
}
