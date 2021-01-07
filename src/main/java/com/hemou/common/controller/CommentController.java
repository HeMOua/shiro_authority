package com.hemou.common.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.core.shiro.token.SecurityManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CommentController {

    @GetMapping("getVerifyCode")
    public void getVerifyCode(HttpServletResponse response) throws IOException {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(120, 44, 4, 4);
        captcha.write(response.getOutputStream());
        String code = captcha.getCode();
        SecurityManager.setVerifyCode(code);
        LoggerUtils.info(getClass(), "验证码为：[%s]", code);
    }
}
