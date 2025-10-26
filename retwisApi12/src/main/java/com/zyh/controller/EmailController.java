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
     * @param email 登録するメールアドレス
     * @param request
     * @return
     * @throws MessagingException
     */
    @Logweb("登録するメールアドレス送る")
    @PostMapping("/sendEmail")
    public Msg sendEmail(@Validated @RequestBody EmailDto email, HttpServletRequest request) throws MessagingException {
        String ip=IpUtil.getIpAddr(request);
        String key=ip+":emailCount";
        String key2=key+":total";
        ArrayList<LimitIp> limitList=new ArrayList<>();
        //インターフェースフロー制御: 1 時間あたり 5 通を超える登録メールは送信できず、
        //1 分あたり 1 通を超えるメールは送信できません。
        //Redisを使用して有効期限を設定する
        limitList.add(new LimitIp(key2,60*60,50,"1時間"));
        limitList.add(new LimitIp(key,60,10,"1分"));
        Map<String, String> msg = ipLimitUtil.ipContro(limitList);
        if(!msg.get("message").equals("success")){
            return Msg.fail(msg.get("message"));
        }
        //メール送る
        emailService.sendRegisterEmail(email.getEmail());
        return Msg.success("メールが送信されました。30分以内に登録を完了してください。");
    }



}
