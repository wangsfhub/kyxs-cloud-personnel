package com.kyxs.cloud.personnel.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *  启动项目后, 加载系统参数到redis中
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class InitSysConfigCache {
    private final IInitConfigService initConfigService;

    @PostConstruct // 是java注解，在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
    public void init() {
        initConfigService.refreshParam();
    }
}
