package com.hemou.user.controller;

import com.hemou.common.controller.BaseController;
import com.hemou.common.model.UUser;
import com.hemou.common.service.UUserService;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Result;
import com.hemou.core.shiro.token.SecurityManager;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("u")
@Controller
public class AccountController extends BaseController {

    @Autowired
    private UUserService userService;

    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    @GetMapping("register")
    public String toRegister(){
        return "user/register";
    }

    @ResponseBody
    @PostMapping("submitLogin")
    public Object submitLogin(boolean rememberMe, UUser user, HttpServletRequest request){
        try {
            SecurityManager.login(user, rememberMe);

            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url = "/url";
            if(request != null){
                url = savedRequest.getRequestUrl();
            }
            LoggerUtils.info(getClass(), "submitLogin：之前的访问路径 [%s]", url);
            return Result.success("登录成功！", url);
        } catch (DisabledAccountException e){
            return Result.error(e.getMessage());
        } catch (AccountException e){
            return Result.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return Result.error("密码错误！");
        }
    }

    @ResponseBody
    @PostMapping("submitRegister")
    public Object submitRegister(String verifyCode, UUser entity){
        if(!SecurityManager.isVCodeValid(verifyCode)) return Result.fail("验证码无效或有误！");

        // 清除验证码
        SecurityManager.clearVerifyCode();

        // 验证email是否存在
        String email = entity.getEmail();
        UUser user = userService.selectByEmail(email);
        if(user != null) return Result.fail("邮箱已存在请重新注册！");

        // 插入数据
        Date date = new Date();
        entity.setStatus(UUser.VALID);
        entity.setCreateTime(date);
        entity.setLastLoginTime(date);
        userService.insertSelective(entity);
        LoggerUtils.info(getClass(), "注册：成功插入数据 [%s]", entity);

        // 登录
        entity = SecurityManager.login(entity, true);
        LoggerUtils.info(getClass(), "[%s]登录成功！", entity);
        return Result.success("注册成功");
    }
}
