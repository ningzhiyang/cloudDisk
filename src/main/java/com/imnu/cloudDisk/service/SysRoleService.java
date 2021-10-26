package com.imnu.cloudDisk.service;

import com.imnu.cloudDisk.entity.SysRole;

import java.util.List;

public interface SysRoleService {
    SysRole findRoleByRid(Integer rId);

    List<SysRole> findAllRole();

    int insertSysRole(SysRole sysRole);

    int deleteysRoleById(Integer id);

    SysRole getSysRoleById(Integer id);

    int updateSysRoleById(SysRole sysRole);

    int updateSysRoleGroupById(SysRole sysRole);
}
