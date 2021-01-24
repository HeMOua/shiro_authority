package com.hemou.core.shiro.cache;

import cn.hutool.core.util.ObjectUtil;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.core.shiro.ShiroConstant;
import org.apache.shiro.session.Session;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JedisManager {

    private JedisPool jedisPool;

    public Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis;
        } catch (JedisConnectionException e) {
            LoggerUtils.error(getClass(), "尚未启动redis，系统自动退出");
            System.exit(0);
            throw new JedisConnectionException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取键值
     * @param dbIndex
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] get(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        byte[] result = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            result = jedis.get(key);
            return result;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 删除键
     * @param dbIndex
     * @param key
     * @throws Exception
     */
    public void del(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.del(key);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 设置值和到期时间
     * @param dbIndex
     * @param key
     * @param value
     * @param expireTime 如果 expireTime > 0 才设置
     * @throws Exception
     */
    public void setex(int dbIndex, byte[] key, byte[] value, int expireTime) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.set(key, value);
            if (expireTime > 0) jedis.expire(key, expireTime);
        } finally {
            returnResource(jedis);
        }
    }

    public Collection<Session> getAllSession(int dbIndex, String redisShiroSession) throws Exception {
        Jedis jedis = null;
        Set<Session> sessions = new HashSet<Session>();
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            Set<byte[]> byteKeys = jedis.keys((ShiroConstant.SHIRO_SESSION_ALL).getBytes());
            if (byteKeys != null && byteKeys.size() > 0) {
                for (byte[] bs : byteKeys) {
                    Session obj = ObjectUtil.deserialize(jedis.get(bs));
                    if(obj != null) sessions.add(obj);
                }
            }
            return sessions;
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 归还资源
     * @param jedis
     */
    public void returnResource(Jedis jedis) {
        if (jedis == null) return;
        jedis.close();
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}

