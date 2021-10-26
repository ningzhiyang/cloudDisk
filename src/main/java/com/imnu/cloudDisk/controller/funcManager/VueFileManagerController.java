package com.imnu.cloudDisk.controller.funcManager;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnu.cloudDisk.entity.VueFile;
import com.imnu.cloudDisk.hadoop.HadoopConfig;
import com.imnu.cloudDisk.service.VueFileService;
import com.imnu.cloudDisk.util.WordFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/8/6 <br>
 */
@Controller
@Slf4j
@RequestMapping("/bg/vue_file_manager")
public class VueFileManagerController {

    @Autowired
    private HadoopConfig hadoopConfig;
    @Autowired
    private VueFileService vueFileService;

    @RequestMapping("/vue_file_manager")
    public String userManager() {
        return "funcManage/vueFileManager";
    }

    @ResponseBody
    @RequestMapping("/vue_file_manager_json")
    public JSONObject user_manager_json(int page, int limit) {
        JSONObject json = new JSONObject();
        PageHelper.startPage(page, limit);
        List<VueFile> list = vueFileService.findAllFile();
        PageInfo<VueFile> pageInfo = new PageInfo<>(list);
        json.put("msg", "");
        json.put("code", "");
        json.put("data", pageInfo.getList());
        json.put("count", pageInfo.getSize());
        return json;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public JSONObject delete(Integer id) throws Exception {
        JSONObject json = new JSONObject();
        int res = vueFileService.deleteVueFileByid(id);
        VueFile vueFile = vueFileService.findFileByFid(id);
        String path = vueFile.getFPath() + vueFile.getFName();
        Boolean resa = hadoopConfig.deleteFile(path);
        json.put("status", "success");
        return json;

    }

    @ResponseBody
    @RequestMapping("/filtera")
    public JSONObject filtera() throws Exception {
        JSONObject json = new JSONObject();
        List<VueFile> vueFiles = vueFileService.findAllFile();
        for (VueFile vueFile : vueFiles) {
            String replace = WordFilter.replaceWords(vueFile.getFName());
            log.info(replace);
            VueFile vf = new VueFile();
            vf.setFId(vueFile.getFId());
            vf.setFName(replace);
            vueFileService.updateFnameById(vf);
        }
        json.put("status", "success");
        return json;
    }
}

