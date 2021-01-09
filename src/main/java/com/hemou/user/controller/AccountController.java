package com.hemou.user.controller;

import com.hemou.common.controller.BaseController;
import com.hemou.common.model.UUser;
import com.hemou.common.service.UUserService;
import com.hemou.common.utils.EncryptionUtil;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Result;
import com.hemou.core.shiro.token.TokenManager;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

    @GetMapping("login")
    public String toLogin(){
        return "common/login";
    }

    @GetMapping("register")
    public String toRegister(){
        return "common/register";
    }

    @ResponseBody
    @PostMapping("submitLogin")
    public Object submitLogin(boolean rememberMe, UUser entity, HttpServletRequest request){
        try {
            UUser user = TokenManager.login(entity, rememberMe);

            // 更新最后登录时间
            user.setLastLoginTime(new Date());
            userService.updateByPrimaryKeySelective(user);

            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url = null;
            if(null != savedRequest){
                url = savedRequest.getRequestUrl();
            }
            LoggerUtils.info(getClass(), "submitLogin：之前的访问路径 [%s]", url);
            if(StringUtils.isEmpty(url)) url = "user/index.shtml";
            return Result.success("登录成功！", url);
        } catch (UnknownAccountException e){
            return Result.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return Result.error("密码错误！");
        } catch (LockedAccountException e){
            return Result.error(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("submitRegister")
    public Object submitRegister(String verifyCode, UUser entity){
        if(!TokenManager.isVCodeValid(verifyCode)) return Result.warning("验证码无效或有误！");

        // 清除验证码
        TokenManager.clearVerifyCode();

        // 验证email是否存在
        String email = entity.getEmail();
        UUser user = userService.selectByEmail(email);
        if(user != null) return Result.warning("邮箱已存在请重新注册！");

        // 插入数据
        Date date = new Date();
        String pswd = entity.getPswd();
        String password = EncryptionUtil.encryptionPassword(pswd);
        entity.setPswd(password);
        entity.setStatus(UUser.VALID);
        entity.setCreateTime(date);
        entity.setLastLoginTime(date);
        userService.insertSelective(entity);
        LoggerUtils.info(getClass(), "注册：成功插入数据 [%s]", entity);

        // 登录
        entity.setPswd(pswd);
        entity = TokenManager.login(entity, true);
        LoggerUtils.info(getClass(), "[%s]注册成功！", entity);
        return Result.success("注册成功", "user/index.shtml");
    }

    @ResponseBody
    @GetMapping("logout")
    public Object logout(){
        try{
            TokenManager.logout();
            return Result.success("退出登录成功！");
        }catch (Exception e){
            LoggerUtils.info(getClass(), "退出时出错，%s", e.getMessage());
            return Result.error("操作异常！");
        }
    }
}
