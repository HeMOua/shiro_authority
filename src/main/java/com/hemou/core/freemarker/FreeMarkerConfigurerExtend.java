package com.hemou.core.freemarker;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

public class FreeMarkerConfigurerExtend extends FreeMarkerConfigurer {

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        Configuration cfg = this.getConfiguration();
        // 添加shiro标签
        cfg.setSharedVariable("shiro", new ShiroTags());
    }
}
