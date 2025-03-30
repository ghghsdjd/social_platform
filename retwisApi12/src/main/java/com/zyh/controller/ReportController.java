package com.zyh.controller;

import com.zyh.dto.ReportDto;
import com.zyh.entity.Msg;
import com.zyh.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    ReportService reportService;
    @PostMapping("/addReport")
    public Msg addReport(@RequestBody ReportDto dto){
        boolean b = reportService.addReport(dto);
        return b?Msg.success("举报成功！"):Msg.fail("你已经举报过了！");
    }
}
