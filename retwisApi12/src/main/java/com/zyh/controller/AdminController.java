package com.zyh.controller;

import com.github.pagehelper.PageInfo;
import com.zyh.entity.Msg;
import com.zyh.entity.PostDetai;
import com.zyh.entity.ReportDeatil;
import com.zyh.entity.User;
import com.zyh.service.AdminService;
import com.zyh.service.PostService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RequestMapping("/admin")
@RestController
public class AdminController {
//    @RequiresRoles("admin")
//    @GetMapping("/admin/test")
//    public Msg test(){
//        return Msg.success("admin");
//    }

    @Autowired
    AdminService adminService;
    @Autowired
    PostService postService;

    /**
     * find all users
     * @param page
     * @param size
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/getUser")
    public Msg getUser( @RequestParam(name = "pagenum",defaultValue = "1")Integer page,
                        @RequestParam(name ="pagesize",defaultValue = "5")Integer size){
        PageInfo all = adminService.findAll(page, size);
        return Msg.success().add("userlist",all);
    }

    /**
     * search user by id
     * @param id
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/getOne/{id}")
    public Msg findOne(@PathVariable Integer id){
        User user= adminService.findById(id);
        List<User> userList=new ArrayList<>();
        if(user!=null)
            userList.add(user);
        return Msg.success().add("userlist",userList);
    }

    /**
     * update user status
     * @param id
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/changeStatus/{id}")
    public Msg changeStatus(@PathVariable Integer id){
        boolean b = adminService.changeStatus(id);
        return b?Msg.success("修改成功"):Msg.fail("修改失败");
    }

    /**
     * find all posts
     * @param page
     * @param size
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/getPost")
    public Msg getPost(@RequestParam(name = "pagenum",defaultValue = "1")Integer page,
                       @RequestParam(name ="pagesize",defaultValue = "5")Integer size){
        return Msg.success("").add("postlist",postService.findAll(page, size));
    }

    /**
     * search post by id
     * @param id
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/findOne/{id}")
    public Msg getOne(@PathVariable Integer id){
        PostDetai post = postService.findPostById(id);
        List<PostDetai> postList=new ArrayList<>();
        if(post!=null)
            postList.add(post);
        return Msg.success().add("postlist",postList);
    }

    /**
     * update post status
     * @param id
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/changePostStatus/{id}")
    public Msg changePostStatus(@PathVariable Integer id){
        Boolean b=postService.changeStatus(id);
        return Msg.success();
    }

    /**
     * 通報一覧を取得する
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/findAllReport")
    public Msg findAllReport(){
        List<ReportDeatil> allReport = adminService.findAllReport();
        return Msg.success().add("reportList",allReport);
    }

    /**
     * 通報を処理する
     * @param id
     * @return
     */
    @RequiresRoles("admin")
    @PutMapping("/solve/{id}")
    public Msg solve(@PathVariable Integer id){
        adminService.solve(id);
        return Msg.success("success");
    }


    @RequiresRoles("admin")
    @PutMapping("/deleteSolve/{id}")
    public Msg deleteSolve(@PathVariable Integer id){
        adminService.deleteSolve(id);
        return Msg.success("success");
    }


}
