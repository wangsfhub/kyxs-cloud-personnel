package com.kyxs.cloud.personnel.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 统一调用类
 */
@Slf4j
public class ServerExecutor {
    //不需要返回值的
    public static void execute(Runnable runnable){
        try {
            //调用前和调用后可以做一些处理，比如熔断、日志等等
            long startTime = System.currentTimeMillis();
            runnable.run();
            //调用后
            log.info("业务执行时间：{}s",(System.currentTimeMillis()-startTime)/1000);
        }catch (Exception e){
            log.error("RPC error：{}",e);
        }
    }
    //需要返回值的
    public static <T> T executeAndReturn(Callable<T> callable){
        try {
            //调用前和调用后可以做一些处理，比如熔断、日志等等
            long startTime = System.currentTimeMillis();
            T t = callable.call();
            //调用后
            log.info("业务执行时间：{}s",(System.currentTimeMillis()-startTime)/1000);
            return t;
        } catch (Exception e) {
            log.error("RPC invoke error：{}",e);
        }
        return null;
    }
}
