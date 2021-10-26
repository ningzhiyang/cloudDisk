package com.imnu.cloudDisk.controller.vueData;

import com.alibaba.fastjson.JSONObject;
import com.imnu.cloudDisk.entity.VueUser;
import com.imnu.cloudDisk.hadoop.HadoopConfig;
import com.imnu.cloudDisk.hadoop.HdfsConfig;
import com.imnu.cloudDisk.service.VueUserService;
import com.imnu.cloudDisk.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/29 <br>
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/vue")
public class VueLoginController {

    @Resource
    private VueUserService vueUserService;
    @Autowired
    private HadoopConfig hadoopConfig;

    @RequestMapping("/to_login")
    public JSONObject to_login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) throws IOException {
        log.info(username+"---->"+password);
        JSONObject json = new JSONObject();
        String md5pwd = MD5Util.encode(password);
        VueUser vueUser = vueUserService.login(username,md5pwd);
        if (vueUser != null){
            if (vueUser.getVStatus() == 1){
                Integer used = hadoopConfig.userdSpace(username);
                VueUser vueUser1 = new VueUser();
                vueUser1.setVId(vueUser.getVId());
                vueUser1.setVLoginIp(GetIp.getIp(request));
                vueUser1.setVLoginTime(TimeUtil.getCurrentTime());
                vueUserService.updateIpByVueUser(vueUser1);
                json.put("code",1);
                json.put("used",used);
                json.put("userimg",vueUser.getVImg());
            }else {
                json.put("code",-1);
            }
            return json;
        }else {
            json.put("code",0);
            return json;
        }

    }

    @RequestMapping("/correctPwd")
    public JSONObject correctPwd(@RequestParam("username") String username,@RequestParam("pwd") String pwd) throws IOException {
        log.info(username + "---->" + pwd);
        JSONObject json = new JSONObject();
        int res = vueUserService.updateVuePwdByUserName(username,MD5Util.encode(pwd));

        if (res == 1){
            json.put("code",1);

        }else {
            json.put("code",0);
        }
        return json;
    }

    @RequestMapping("/used")
    public JSONObject used(@RequestParam("username") String username) throws IOException {
        Integer used = hadoopConfig.userdSpace(username);
        JSONObject json = new JSONObject();
        json.put("used",used);
        return json;
    }

    @RequestMapping("/yzm")
    public JSONObject yzm(@RequestParam("phone") String phone) throws Exception {
        JSONObject json = new JSONObject();
        String checkcode = String.valueOf((int)((Math.random()*9+1)*100000));
        PhoneValidCodeUtil.yzm(checkcode,phone);
        json.put("checkcode",checkcode);
        json.put("code",0);
        return json;
    }

    @ResponseBody
    @RequestMapping("/upload")
    public JSONObject upload(@RequestParam("image") MultipartFile fileUpload, HttpServletRequest request) throws FileNotFoundException {
        JSONObject json = new JSONObject();
        //设置文件上传路径  获取到是服务里的static/pic目录路径
        String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/pic";
//        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\pic";
        //获取原始文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名  拼接新文件前缀名和后缀名
        String fileNames = UUID.randomUUID() + suffixName;
        try {
            //保存文件到指定目录
            FileUtil.uploadFile(fileUpload.getBytes(), filePath, fileNames);
            //返回的是新的文件名到ajax
            json.put("code",1);
            json.put("name", fileNames);
            return json;
        } catch (Exception e) {
            return json;
        }
    }

    @RequestMapping("/register")
    public JSONObject register(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("phone") String phone,@RequestParam("imgpath") String imgPath) throws IOException {
        log.info(username+"----->"+password+"----->"+phone+"----->"+imgPath);
        JSONObject json = new JSONObject();
        VueUser vueUser = vueUserService.getVueByUserName(username);
        if (vueUser == null){
            VueUser newUser = new VueUser();
            newUser.setVAccount(username);
            newUser.setVPwd(MD5Util.encode(password));
            newUser.setVPhone(phone);
            newUser.setVImg("/cloud/pic/"+imgPath);
            newUser.setVLoginTime(TimeUtil.getCurrentTime());
            vueUserService.insertUser(newUser);
            hadoopConfig.mkdir("/"+username);
            json.put("code",0);
        }else {
            json.put("code",1);
        }
        return json;
    }
}
