package com.imnu.cloudDisk.controller;

import com.alibaba.fastjson.JSONObject;
import com.imnu.cloudDisk.entity.SysMenu;
import com.imnu.cloudDisk.entity.SysUser;
import com.imnu.cloudDisk.service.SysUserService;
import com.imnu.cloudDisk.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private UserUtils userUtils;
    @Autowired
    private MakeTreeUtil makeTreeUtil;
    @Autowired
    private SysUserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        SysUser sysUser = userUtils.getUser();
        if (sysUser != null){
            sysUser.setUIp(GetIp.getIp(request));
            sysUser.setULoginTime(TimeUtil.getCurrentTime());
            userService.updateSysUser(sysUser);
            Map<String, List<SysMenu>> makeTree = makeTreeUtil.makeTree(sysUser.getRId());
            request.getSession().setAttribute("sysUser", sysUser);
            model.addAttribute("one", makeTree.get("one"));
            model.addAttribute("two", makeTree.get("two"));
            return "index";
        }else {
            return "redirect:/login";
        }
    }

    @ResponseBody
    @RequestMapping("/upload")
    public JSONObject upload(@RequestParam("image") MultipartFile fileUpload, HttpServletRequest request) throws FileNotFoundException {
        JSONObject json = new JSONObject();
        //????????????????????????  ????????????????????????static/pic????????????
        String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/pic";
//        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\pic";
        //?????????????????????
        String fileName = fileUpload.getOriginalFilename();
        //?????????????????????
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //?????????????????????  ????????????????????????????????????
        String fileNames = UUID.randomUUID() + suffixName;
        try {
            //???????????????????????????
            FileUtil.uploadFile(fileUpload.getBytes(), filePath, fileNames);
            //??????????????????????????????ajax
            json.put("name", fileNames);
            return json;
        } catch (Exception e) {
            return json;
        }
    }
}
