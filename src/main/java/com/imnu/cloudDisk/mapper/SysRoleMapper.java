package com.imnu.cloudDisk.mapper;

import com.imnu.cloudDisk.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer rId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer rId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    SysRole findRoleByRid(Integer rId);

    List<SysRole> findAllRole();

    int updateSysRoleGroupById(SysRole sysRole);
}