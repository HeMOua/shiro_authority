package com.hemou.user.controller;

import com.hemou.common.controller.BaseController;
import com.hemou.common.model.UUser;
import com.hemou.common.service.UUserService;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Result;
import com.hemou.common.utils.TextUtils;
import com.hemou.common.utils.UserUtils;
import com.hemou.core.shiro.token.TokenManager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UUserService userService;

    @PostMapping("update")
    public Object update(UUser entity){
        String nickname = entity.getNickname();
        if(StringUtils.isEmpty(nickname)) return Result.warning("昵称不可为空！");

        int update = userService.updateByPrimaryKeySelective(entity);
        LoggerUtils.info(getClass(), "修改用户 [%s] %s", entity, update==1 ? "成功！" : "失败");
        if(update == 1){
            UUser user = userService.selectByPrimaryKey(entity.getId());
            TokenManager.setUser(user);
            return Result.success("修改成功！");
        }else{
            return Result.error("修改失败！");
        }
    }

    @PostMapping("updatePswd")
    public Object updatePswd(String password, String newPassword, String reNewPassword){
        // 有效一致性检测
        if(TextUtils.isExistEmpty(password, newPassword, reNewPassword)) return Result.warning("请完成填写信息！");
        if(!newPassword.equals(reNewPassword)) return Result.warning("两次密码不一致");

        password = UserUtils.md5Password(password);
        newPassword = UserUtils.md5Password(newPassword);

        // 正确性检测
        UUser user = TokenManager.getUser();
        if(!user.getPswd().equals(password)) return Result.warning("原密码不正确！");
        if(user.getPswd().equals(newPassword)) return Result.warning("新老密码不能一样，请重新修改！");

        // 修改
        UUser entity = new UUser();
        entity.setPswd(newPassword);
        entity.setId(user.getId());
        int update = userService.updateByPrimaryKeySelective(entity);
        if(update == 1){
            TokenManager.logout();
            return Result.success("修改成功，请重新登录！");
        }else{
            return Result.error("修改失败！");
        }
    }
}
