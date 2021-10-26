package com.imnu.cloudDisk.mapper;

import com.imnu.cloudDisk.entity.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Integer mId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer mId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> findAllMenu();

    void updateStatusById(Integer id, Integer status);

    List<SysMenu> getMenuByName(String name);

    List<SysMenu> getSysMenuByValue(int mFirstMenuId);
}