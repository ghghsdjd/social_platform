package com.zyh.controller;

import com.zyh.dto.EmailDto;
import com.zyh.entity.LimitIp;
import com.zyh.entity.Msg;
import com.zyh.service.EmailService;
import com.zyh.sysLog.Logweb;
import com.zyh.util.IpLimitUtil;
import com.zyh.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;


@RestController
public class EmailController {
    @Autowired
    EmailService emailService;
    @Autowired
    IpLimitUtil ipLimitUtil;

    /**
     *
     * @param email 邮箱地址
     * @param request
     * @return
     * @throws MessagingException
     */
    @Logweb("发送注册邮件")
    @PostMapping("/sendEmail")
    public Msg sendEmail(@Validated @RequestBody EmailDto email, HttpServletRequest request) throws MessagingException {
        String ip=IpUtil.getIpAddr(request);
        String key=ip+":emailCount";
        String key2=key+":total";
        ArrayList<LimitIp> limitList=new ArrayList<>();
        //接口限流，一小时不能超过5封注册邮件，1分钟不超过1封，
        //用reids设置过期时间实现的
        limitList.add(new LimitIp(key2,60*60,50,"一小时"));
        limitList.add(new LimitIp(key,60,10,"一分钟"));
        Map<String, String> msg = ipLimitUtil.ipContro(limitList);
        if(!msg.get("message").equals("success")){
            return Msg.fail(msg.get("message"));
        }
        //发送邮件
        emailService.sendRegisterEmail(email.getEmail());
        return Msg.success("邮件已发送,请在30分钟内完成注册");
    }



}
