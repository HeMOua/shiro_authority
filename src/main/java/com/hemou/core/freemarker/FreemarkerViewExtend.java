package com.hemou.core.freemarker;

import com.hemou.core.shiro.token.TokenManager;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class FreemarkerViewExtend extends FreeMarkerView {

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);
        if(TokenManager.isLogin())model.put("token", TokenManager.getUser());
        model.put("basePath", request.getContextPath());
        model.put("_v", System.currentTimeMillis());
    }

    
}
