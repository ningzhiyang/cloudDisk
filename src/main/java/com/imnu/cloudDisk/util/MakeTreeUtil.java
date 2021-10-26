package com.imnu.cloudDisk.util;

import com.imnu.cloudDisk.entity.SysMenu;
import com.imnu.cloudDisk.entity.SysRole;
import com.imnu.cloudDisk.service.SysMenuService;
import com.imnu.cloudDisk.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/26 <br>
 */
@Component
@Slf4j
public class MakeTreeUtil {

    @Autowired
    private UserUtils userUtils;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysMenuService sysMenuService;

    public Map<String, List<SysMenu>> makeTree(Integer r_id) {
        SysRole sysRole = sysRoleService.findRoleByRid(r_id);
        String[] m_ids = sysRole.getRGroup().split(",");
        List<SysMenu> sysMenus = new ArrayList<>();
        for (String m_id : m_ids) {
            SysMenu sysMenu = sysMenuService.findMenuByMid(m_id);
            if (sysMenu != null){
                if (userUtils.hasRole(sysMenu.getMUrl())) {
                    sysMenus.add(sysMenuService.findMenuByMid(m_id));
                } else {
                    log.error("没有当前权限：" + sysMenu.getMUrl());
                }
            }
        }
        return makeTreeList(sysMenus);
    }

    private Map<String, List<SysMenu>> makeTreeList(List<SysMenu> sysMenus) {
        List<SysMenu> first = new ArrayList<>();
        List<SysMenu> second = new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getMFirstMenuId() == 1 && sysMenu.getMSecondMenuId() == 0 && sysMenu.getMThirdMenuId() == 0) {
                first.add(sysMenu);
            }
        }
        for (SysMenu menu : sysMenus) {
            for (SysMenu sysMenu : first) {
                if (menu.getMFirstMenuId() == 2 && menu.getMSecondMenuId() == 1 && menu.getMThirdMenuId().equals(sysMenu.getMId())) {
                    second.add(menu);
                }
            }
        }
        Map<String, List<SysMenu>> map = new HashMap<>();
        map.put("one",first);
        map.put("two",second);
        return map;
    }
}
