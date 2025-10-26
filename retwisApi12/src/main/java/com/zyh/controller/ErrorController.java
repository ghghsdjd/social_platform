package com.zyh.controller;

import com.zyh.entity.Msg;
import com.zyh.sysLog.Logweb;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * * カスタムエラーコントローラー
 *
 */
@RestController
public class ErrorController extends BasicErrorController {
    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }


    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        // エラーメッセージを取得する
        String message = body.get("message").toString();

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 401);
        map.put("message", "token认证失败");
        return new ResponseEntity<>(map, status);
    }




}
