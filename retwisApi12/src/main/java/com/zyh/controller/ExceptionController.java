package com.zyh.controller;


import com.zyh.entity.Msg;
import com.zyh.sysLog.Logweb;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Exceptionの戻り処理
 * SpringbootはExceptionをグローバルにキャッチします
 */
@RestControllerAdvice
public class ExceptionController {

    // shiroのExceptionをキャッチする
    @ExceptionHandler(ShiroException.class)
    public Msg handle401() {
        return Msg.noPermission().add("info", "您没有权限访问！");
    }

    // shiroのExceptionをキャッチする
    @ExceptionHandler(UnknownAccountException.class)
    public Msg unKnow(){
        return Msg.noPermission().add("info", "您没有权限访问！");
    }

    // 他のExceptionをキャッチする

    @ExceptionHandler(Exception.class)
    public Msg globalException(HttpServletRequest request, Throwable ex) {
        return Msg.code(getStatus(request).value()).add("info", "访问出错，无法访问: " + ex.getMessage());
    }
    // HTTPステータスを取得する
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
