package com.hemou.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hemou.common.controller.BaseController;
import com.hemou.common.model.UUser;
import com.hemou.common.service.UUserService;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Page;
import com.hemou.common.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("member")
public class MemberController extends BaseController {

    @Resource
    private UUserService userService;

    @GetMapping("list")
    public String list(@RequestParam(value = "start", defaultValue = "0") Integer start,
                       @RequestParam(value = "count", defaultValue = "5") Integer count,
                       String search, Model model){
        PageHelper.offsetPage(start, count);
        List<UUser> users = userService.selectBySearch(search);
        int total = (int) new PageInfo<>(users).getTotal();
        Page page = new Page(start, count, total);

        model.addAttribute("page", page);
        model.addAttribute("users", users);
        model.addAttribute("search", search);
        return "member/list";
    }

    @ResponseBody
    @PostMapping("forbidUserById")
    public Object forbidUserById(Long id, Long status){
        UUser user = userService.selectByPrimaryKey(id);
        if(null == user){
            return Result.error("用户不存在");
        }
        if(!UUser.INVALID.equals(status) && !UUser.VALID.equals(status)){
            return Result.error("状态错误！");
        }
        user.setStatus(status);
        int update = userService.updateByPrimaryKeySelective(user);
        LoggerUtils.info(getClass(), "修改id为 [%s] 的用户，使其状态为 [%s], %s", id, status, update == 1?"成功":"失败");
        if(update == 1){
            if(UUser.INVALID.equals(status)){
                return Result.success("禁用成功！");
            }else{
                return Result.success("激活成功！");
            }
        }else{
            return Result.error("修改状态失败！");
        }
    }

    @ResponseBody
    @PostMapping("deleteUserById")
    public Object deleteUserById(Long id){
        int delete = userService.deleteByPrimaryKey(id);
        LoggerUtils.info(getClass(), "删除id为 [%s] 的用户，%s", id, delete == 1 ?"成功":"失败");
        if(delete == 1){
            return Result.success("删除成功！");
        }else{
            return Result.error("删除失败！");
        }
    }
}
