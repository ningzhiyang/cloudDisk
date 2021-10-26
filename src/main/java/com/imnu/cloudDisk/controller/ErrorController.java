package com.imnu.cloudDisk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/28 <br>
 */
@Controller
public class ErrorController {
    @RequestMapping(value = "/error400Page")
    public String error400Page() {
        return "error/400";
    }
    @RequestMapping(value = "/error401Page")
    public String error401Page() {
        return "error/401";
    }
    @RequestMapping(value = "/error403Page")
    public String error403Page() {
        return "error/403";
    }
    @RequestMapping(value = "/error404Page")
    public String error404Page() {
        return "error/404";
    }
    @RequestMapping(value = "/error500Page")
    public String error500Page() {
        return "error/500";
    }
}
