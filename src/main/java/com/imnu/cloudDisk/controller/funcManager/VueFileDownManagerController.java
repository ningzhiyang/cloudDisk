package com.imnu.cloudDisk.controller.funcManager;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imnu.cloudDisk.entity.DownRecord;
import com.imnu.cloudDisk.service.DownRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/8/6 <br>
 */
@Controller
@Slf4j
@RequestMapping("/bg/vue_user_down_manager")
public class VueFileDownManagerController {

    @Autowired
    private DownRecordService downRecordService;

    @RequestMapping("/vue_user_down_manager")
    public String userManager() {
        return "funcManage/vueUserDownManager";
    }

    @ResponseBody
    @RequestMapping("/vue_user_down_manager_json")
    public JSONObject user_manager_json(int page, int limit) {
        JSONObject json = new JSONObject();
        PageHelper.startPage(page, limit);
        List<DownRecord> list = downRecordService.findAllRecord();
        PageInfo<DownRecord> pageInfo = new PageInfo<>(list);
        json.put("msg", "");
        json.put("code", "");
        json.put("data", pageInfo.getList());
        json.put("count", pageInfo.getSize());
        return json;
    }
}
