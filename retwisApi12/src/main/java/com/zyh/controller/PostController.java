package com.zyh.controller;


import com.zyh.sysLog.Logweb;
import com.zyh.entity.Msg;
import com.zyh.entity.Post;
import com.zyh.entity.PostDetai;
import com.zyh.service.PostService;

import com.zyh.service.TypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * * 文章コントローラー
 */
@RequestMapping("/post")
@RestController
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    TypeService typeService;

    @Logweb("文章を追加する")
    @ApiOperation(value = "文章を追加する")
    @PostMapping("/add")
    public Msg add(@RequestBody Post post){
        postService.addPost(post);
        return Msg.success("文章追加する成功").add("id",post.getId());
    }

    /**
     * 全部の文章を見つける
     * ページ分け
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findAll")
    public Msg findAll(@RequestParam(name = "pagenum",defaultValue = "1")Integer page,
                       @RequestParam(name ="pagesize",defaultValue = "5")Integer size){
        return Msg.success().add("postList",postService.findAll(page, size));
    }

    /**
     * 文章のIDで内容を見つける
     * @param id
     * @return
     */
    @GetMapping("/findPostById/{id}")
    public Msg findPostById(@PathVariable Integer id){
        PostDetai postDetai=postService.findPostById(id);
        if(postDetai==null)
            return Msg.fail("文章が存在しません");
        return Msg.success().add("data",postDetai);
    }

    /**
     * 文章のIDで内容を見つける
     *
     * @param id
     * @return
     */
    @GetMapping("/findPostByIds/{id}")
    public Msg findPostByIds(@PathVariable Integer id){
        PostDetai postDetai=postService.findPostByIds(id);
        return Msg.success().add("data",postDetai);
    }

    /**
     * 文章のタイプで内容を見つける
     * @param name タイプ名
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findType/{name}")
    public Msg findTypeById(@PathVariable String name,
                            @RequestParam(name = "pagenum",defaultValue = "1")Integer page,
                            @RequestParam(name ="pagesize",defaultValue = "5")Integer size){
        Integer id=typeService.findTypeIdByName(name);
        if(id==null) return Msg.fail("タイプが存在しません");
        return Msg.success().add("postList",postService.findTypeById(id,page, size));
    }

    /**
     * 文章を修正する
     * @param post
     * @return
     */
    @Logweb("文章を修正する")
    @PutMapping("/update")
    public Msg update(@RequestBody Post post){
        postService.update(post);
        return Msg.success("文章を修正する成功");
    }
    @GetMapping("/rePost/{id}")
    public Msg rePost(@PathVariable Integer id){
        int ids = postService.rePost(id);
        return Msg.success("リポスト成功").add("id",ids);
    }

}
