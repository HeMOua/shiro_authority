package com.hemou.core.shiro.filter;

import com.hemou.common.utils.HTTPUtils;
import com.hemou.common.utils.Result;
import com.hemou.core.shiro.ShiroConstant;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class OnlineFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();

        Boolean isOnline = (Boolean) session.getAttribute(ShiroConstant.ONLINE_STATUS_KEY);
        if(null != isOnline && !isOnline){
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        subject.logout();
        saveRequest(request);
        if(HTTPUtils.isAjax(request)){
            response.setCharacterEncoding("UTF-8");
            Result error = Result.error("您已经被踢出，请重新登录或联系管理员！");
            error.setObj("reload");
            HTTPUtils.out(response, error);
        }else{
            WebUtils.issueRedirect(request, response, ShiroConstant.KICK_OUT_URL);
        }
        return false;
    }
}
