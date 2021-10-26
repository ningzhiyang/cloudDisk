package com.imnu.cloudDisk.mapper;

import com.imnu.cloudDisk.entity.SysUser;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer uId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer uId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser getUserByUsername(String username);

    void updateSysUser(SysUser user);

    List<SysUser> findAllUser();

    Integer countAllUser();

    void updateStatusByUid(Integer uid, Integer u_status);

    List<SysUser> findSysUserByUserName(String name);

    List<SysUser> findAllByName(String name);
}