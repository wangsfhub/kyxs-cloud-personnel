package com.kyxs.cloud.personnel.service.Impl;

import com.kyxs.cloud.personnel.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("WechatMsgService")
@Slf4j
public class WechatServiceImpl implements MsgService {
    @Override
    public Map getAccount() {
        return new HashMap(){{
            put("id","111");
            put("pwd","123456");
            put("type","wechat");
        }};
    }

    @Override
    public void send() {
        log.info("微信发送成功------------");
    }
}
