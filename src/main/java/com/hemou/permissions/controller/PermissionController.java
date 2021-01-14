package com.hemou.permissions.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hemou.common.constant.Constant;
import com.hemou.common.controller.BaseController;
import com.hemou.common.model.UPermission;
import com.hemou.common.model.URole;
import com.hemou.common.service.UPermissionService;
import com.hemou.common.service.URoleService;
import com.hemou.common.utils.LoggerUtils;
import com.hemou.common.utils.Page;
import com.hemou.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController {

    protected String prefix = "permission/";

    private final UPermissionService permissionService;

    private final URoleService roleService;

    @Autowired
    public PermissionController(UPermissionService permissionService, URoleService roleService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
    }

    /**
     * 查看所有权限
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
        List<UPermission> permissions = permissionService.selectBySearch(search);

        int total = (int) new PageInfo<>(permissions).getTotal();
        Page page = new Page(start, count);
        page.setTotal(total);

        model.addAttribute("search", search);
        model.addAttribute("list", permissions);
        model.addAttribute("page", page);
        return prefix + "index";
    }

    /**
     * 添加权限
     * @param permission
     * @return
     */
    @ResponseBody
    @PostMapping("addPermission")
    public Object addPermission(UPermission permission){
        int insert = permissionService.insert(permission);
        if(insert == 1) return Result.success("添加权限成功！");
        else return Result.error("添加权限失败！");
    }

    /**
     * 删除权限
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("deletePermission")
    public Object deletePermission(String ids){
        try {
            if(StringUtils.isEmpty(ids)) return Result.error("请选择删除角色！");

            int delete = permissionService.deleteById(ids);
            LoggerUtils.info(getClass(), "删除%s个权限", delete);
            if(delete != 0){
                return Result.success("删除成功！");
            }else{
                return Result.error("删除失败！");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("allocation")
    public String allocation(@RequestParam(value = "start", defaultValue = "0") Integer start,
                             @RequestParam(value = "count", defaultValue = Constant.PAGE_SIZE) Integer count,
                             String search, Model model){
        PageHelper.offsetPage(start, count);
        List<URole> roles = roleService.selectBySearch(search);
        int total = (int) new PageInfo<>(roles).getTotal();
        Page page = new Page(start, count, total);
        permissionService.fillByRole(roles);
        model.addAttribute("page", page);
        model.addAttribute("roles", roles);
        model.addAttribute("search", search);
        return prefix + "allocation";
    }

    @GetMapping("choosePermission")
    public String choosePermission(String rid, String pids, Model model){
        List<UPermission> permissions = permissionService.queryAllByLimit(0, Short.MAX_VALUE);
        model.addAttribute("permissions", permissions);
        model.addAttribute("pids", pids);
        model.addAttribute("rid", rid);
        return prefix + "choosePermission";
    }

    @ResponseBody
    @PostMapping("allocPermission")
    public Object allocPermission(Long rid, String pids){
        try {
            LoggerUtils.info(getClass(), "分配角色权限，rid=%s, pids=%s", rid, pids);
            roleService.allocPermission(rid, pids);
            return Result.success("分配成功");
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("cancelPermission")
    public Object cancelPermission(String ids){
        LoggerUtils.info(getClass(), "取消角色rid=%s所有权限", ids);
        roleService.cancelPermission(ids);
        return Result.success("操作成功");
    }

    @GetMapping("add")
    public String add(){
        return prefix + "add";
    }
}
