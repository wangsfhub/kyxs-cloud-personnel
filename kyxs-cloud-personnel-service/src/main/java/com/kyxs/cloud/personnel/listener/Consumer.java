//package com.kyxs.cloud.personnel.listener;
//
//import com.kyxs.cloud.personnel.config.JmsConfig;
//import com.kyxs.cloud.personnel.service.EmployeeService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
///**
// * 消费者监听
// */
//@Component
//@RocketMQMessageListener(topic = JmsConfig.TOPIC,consumerGroup = JmsConfig.GROUP)
//@Slf4j
//public class Consumer implements RocketMQListener<MessageExt> {
//    @Autowired
//    private EmployeeService employeeService;
//    /**
//     * 业务逻辑分派Map
//     * Function为函数式接口，
//     * 下面代码中 Function<String, Object> 的含义是接收一个String类型的变量用来获取你要执行哪个Function，实际使用中可自行定义
//     * Function执行完成返回一个Object类型的结果,这个结果就是统一的业务处理返回结果，实际使用中可自行定义
//     */
//    public Map<String, Function<String,Object>> checkResultDispatcher = new HashMap<>();
//    /**
//     * 初始化 业务逻辑分派Map 其中value 存放的是 lambda表达式
//     * 也可以依赖于spring的@PostConstruct 初始化checkResultDispatcher 根据各个技术栈调整
//     */
//    {
//        checkResultDispatcher.put(JmsConfig.TAG_CALC, order -> employeeService.getDetailById(1L));
//        checkResultDispatcher.put(JmsConfig.TAG_DECLARE, order -> employeeService.getDetailById(2L));
//    }
//    @Override
//    public void onMessage(MessageExt messageExt) {
//        byte[] body = messageExt.getBody();
//        String msg = new String(body);
//        log.info("监听到topic：{},tag：{},消息：{}", messageExt.getTopic(),messageExt.getTags(),msg);
//        try {
//            //消费发生异常后会重复消费同一数据，默认会按第3级及之后的延时时间间隔重复消费（即第一次重复消费在首次消费10s后，第二次重复消费在第一次重复消费30s后，以此类推），可以根据消费次数终止重复消费
//            if(messageExt.getReconsumeTimes()>4){
//                log.info("停止重试，写入数据库...");
//                return;
//            }
//            //处理业务
//            String tags = messageExt.getTags();
//            if(StringUtils.isNotEmpty(tags)){
//                Function<String, Object> result = checkResultDispatcher.get(tags);
//                if (result != null) {
//                    //执行这段表达式获得String类型的结果
//                    System.out.println(result.apply(tags));
//                }
//            }
//        }catch (Exception ex){
//            //抛出异常，执行重试逻辑
//            throw new RuntimeException("处理消息失败");
//        }
//    }
//}
