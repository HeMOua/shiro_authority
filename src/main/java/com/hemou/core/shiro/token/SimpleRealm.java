package com.hemou.core.shiro.token;

import com.hemou.common.model.UUser;
import com.hemou.common.service.UUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleRealm extends AuthorizingRealm {

    @Autowired
    private UUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ShiroToken token = (ShiroToken)authenticationToken;
        UUser user = userService.selectByEmail(token.getUsername());
        if(null == user){
            throw new UnknownAccountException("用户不存在！");
        }else if(UUser.INVALID.equals(user.getStatus())){
            throw new LockedAccountException("账号已禁止使用！");
        }
        return new SimpleAuthenticationInfo(user, user.getPswd(), getName());
    }
}
