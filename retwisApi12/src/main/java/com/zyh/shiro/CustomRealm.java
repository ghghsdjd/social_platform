package com.zyh.shiro;


import com.zyh.entity.User;
import com.zyh.mapper.UserMapper;
import com.zyh.service.UserService;
import com.zyh.util.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/**
 * セキュリティフレームワークで使用する Shiro。doGetAuthenticationInfo メソッドをオーバーライドする必要がある。
 * 独自のルールを追加する。
 */

@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;

    /**
     * このメソッドをオーバーライドする必要がある。さもないとエラーになる。
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * このメソッドを使用して ID の正当性を検証する。誤りがあれば例外を投げる。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        Integer userId = JWTUtil.getUserId(token);
        if(userId==null)
        {
            throw new UnknownAccountException();
        }
        else{
            boolean verify = JWTUtil.verify(token, userId);
            if(!verify)
            {
                throw new UnknownAccountException();
            }
        }

        return new SimpleAuthenticationInfo(token, token,this.getName());
    }

    /**
     * 権限認証
     * 現時点では権限関連の処理はまだ扱っていない。
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Integer userId = JWTUtil.getUserId(principals.toString());
        User user = userMapper.findUserById(userId);
        if(user==null)
        {
            throw new UnknownAccountException();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(user.getUsername().equals("admin")){
            info.addRole("admin");
        }
        return info;
    }


}
