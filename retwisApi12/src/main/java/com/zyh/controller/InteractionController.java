package com.zyh.controller;

import com.zyh.entity.Msg;
import com.zyh.service.InteractionService;
import com.zyh.sysLog.Logweb;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * * いいねとコレクションコントローラー
 */
@RestController
public class InteractionController {
    @Autowired
    InteractionService interactionService;

    @Logweb("いいね")
    @GetMapping("/onStart/{postId}")
    public Msg onStart(@PathVariable Integer postId){
        if(interactionService.existsStar(postId)){
            return Msg.fail("Failed to like");
        }
        interactionService.onStart(postId);
        return Msg.success("いいね成功");
    }
    @Logweb("いいね解除")
    @GetMapping("/unStart/{postId}")
    public Msg unStart(@PathVariable Integer postId){
        if(!interactionService.existsStar(postId)){
            return Msg.fail("いいね解除失敗");
        }
        interactionService.unStart(postId);
        return Msg.success("いいね解除成功");
    }
    @Logweb("コレクション")
    @GetMapping("/onCollect/{postId}")
    public Msg onCollect(@PathVariable Integer postId){
        if(interactionService.existsCollect(postId)){
            return Msg.fail("コレクション失敗");
        }
        else {
            interactionService.onCollect(postId);
        }
        return Msg.success("コレクション成功");
    }
    @Logweb("コレクション解除")
    @GetMapping("/unCollect/{postId}")
    public Msg unCollect(@PathVariable Integer postId){
        if(!interactionService.existsCollect(postId)){
            return Msg.fail("コレクション解除失敗");
        }
        else {
            interactionService.unCollect(postId);
        }
        return Msg.success("取消收藏成功");
    }



}
