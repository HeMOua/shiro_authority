package com.hemou.core.shiro.session.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.core.shiro.ShiroConstant;
import com.hemou.core.shiro.cache.JedisManager;
import com.hemou.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

public class JedisShiroSessionRepository implements ShiroSessionRepository {

    private JedisManager jedisManager;

    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null) throw new NullPointerException("session is empty");
        try {
            // 序列化session key
            byte[] key = ObjectUtil.serialize(buildRedisSessionKey(session.getId()));
            // 如果不存在状态就设置
            if(null == session.getAttribute(ShiroConstant.ONLINE_STATUS_KEY)){
                session.setAttribute(ShiroConstant.ONLINE_STATUS_KEY, true);
            }
            // 序列化session value
            byte[] value = ObjectUtil.serialize(session);
            getJedisManager().setex(ShiroConstant.DB_INDEX, key, value, (int) (session.getTimeout() / 1000));
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.error(getClass(), "save session error，id:[%s]",session.getId());
        }
    }

    @Override
    public void deleteSession(Serializable id) {
        if (id == null) throw new NullPointerException("session id is empty");
        try {
            getJedisManager().del(ShiroConstant.DB_INDEX, ObjectUtil.serialize(buildRedisSessionKey(id)));
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.error(getClass(), "删除session出现异常，id:[%s]",id);
        }
    }

    @Override
    public Session getSession(Serializable id) {
        if (id == null) throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            byte[] value = getJedisManager().get(ShiroConstant.DB_INDEX,
                    ObjectUtil.serialize(buildRedisSessionKey(id)));
            session = ObjectUtil.deserialize(value);
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.error(getClass(), "获取session异常，id:[%s]",id);
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
        Collection<Session> sessions = null;
        try {
            sessions = getJedisManager().getAllSession(ShiroConstant.DB_INDEX, ShiroConstant.SHIRO_SESSION_TAG);
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.error(getClass(), "获取全部session异常");
        }
        return sessions;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return ShiroConstant.SHIRO_SESSION_TAG + sessionId;
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
