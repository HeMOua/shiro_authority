package com.hemou.user.controller;

import com.hemou.common.controller.BaseController;
import com.hemou.common.model.UUser;
import com.hemou.common.service.UUserService;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Result;
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
}
