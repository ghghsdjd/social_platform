package com.zyh.filter;



import com.zyh.shiro.JWTToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class JWTFilter extends BasicHttpAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * トークンがある場合はトークンを検査し、ない場合は直接通過させる
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        // リクエストヘッダに "token" が含まれているか判定
        if (isLoginAttempt(request, response)) {
            // 存在する場合は executeLogin を呼び出してログイン処理を行い、トークンの正当性を検査する
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                // トークンエラー

                responseError(response, e.getMessage());
               // throw new UnknownAccountException();
            }
        }
        // リクエストヘッダに token が存在しない場合はログイン処理またはゲストアクセスの可能性があり、トークン検査は不要で true を返す
        return true;
    }

    /**
     * ユーザーがログインを試行しているかどうかを判定する
     * header に token フィールドが含まれているかをチェックする
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("token");
        String requestURL = req.getRequestURI();
        if(requestURL.equals("/retwisApi/websocket")) {
            return false;
        }
        return token != null;
    }

    /**
     * ログイン処理を実行する
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        JWTToken jwtToken = new JWTToken(token);
        // realm にログインを委譲し、エラーがあれば例外が投げられる
        getSubject(request, response).login(jwtToken);
        // 例外が投げられなければログイン成功を意味し、true を返す
        return true;
    }

    /**
     * クロスオリジン（CORS）をサポートする
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // CORS の場合、最初に OPTIONS リクエストが送られるため、ここでは OPTIONS リクエストに正しいステータスを直接返す
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);

    }

    /**
     * 不正なリクエストを /unauthorized/** にリダイレクトする
     */
    private void responseError(ServletResponse response, String message) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            // エンコーディングを設定しないとリダイレクト時に日本語（或いは漢字）が空文字列になる
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect("/unauthorized/" + message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
