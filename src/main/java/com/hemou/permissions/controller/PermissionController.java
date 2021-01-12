package com.hemou.permissions.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hemou.common.constant.Constant;
import com.hemou.common.controller.BaseController;
import com.hemou.common.model.UPermission;
import com.hemou.common.service.UPermissionService;
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

    @Autowired
    public PermissionController(UPermissionService permissionService) {
        this.permissionService = permissionService;
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

    @GetMapping("add")
    public String add(){
        return prefix + "add";
    }
}
