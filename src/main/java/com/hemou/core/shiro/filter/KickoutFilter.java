package com.hemou.core.shiro.filter;

import com.hemou.common.utils.HTTPUtils;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Result;
import com.hemou.core.shiro.ShiroConstant;
import com.hemou.core.shiro.cache.JedisManager;
import com.hemou.core.shiro.session.ShiroSessionRepository;
import com.hemou.core.shiro.token.TokenManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class KickoutFilter extends AccessControlFilter {


    static JedisManager jedisManager;
    static ShiroSessionRepository sessionRepository;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        Subject subject = getSubject(request, response);
        // 非登录状态放行
        if(!subject.isAuthenticated() && !subject.isRemembered()) return true;
        // 获取当前sessionId
        Session session = subject.getSession();
        Serializable sessionId = session.getId();

        Boolean isKickOut = (Boolean) session.getAttribute(ShiroConstant.KICK_OUT_STATUS_KEY);
        if(null != isKickOut && isKickOut) return false;

        String id = String.valueOf(TokenManager.getUser().getId());
        byte[] keyByte = buildOnlineStatusKey(id).getBytes();
        byte[] sessionIdByte = jedisManager.get(ShiroConstant.DB_INDEX, keyByte);
        if (null != sessionIdByte) {
            // 如果存在记录，并且session一致则更新
            String sessionStr = new String(sessionIdByte);
            if(sessionId.equals(sessionStr)){
                jedisManager.setex(ShiroConstant.DB_INDEX, keyByte, sessionIdByte, ShiroConstant.SESSION_EXPIRE_TIME);
            }else{ // 如果 session 不一致，那就是在他处登录了
                Session oldSession = sessionRepository.getSession(sessionStr);
                jedisManager.setex(ShiroConstant.DB_INDEX, keyByte, sessionId.toString().getBytes(), ShiroConstant.SESSION_EXPIRE_TIME);
                if(null != oldSession){ // 设置老session的踢出状态为true
                    oldSession.setAttribute(ShiroConstant.KICK_OUT_STATUS_KEY, true);
                    sessionRepository.saveSession(oldSession);
                    LoggerUtils.info(getClass(), "kickout old session success,oldId[%s]", sessionStr);
                }
            }
        }else{ // 如果不存在记录，则添加
            jedisManager.setex(ShiroConstant.DB_INDEX, keyByte, sessionId.toString().getBytes(), ShiroConstant.SESSION_EXPIRE_TIME);
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        subject.logout();
        saveRequest(request);
        if(HTTPUtils.isAjax(request)) {
            Result result = Result.error("您已经被踢出，请重新登录或联系管理员！");
            HTTPUtils.out(response, result);
        }else{
            WebUtils.issueRedirect(request, response, ShiroConstant.KICK_OUT_URL);
        }
        return false;
    }

    private String buildOnlineStatusKey(String sessionId){
        return String.format("%s%s", ShiroConstant.ONLINE_STATUS_TAG, sessionId);
    }

    public static void setJedisManager(JedisManager jedisManager) {
        KickoutFilter.jedisManager = jedisManager;
        System.out.println("setJedisManager");
    }

    public static void setSessionRepository(ShiroSessionRepository sessionRepository) {
        KickoutFilter.sessionRepository = sessionRepository;
        System.out.println("setSessionRepository");
    }
}
