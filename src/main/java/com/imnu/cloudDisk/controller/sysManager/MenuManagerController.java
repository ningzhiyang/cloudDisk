package com.imnu.cloudDisk.controller.sysManager;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnu.cloudDisk.entity.SysMenu;
import com.imnu.cloudDisk.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/28 <br>
 */
@Controller
@Slf4j
@RequestMapping("/bg/menu_manager")
public class MenuManagerController {
    @Autowired
    private SysMenuService menuService;

    @RequestMapping("/menu_manager")
    public String userManager(){
        return "sysManage/menuManager";
    }

    @ResponseBody
    @RequestMapping("/menu_manager_json")
    public JSONObject user_manager_json(int page, int limit){
        PageHelper.startPage(page,limit);
        JSONObject json = new JSONObject();
        List<SysMenu> list=menuService.findAllMenu();
        PageInfo<SysMenu> pageInfo = new PageInfo<>(list);
        json.put("msg","");
        json.put("code","");
        json.put("data",pageInfo.getList());
        json.put("count",pageInfo.getTotal());
        return json;
    }

    @RequestMapping("/insert")
    public String insert(){
        return "sysManage/menuManager_insert";
    }

    @ResponseBody
    @RequestMapping("/to_insert")
    public JSONObject to_insert(SysMenu sysMenu, Integer lastMenu){
        JSONObject json = new JSONObject();
        if (sysMenu.getMFirstMenuId() == 2){
            sysMenu.setMSecondMenuId(1);
            sysMenu.setMThirdMenuId(lastMenu);
        }else if (sysMenu.getMFirstMenuId() == 1){
            sysMenu.setMSecondMenuId(0);
            sysMenu.setMThirdMenuId(0);
        }else if (sysMenu.getMFirstMenuId() == 3){
            sysMenu.setMSecondMenuId(0);
            sysMenu.setMThirdMenuId(lastMenu);
        }
        sysMenu.setMStatus(true);
        log.info(String.valueOf(sysMenu));
        int res = menuService.insertSysMenu(sysMenu);
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
        int res = menuService.deleteSysMenuById(id);
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
        SysMenu sysMenu = menuService.getSysMenuById(id);
        model.addAttribute("sysMenu",sysMenu);
        return "sysManage/menuManager_update";
    }

    @ResponseBody
    @RequestMapping("/to_update")
    public JSONObject to_update(SysMenu sysMenu, Integer lastMenu){
        JSONObject json = new JSONObject();
        if (sysMenu.getMFirstMenuId() == 2){
            sysMenu.setMSecondMenuId(1);
            sysMenu.setMThirdMenuId(lastMenu);
        }else if (sysMenu.getMFirstMenuId() == 1){
            sysMenu.setMSecondMenuId(0);
            sysMenu.setMThirdMenuId(0);
        }else if (sysMenu.getMFirstMenuId() == 3){
            sysMenu.setMSecondMenuId(0);
            sysMenu.setMThirdMenuId(lastMenu);
        }
        int res = menuService.updateSysMenuByUid(sysMenu);
        if (res == 1){
            json.put("status","success");
            return json;
        }else {
            json.put("status","fail");
            return json;
        }
    }

    @ResponseBody
    @RequestMapping("/switch")
    public String switch_status(Integer id,Integer u_status) {
        menuService.updateStatusById(id,u_status);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/findListInfo")
    public JSONObject findListInfo(String name,int page,int limit) {
        JSONObject json = new JSONObject();
        PageHelper.startPage(page,limit);
        List<SysMenu> sysMenus =menuService.getMenuByName(name);
        PageInfo pageInfo = new PageInfo(sysMenus);
        json.put("msg","");
        json.put("code","");
        json.put("data",pageInfo.getList());
        json.put("count",pageInfo.getSize());
        return json;
    }

    @ResponseBody
    @RequestMapping("/getMenuList")
    public JSONObject getMenuList(Integer value) {
        JSONObject json = new JSONObject();
        List<SysMenu> sysMenus = menuService.getSysMenuByValue(value);
        json.put("menus",sysMenus);
        json.put("status","success");
        return json;
    }
}
