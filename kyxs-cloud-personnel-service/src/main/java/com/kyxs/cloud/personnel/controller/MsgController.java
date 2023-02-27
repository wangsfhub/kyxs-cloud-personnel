package com.kyxs.cloud.personnel.controller;

import com.kyxs.cloud.core.base.result.R;
import com.kyxs.cloud.core.base.utils.SpringApplicationContextUtil;
import com.kyxs.cloud.personnel.enums.MsgProcessorEnum;
import com.kyxs.cloud.personnel.executor.ServerExecutor;
import com.kyxs.cloud.personnel.service.MsgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

}
