package com.imnu.cloudDisk.controller.sysManager;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnu.cloudDisk.entity.SysUser;
import com.imnu.cloudDisk.service.SysUserService;
import com.imnu.cloudDisk.util.MD5Util;
import com.imnu.cloudDisk.util.UserUtils;
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
 * @CreateDate 2021/7/26 <br>
 */
@Controller
@Slf4j
@RequestMapping("/bg/user_manager")
public class UserManagerController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private UserUtils userUtils;

    @RequestMapping("/user_manager")
    public String userManager(){
        return "sysManage/userManager";
    }

    @ResponseBody
    @RequestMapping("/user_manager_json")
    public JSONObject user_manager_json(int page,int limit){
        JSONObject json = new JSONObject();
        PageHelper.startPage(page,limit);
        List<SysUser> list=userService.findAllUser();
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        json.put("msg","");
        json.put("code","");
        json.put("data",pageInfo.getList());
        json.put("count",pageInfo.getSize());
        return json;
    }

    @RequestMapping("/insert")
    public String insert(){
        return "sysManage/userManager_insert";
    }

    @ResponseBody
    @RequestMapping("/to_insert")
    public JSONObject to_insert(SysUser sysUser){
        JSONObject json = new JSONObject();
        sysUser.setUPassword(MD5Util.encode(sysUser.getUPassword()));
        int res = userService.insertSysUser(sysUser);
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
        int res = userService.deleteSysUserByUid(id);
        if (res == 1){
            json.put("status","success");
            return json;
        }else {
            json.put("status","fail");
            return json;
        }
    }

    @RequestMapping("/update")
    public String update(Integer id,Model model){
        SysUser sysUser = userService.getSysUserByUid(id);
        model.addAttribute("sysUser",sysUser);
        return "sysManage/userManager_update";
    }

    @ResponseBody
    @RequestMapping("/to_update")
    public JSONObject to_update(SysUser sysUser){
        JSONObject json = new JSONObject();
        sysUser.setUPassword(MD5Util.encode(sysUser.getUPassword()));
        int res = userService.updateSysUserByUid(sysUser);
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
        userService.updateStatusByUid(id,u_status);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/findListInfo")
    public JSONObject findListInfo(String name,int page,int limit) {
        JSONObject json = new JSONObject();
        PageHelper.startPage(page,limit);
        List<SysUser> sysUsers = userService.findAllByName(name);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);
        json.put("msg","");
        json.put("code","");
        json.put("data",pageInfo.getList());
        json.put("count",pageInfo.getSize());
        return json;
    }
}
