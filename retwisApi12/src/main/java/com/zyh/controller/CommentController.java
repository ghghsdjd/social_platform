package com.zyh.controller;


import com.zyh.entity.Answer;
import com.zyh.entity.Comment;
import com.zyh.entity.Msg;
import com.zyh.service.CommentService;
import com.zyh.sysLog.Logweb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

/**
 * コメントコントローラー
 */
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    /**
     *
     * @param id  文書ID
     * @param map コメント内容を受け取る
     * @return
     */
    @Logweb("コメント")
    @PostMapping("/comment/{id}")
    public Msg comment(@PathVariable Integer id, @RequestBody Map<String, String> map){
        Comment item = commentService.comment(id, map.get("content"));
        return Msg.success("コメントしました").add("item",item);
    }

    /**
     *
     * @param id 文書ID
     * @return
     */
    @GetMapping("/findCommentByPid/{id}")
    public Msg findCommentByPid(@PathVariable Integer id){
        List<Comment> commentList = commentService.findCommentByPid(id);
        return Msg.success().add("list",commentList);
    }

    /**
     *
     * @param commentId コメントID
     * @param map 返信内容を受け取る
     * @return
     */
    @Logweb("返信")
    @PostMapping("/answer/{commentId}")
    public Msg answer(@PathVariable Integer commentId,@RequestBody Map<String,String> map){
        Answer answer = commentService.answer(commentId, map.get("content"));
        return Msg.success().add("answer",answer);
    }
}
