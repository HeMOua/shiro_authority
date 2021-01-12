package com.hemou.permissions.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hemou.common.constant.Constant;
import com.hemou.common.controller.BaseController;
import com.hemou.common.model.URole;
import com.hemou.common.model.UUser;
import com.hemou.common.service.URoleService;
import com.hemou.common.service.UUserService;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Page;
import com.hemou.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (URole)表控制层
 *
 * @author hemou
 * @since 2021-01-12 18:46:14
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    protected String prefix = "role/";

    private final URoleService roleService;

    private final UUserService userService;

    @Autowired
    public RoleController(URoleService roleService, UUserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    /**
     * 查看所有角色
     * @param start
     * @param count
     * @param search
     * @param model
     * @return
     */
    @GetMapping("index")
    public String list(@RequestParam(value = "start", defaultValue = "0") Integer start,
                       @RequestParam(value = "count", defaultValue = Constant.PAGE_SIZE) Integer count,
                       String search, Model model){
        PageHelper.offsetPage(start, count);
        List<URole> roles = roleService.selectBySearch(search);

        int total = (int) new PageInfo<>(roles).getTotal();
        Page page = new Page(start, count);
        page.setTotal(total);

        model.addAttribute("search", search);
        model.addAttribute("roles", roles);
        model.addAttribute("page", page);
        return prefix + "index";
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @ResponseBody
    @PostMapping("addRole")
    public Object addRole(URole role){
        int insert = roleService.insert(role);
        if(insert == 1) return Result.success("添加角色成功！");
        else return Result.error("添加角色失败！");
    }

    /**
     * 删除角色
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("deleteRole")
    public Object deleteRole(String ids){
        try {
            if(StringUtils.isEmpty(ids)) return Result.error("请选择删除角色！");

            int delete = roleService.deleteById(ids);
            LoggerUtils.info(getClass(), "删除%s个角色", delete);
            if(delete != 0){
                return Result.success("删除成功！");
            }else{
                return Result.error("删除失败！");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 给用户分配角色
     * @param start
     * @param count
     * @param search
     * @param model
     * @return
     */
    @GetMapping("allocation")
    public String allocation(@RequestParam(value = "start", defaultValue = "0") Integer start,
                             @RequestParam(value = "count", defaultValue = Constant.PAGE_SIZE) Integer count,
                             String search, Model model){
        PageHelper.offsetPage(start, count);
        List<UUser> users = userService.selectBySearch(search);
        int total = (int) new PageInfo<>(users).getTotal();
        Page page = new Page(start, count, total);
        roleService.fillByUser(users);
        model.addAttribute("page", page);
        model.addAttribute("users", users);
        model.addAttribute("search", search);
        return prefix + "allocation";
    }

    @GetMapping("chooseRole")
    public String allocForUser(String rids, Model model){
        List<URole> roles = roleService.queryAllByLimit(0, Short.MAX_VALUE);
        model.addAttribute("roles", roles);
        model.addAttribute("rids", rids);
        return prefix + "chooseRole";
    }

    @GetMapping("add")
    public String add(){
        return prefix + "add";
    }
}