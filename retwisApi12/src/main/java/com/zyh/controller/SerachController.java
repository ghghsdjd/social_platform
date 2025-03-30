package com.zyh.controller;

import com.zyh.entity.Msg;
import com.zyh.service.SerachService;
import com.zyh.sysLog.Logweb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 搜索功能
 */
@RestController
public class SerachController {
    @Autowired
    SerachService serachService;
    @Logweb("搜索")
    @GetMapping("/serach")
    public Msg serach(@RequestParam(required = true) String content){
        Map<String, List> serach = serachService.serach(content);
        return Msg.success().add("data",serach);
    }
}
