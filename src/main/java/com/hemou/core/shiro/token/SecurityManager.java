package com.hemou.core.shiro.token;

import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.model.UUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

public class SecurityManager {

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static UUser getToken(){
        return (UUser) getSubject().getPrincipal();
    }

    /**
     * 获取Sessoin
     * @return
     */
    public static Session getSession(){
        return getSubject().getSession();
    }

    /**
     * 将数据存入Session
     * @param key
     * @param value
     */
    public static void setValue(Object key, Object value){
        getSession().setAttribute(key, value);
    }

    /**
     * 从Session获取数据
     * @param key
     * @return
     */
    public static Object getValue(Object key){
        return getSession().getAttribute(key);
    }

    /**
     * 存入验证码置Session
     * @param code
     */
    public static void setVerifyCode(String code){
        if(StringUtils.isEmpty(code)){
            LoggerUtils.info(SecurityManager.class, "设置验证码：验证码不可为空！");
            return;
        }
        setValue("CODE", code);
    }

    /**
     * 判断验证码是否正确
     * @param code
     * @return
     */
    public static boolean isVCodeValid(String code){
        if(StringUtils.isEmpty(code)){
            LoggerUtils.info(SecurityManager.class, "验证验证码：验证码不可为空！");
            return false;
        }
        return code.equals(String.valueOf(getValue("CODE")));
    }

    public static void clearVerifyCode(){
        getSession().removeAttribute("CODE");
    }

    /**
     * 登录
     * @param user
     * @param remember
     * @return
     */
    public static UUser login(UUser user, boolean remember){
        ShiroToken shiroToken = new ShiroToken(user.getEmail(), user.getPswd());
        shiroToken.setRememberMe(remember);
        getSubject().login(shiroToken);
        return getToken();
    }

    /**
     * 登出
     */
    public static void logout(){
        getSubject().logout();
    }
}
