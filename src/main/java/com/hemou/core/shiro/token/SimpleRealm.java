package com.hemou.core.shiro.token;

import com.hemou.common.model.URole;
import com.hemou.common.model.UUser;
import com.hemou.common.service.UPermissionService;
import com.hemou.common.service.URoleService;
import com.hemou.common.service.UUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleRealm extends AuthorizingRealm {

    @Autowired
    private UUserService userService;
    @Autowired
    private URoleService roleService;
    @Autowired
    private UPermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        UUser user = TokenManager.getUser();

        // 拥有角色
        Set<String> roleSet = new HashSet<>();
        List<URole> roles = roleService.selectByUserId(user.getId());
        for(URole r : roles) roleSet.add(r.getType());
        info.setRoles(roleSet);

        // 拥有权限
        Set<String> permsSet = permissionService.selectByUserId(user.getId());
        info.setStringPermissions(permsSet);
        return info;
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
