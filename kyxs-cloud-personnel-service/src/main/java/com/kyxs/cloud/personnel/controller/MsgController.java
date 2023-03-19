package com.kyxs.cloud.personnel.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.SpringApplicationContextUtil;
import com.kyxs.cloud.personnel.api.enums.MsgProcessorEnum;
import com.kyxs.cloud.personnel.config.JmsConfig;
import com.kyxs.cloud.personnel.executor.ServerExecutor;
import com.kyxs.cloud.personnel.service.MsgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;
@Tag(name = "消息管理")
@RestController
@RequestMapping("/demo/msg")
public class MsgController {
    @Qualifier("EmailMsgService")
    @Autowired
    private MsgService emailMsgService;
    @Qualifier("SmsMsgService")
    @Autowired
    private MsgService smsMsgService;
    @Qualifier("WechatMsgService")
    @Autowired
    private MsgService wechatMsgService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;//注入Mq

    /**
     * Spring会自动将Strategy接口的实现类注入到这个Map中，key为bean id，value值则为对应的策略实现类
     */
    @Autowired
    private Map<String, MsgService> msgServiceMap;


    @GetMapping("/email")
    @Operation(summary = "邮件发送",description = "邮件发送")
    public R email(){
        emailMsgService.send();
        return R.ok("邮件发送成功");
    }
    @GetMapping("/sms")
    @Operation(summary = "短信发送",description = "短信发送")
    public R sms(){
        smsMsgService.send();
        return R.ok("短信发送成功");
    }
    @GetMapping("/wechat")
    @Operation(summary = "微信发送",description = "微信发送")
    public R wechat(){
        wechatMsgService.send();
        return R.ok("微信发送成功");
    }
    /**
     * 方式一
     */
    @GetMapping("/send/{type}")
    @Operation(summary = "动态发送")
    public R send(@PathVariable String type){

        MsgProcessorEnum dataProcessorEnum = MsgProcessorEnum.get(type);
        if(dataProcessorEnum == null){
            //没有在枚举中匹配到处理器，说明业务参数不合法或者没有添加对应的业务枚举
            return R.failed("发送失败，找不到对应的服务");
        }
        MsgService processor = SpringApplicationContextUtil.getBean(dataProcessorEnum.getProcessor(), MsgService.class);
        if(processor == null){
            //没有从spring容器中获取到对应处理器到实例，属于异常情况，检查枚举配置和处理器是否正确注入spring容器
            return R.failed("发送失败，找不到对应的服务");
        }
        //交给对应到处理器去处理
        processor.send();
        return R.ok(type+"发送成功");
    }
    /**
     * 方式2
     */
    @GetMapping("/send2/{type}")
    @Operation(summary = "动态发送")
    public R send2(@PathVariable String type){
        msgServiceMap.get(MsgProcessorEnum.get(type).getProcessor()).send();
        return R.ok(type+"发送成功");
    }
    @GetMapping("/func/{type}")
    @Operation(summary = "函数式动态发送")
    public R funcSend(@PathVariable String type){
        MsgProcessorEnum dataProcessorEnum = MsgProcessorEnum.get(type);
        if(dataProcessorEnum == null){
            //没有在枚举中匹配到处理器，说明业务参数不合法或者没有添加对应的业务枚举
            return R.failed("发送失败，找不到对应的服务");
        }
        MsgService processor = SpringApplicationContextUtil.getBean(dataProcessorEnum.getProcessor(), MsgService.class);
        if(processor == null){
            //没有从spring容器中获取到对应处理器到实例，属于异常情况，检查枚举配置和处理器是否正确注入spring容器
            return R.failed("发送失败，找不到对应的服务");
        }
        //交给对应到处理器去处理
        //函数式接口处理
        ServerExecutor.execute(()-> processor.send());
        return R.ok(type+"发送成功");
    }
    @GetMapping("/account/sms")
    @Operation(summary = "获取短信账号信息")
    public R getSmsAccount(){
        //函数式接口处理
        Map account = ServerExecutor.executeAndReturn(()-> smsMsgService.getAccount());
        return R.ok(account);
    }
    /**
     * rocketMQ测试
     */
    @GetMapping(value = "/rocket/product/create")
    public R createProduct() {
        //添加信息
        String msg = "测试消息服务是否能正常接收";
//        String[] tags = {JmsConfig.TAG_CALC, JmsConfig.TAG_DECLARE};
//        for (int i = 0; i < tags.length; i++) {
//            String dest = String.format("%s:%s",JmsConfig.TOPIC,tags[i]);
//            rocketMQTemplate.convertAndSend(dest, msg);
//        }
        String dest = String.format("%s:%s",JmsConfig.TOPIC,"openCustomer");
        rocketMQTemplate.convertAndSend(dest, "[\"1\"]");
        //关闭客户
        dest = String.format("%s:%s",JmsConfig.TOPIC,"createCustomer");
        rocketMQTemplate.convertAndSend(dest, "{\"passPermissions\":[{\"id\":1635951500779859970,\"permissionValue\":\"111111111111\",\"relationPassId\":1635951498980503554,\"types\":\"111111111111\"}],\"upmsCustomer\":{\"address\":\"朝阳\",\"agencyId\":1381788384176693249,\"allocationStatus\":40001,\"allocationTime\":1678876203580,\"area\":\"1\",\"createBy\":\"1\",\"createTime\":\"2023-03-15T18:29:59.927\",\"creditCode\":\"111111151111111111\",\"customIdType\":95020002,\"customNumber\":\"266603T\",\"customType\":95040011,\"customerPrincipalId\":\"1385053845579603969\",\"id\":1635951471340040194,\"licenses\":\"1635950547017080834\",\"name\":\"rocketmq测试客户\",\"orgId\":\"1635951471340040194\",\"scale\":213,\"source\":20030,\"tenantId\":\"1\",\"type\":174,\"updateTime\":\"2023-03-15T18:30:05.200\"},\"upmsOrg\":{\"address\":\"朝阳\",\"area\":\"1\",\"authMode\":30001,\"authTime\":1678876203580,\"createBy\":\"1\",\"createTime\":\"2023-03-15T18:29:59.927\",\"creditCode\":\"111111151111111111\",\"customIdType\":95020002,\"firstOpenTime\":1678876203580,\"id\":\"1635951471340040194\",\"licenses\":\"1635950547017080834\",\"name\":\"rocketmq测试客户\",\"scale\":213,\"status\":20043,\"type\":174,\"updateBy\":\"1385053845579603969\",\"updateTime\":\"2023-03-15T18:30:03.663\"}}");
        return R.ok();
    }
}
