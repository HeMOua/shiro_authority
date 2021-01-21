package com.hemou.core.shiro.session;

import com.hemou.common.model.UUser;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Result;
import com.hemou.core.shiro.ShiroConstant;
import com.hemou.user.bo.UserOnlineBo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.util.*;

public class CustomSessionManager {

    private ShiroSessionRepository shiroSessionRepository;
    private CustomShiroSessionDAO customShiroSessionDAO;

    /**
     * 获取所有的有效Session用户
     *
     * @return
     */
    public List<UserOnlineBo> getAllUserOnline() {
        //获取所有session
        Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
        List<UserOnlineBo> list = new ArrayList<>();
        for (Session session : sessions) {
            UserOnlineBo bo = getSessionBo(session);
            if (null != bo) {
                list.add(bo);
            }
        }
        return list;
    }

    /**
     * 根据ID查询 SimplePrincipalCollection
     *
     * @param userIds 用户ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Long... userIds) {
        //把userIds 转成Set，好判断
        HashSet<Long> idSet = new HashSet<>();
        idSet.addAll(Arrays.asList(userIds));
        //获取所有session
        Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session : sessions) {
            //获取SimplePrincipalCollection
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (null != obj && obj instanceof SimplePrincipalCollection) {
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if (null != obj && obj instanceof UUser) {
                    UUser user = (UUser) obj;
                    //比较用户ID，符合即加入集合
                    if (idSet.contains(user.getId())) {
                        list.add(spc);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取单个Session
     *
     * @param sessionId
     * @return
     */
    public UserOnlineBo getUserOnlineBo(String sessionId) {
        Session session = shiroSessionRepository.getSession(sessionId);
        return getSessionBo(session);
    }

    private UserOnlineBo getSessionBo(Session session) {
        //获取session登录信息。
        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (null == obj) {
            return null;
        }
        //确保是 SimplePrincipalCollection对象。
        if (obj instanceof SimplePrincipalCollection) {
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            obj = spc.getPrimaryPrincipal();
            if (null != obj && obj instanceof UUser) {
                //存储session + user 综合信息
                UserOnlineBo userBo = new UserOnlineBo((UUser) obj);
                //最后一次和系统交互的时间
                userBo.setLastAccess(session.getLastAccessTime());
                //主机的ip地址
                userBo.setHost(session.getHost());
                //session ID
                userBo.setSessionId(session.getId().toString());
                //session最后一次与系统交互的时间
                userBo.setLastLoginTime(session.getLastAccessTime());
                //回话到期 ttl(ms)
                userBo.setTimeout(session.getTimeout());
                //session创建时间
                userBo.setStartTime(session.getStartTimestamp());
                //是否踢出
                Boolean onlineStatus = (Boolean) session.getAttribute(ShiroConstant.ONLINE_STATUS_KEY);
                userBo.setSessionStatus(null == onlineStatus ? true : onlineStatus);
                return userBo;
            }
        }
        return null;
    }

    /**
     * 改变Session状态
     *
     * @param status     {true:踢出,false:激活}
     * @param sessionIds
     * @return
     */
    public Result changeSessionStatus(boolean status, String sessionIds) {
        try {
            Session session = shiroSessionRepository.getSession(sessionIds);
            session.setAttribute(ShiroConstant.ONLINE_STATUS_KEY, status);
            customShiroSessionDAO.update(session);
            String msg = "成功" + (status ? "激活" : "踢掉") + "用户！";
            return Result.success(msg);
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.error(getClass(), "改变Session状态错误，sessionId[%s]", sessionIds);
            return Result.error("改变失败，有可能Session不存在，请刷新再试！");
        }
    }

    public void setShiroSessionRepository(
            ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    public void setCustomShiroSessionDAO(CustomShiroSessionDAO customShiroSessionDAO) {
        this.customShiroSessionDAO = customShiroSessionDAO;
    }
}
