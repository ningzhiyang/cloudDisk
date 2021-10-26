package com.imnu.cloudDisk.service;

import com.imnu.cloudDisk.entity.SysMenu;

import java.util.List;

public interface SysMenuService {
    SysMenu findMenuByMid(String mId);

    List<SysMenu> findAllMenu();

    int insertSysMenu(SysMenu sysMenu);

    int deleteSysMenuById(Integer id);

    SysMenu getSysMenuById(Integer id);

    int updateSysMenuByUid(SysMenu sysMenu);

    void updateStatusById(Integer id, Integer u_status);

    List<SysMenu> getMenuByName(String name);

    List<SysMenu> getSysMenuByValue(int a);
}
