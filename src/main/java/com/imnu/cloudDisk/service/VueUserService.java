package com.imnu.cloudDisk.service;

import com.imnu.cloudDisk.entity.VueUser;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/29 <br>
 */
public interface VueUserService {
    List<VueUser> findAllUser();

    int insertUser(VueUser vueUser);

    int deleteVueUserByUid(Integer id);

    VueUser getVueUserById(Integer id);

    int updateVueUserById(VueUser vueUser);

    void updateStatusById(Integer id, Integer u_status);

    List<VueUser> findAllByName(String name);

    VueUser login(String username, String password);

    Integer getVidByVueUserName(String username);

    int updateVuePwdByUserName(String username, String pwd);

    void updateIpByVueUser(VueUser vueUser1);

    VueUser getVueByUserName(String username);
}
