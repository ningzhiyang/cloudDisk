package com.imnu.cloudDisk.controller.sysManager;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnu.cloudDisk.entity.SysMenu;
import com.imnu.cloudDisk.entity.SysRole;
import com.imnu.cloudDisk.entity.SysUser;
import com.imnu.cloudDisk.service.SysMenuService;
import com.imnu.cloudDisk.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/28 <br>
 */
@Controller
@Slf4j
@RequestMapping("/bg/role_manager")
public class RoleManagerController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService menuService;

    @RequestMapping("/role_manager")
    public String roleManager(){
        return "sysManage/roleManager";
    }

    @ResponseBody
    @RequestMapping("/role_manager_json")
    public JSONObject role_manager_json(int page, int limit){
        JSONObject json = new JSONObject();
        PageHelper.startPage(page,limit);
        List<SysRole> list=sysRoleService.findAllRole();
        List<SysRole> sysRoles = new ArrayList<>();
        for (SysRole sysRole : list) {
            StringBuilder builder = new StringBuilder();
            if (sysRole.getRGroup() != null){
                for (String s : sysRole.getRGroup().split(",")) {
                    SysMenu menu = menuService.findMenuByMid(s);
                    if (menu != null){
                        builder.append(menu.getMName()).append(",");
                    }
                }
            }
            sysRole.setSysMenuList(builder.toString());
            sysRoles.add(sysRole);
        }
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoles);
        json.put("msg","");
        json.put("code","");
        json.put("data",pageInfo.getList());
        json.put("count",pageInfo.getSize());
        return json;
    }

    @RequestMapping("/insert")
    public String insert(){
        return "sysManage/roleManager_insert";
    }

    @ResponseBody
    @RequestMapping("/to_insert")
    public JSONObject to_insert(SysRole sysRole){
        JSONObject json = new JSONObject();
        int res = sysRoleService.insertSysRole(sysRole);
        if (res == 1){
            json.put("status","success");
            return json;
        }else {
            json.put("status","fail");
            return json;
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public JSONObject delete(Integer id){
        JSONObject json = new JSONObject();
        int res = sysRoleService.deleteysRoleById(id);
        if (res == 1){
            json.put("status","success");
            return json;
        }else {
            json.put("status","fail");
            return json;
        }
    }

    @RequestMapping("/update")
    public String update(Integer id, Model model){
        SysRole sysRole = sysRoleService.getSysRoleById(id);
        model.addAttribute("sysRole",sysRole);
        return "sysManage/roleManager_update";
    }

    @ResponseBody
    @RequestMapping("/to_update")
    public JSONObject to_update(SysRole sysRole){
        JSONObject json = new JSONObject();
        int res = sysRoleService.updateSysRoleById(sysRole);
        if (res == 1){
            json.put("status","success");
            return json;
        }else {
            json.put("status","fail");
            return json;
        }
    }

    @RequestMapping("/key")
    public String key(Integer id, Model model) {
        SysRole sysRole = sysRoleService.getSysRoleById(id);
        List<SysMenu> sysMenus = new ArrayList<>();
        if (sysRole !=null){
            for (String s : sysRole.getRGroup().split(",")) {
                SysMenu menu = menuService.findMenuByMid(s);
                if (menu != null){
                    sysMenus.add(menu);
                }
            }
            List<SysMenu> allmenu = menuService.findAllMenu();
            model.addAttribute("rid",id);
            model.addAttribute("sysMenus",sysMenus);
            model.addAttribute("allmenu",allmenu);
        }
        return "sysManage/roleManager_key";
    }

    @ResponseBody
    @RequestMapping("/to_key")
    public JSONObject to_key(SysRole sysRole) {
        JSONObject json = new JSONObject();
        int res = sysRoleService.updateSysRoleGroupById(sysRole);
        if (res == 1){
            json.put("status","success");
            return json;
        }else {
            json.put("status","fail");
            return json;
        }
    }
}
