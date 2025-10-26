package com.zyh.controller;

import com.zyh.entity.Msg;
import com.zyh.service.FollowingService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * * フォローコントローラー
 */
@RestController
@RequestMapping("/following")
public class FollowingController {
    @Autowired
    FollowingService followingService;

    /**
     * 見ている人がフォローしている人の投稿を見つける
     * @param page ページ番号      デフォルトは1
     * @param size 1ページあたりのデータ数  デフォルトは5
     * @return
     */
    @GetMapping("/findPost")
    public Msg findFollowingPost(@RequestParam(name = "pagenum",defaultValue = "1")Integer page,
                                 @RequestParam(name ="pagesize",defaultValue = "5")Integer size){
        return Msg.success().add("postList",followingService.findFollowingPost(page, size));
    }

    /**
     * フォローしている人を見つける
     * @return
     */
    @GetMapping("/findFowings")
    public Msg findFowings(){
        return Msg.success().add("followers",followingService.findFowings());
    }
}
