package com.zyh.controller;

import com.zyh.entity.Message;
import com.zyh.entity.MessageType;
import com.zyh.entity.Msg;
import com.zyh.service.MessageService;

import com.zyh.sysLog.Logweb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * * メッセージコントローラー
 */
@RequestMapping("/message")
@RestController
public class MessageController {
    @Autowired
    MessageService messageService;

    /**
     * メッセージが存在するかどうかを確認します
     * @return
     */
    @GetMapping("/exists")
    public Msg exists(){
        return Msg.success().add("exists",messageService.exitMessage());
    }

    /**
     * メッセージの種類と数を見つける
     * @return
     */
    @GetMapping("/findMessageTypeNum")
    public Msg findMessageTypeNum(){
        List<MessageType> messageTypeNum = messageService.findMessageTypeNum();
        return Msg.success().add("data",messageTypeNum);
    }

    /**
     * メッセージの種類IDでメッセージを見つける
     * @param id
     * @return
     */
    @GetMapping("/findMessageById/{id}")
    public Msg findMessageById(@PathVariable Integer id){
        List<Message> messageList = messageService.findMessageByTypeId(id);
        return Msg.success().add("list",messageList);
    }

    /**
     * メッセージを既読にする
     * @param id メセージID
     * @return
     */
    @Logweb("メッセージを既読にする")
    @GetMapping("/read/{id}")
    public Msg read(@PathVariable Integer id){
        boolean up = messageService.update(id,2);
        return up?Msg.success("success"):Msg.fail("error");
    }

    /**
     * メッセージを削除する
     * @param id
     * @return
     */
    @Logweb("メッセージを削除する")
    @GetMapping("/delete/{id}")
    public Msg delete(@PathVariable Integer id){
        boolean up = messageService.update(id,0);
        return up?Msg.success("success"):Msg.fail("error");
    }
}
