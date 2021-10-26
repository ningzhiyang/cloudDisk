package com.imnu.cloudDisk.mapper;

import com.imnu.cloudDisk.entity.VueUser;

import java.util.List;

public interface VueUserMapper {
    int deleteByPrimaryKey(Integer vId);

    int insert(VueUser record);

    int insertSelective(VueUser record);

    VueUser selectByPrimaryKey(Integer vId);

    int updateByPrimaryKeySelective(VueUser record);

    int updateByPrimaryKey(VueUser record);

    List<VueUser> findAllUser();

    void updateStatusById(Integer id, Integer u_status);

    List<VueUser> findAllByName(String name);

    VueUser login(String username, String password);

    Integer getVidByVueUserName(String username);

    int updateVuePwdByUserName(String username, String pwd);

    void updateIpByVueUser(VueUser vueUser1);

    VueUser getVueByUserName(String username);
}