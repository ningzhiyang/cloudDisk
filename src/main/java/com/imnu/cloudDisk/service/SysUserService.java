package com.imnu.cloudDisk.service;

import com.imnu.cloudDisk.entity.SysUser;

import java.util.List;

public interface SysUserService {
    SysUser getUserByUsername(String username);

    void updateSysUser(SysUser user);

    List<SysUser> findAllUser();

    Integer countAllUser();

    int insertSysUser(SysUser sysUser);

    int deleteSysUserByUid(Integer uid);

    int updateSysUserByUid(SysUser sysUser);

    SysUser getSysUserByUid(Integer id);

    void updateStatusByUid(Integer uid, Integer u_status);

    List<SysUser> findSysUserByUserName(String name);

    List<SysUser> findAllByName(String name);
}
