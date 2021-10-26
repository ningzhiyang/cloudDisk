package com.imnu.cloudDisk.controller.funcManager;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnu.cloudDisk.entity.VueUser;
import com.imnu.cloudDisk.hadoop.HadoopConfig;
import com.imnu.cloudDisk.service.VueUserService;
import com.imnu.cloudDisk.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/29 <br>
 */
@Controller
@Slf4j
@RequestMapping("/bg/vue_user_manager")
public class VueUserManagerController {

    @Resource
    private VueUserService vueUserService;
    @Resource
    private HadoopConfig hadoopConfig;

    @RequestMapping("/vue_user_manager")
    public String userManager(){
        return "funcManage/vueUserManager";
    }

    @ResponseBody
    @RequestMapping("/vue_user_manager_json")
    public JSONObject user_manager_json(int page, int limit){
        JSONObject json = new JSONObject();
        PageHelper.startPage(page,limit);
        List<VueUser> list=vueUserService.findAllUser();
        PageInfo<VueUser> pageInfo = new PageInfo<>(list);
        json.put("msg","");
        json.put("code","");
        json.put("data",pageInfo.getList());
        json.put("count",pageInfo.getSize());
        return json;
    }

    @RequestMapping("/insert")
    public String insert(){
        return "funcManage/vueUserManager_insert";
    }

    @ResponseBody
    @RequestMapping("/to_insert")
    public JSONObject to_insert(VueUser vueUser) throws IOException {
        JSONObject json = new JSONObject();
        vueUser.setVPwd(MD5Util.encode(vueUser.getVPwd()));
        int res = vueUserService.insertUser(vueUser);
        hadoopConfig.mkdir("/"+vueUser.getVAccount());
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
    public JSONObject delete(Integer id) throws Exception {
        JSONObject json = new JSONObject();
        VueUser vueUser = vueUserService.getVueUserById(id);
        hadoopConfig.delete("/"+vueUser.getVAccount());
        int res = vueUserService.deleteVueUserByUid(id);
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
        VueUser vueUser = vueUserService.getVueUserById(id);
        model.addAttribute("vueUser",vueUser);
        return "funcManage/vueUserManager_update";
    }

    @ResponseBody
    @RequestMapping("/to_update")
    public JSONObject to_update(VueUser vueUser){
        JSONObject json = new JSONObject();
        vueUser.setVPwd(MD5Util.encode(vueUser.getVPwd()));
        int res = vueUserService.updateVueUserById(vueUser);
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
        vueUserService.updateStatusById(id,u_status);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/findListInfo")
    public JSONObject findListInfo(String name,int page,int limit) {
        JSONObject json = new JSONObject();
        PageHelper.startPage(page,limit);
        List<VueUser> vueUsers = vueUserService.findAllByName(name);
        PageInfo<VueUser> pageInfo = new PageInfo<>(vueUsers);
        json.put("msg","");
        json.put("code","");
        json.put("data",pageInfo.getList());
        json.put("count",pageInfo.getSize());
        return json;
    }
}
