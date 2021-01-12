package com.hemou.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hemou.common.constant.Constant;
import com.hemou.common.controller.BaseController;
import com.hemou.common.model.UUser;
import com.hemou.common.service.UUserService;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Page;
import com.hemou.common.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("member")
public class MemberController extends BaseController {

    @Resource
    private UUserService userService;

    /**
     * 列出所有用户
     * @param start
     * @param count
     * @param search
     * @param model
     * @return
     */
    @GetMapping("list")
    public String list(@RequestParam(value = "start", defaultValue = "0") Integer start,
                       @RequestParam(value = "count", defaultValue = Constant.PAGE_SIZE) Integer count,
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

    /**
     * 根据id设置用户有效状态
     * @param id
     * @param status
     * @return
     */
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

    /**
     * 根据id删除用户
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("deleteUserById")
    public Object deleteUserById(String ids){
        try {
            if(StringUtils.isEmpty(ids)) return Result.error("请选择删除人员！");

            int delete = userService.deleteByPrimaryKey(ids);
            LoggerUtils.info(getClass(), "删除%s名用户", delete);
            if(delete != 0){
                return Result.success("删除成功！");
            }else{
                return Result.error("删除失败！");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
}
