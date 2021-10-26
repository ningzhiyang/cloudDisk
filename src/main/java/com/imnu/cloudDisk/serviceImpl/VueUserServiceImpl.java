package com.imnu.cloudDisk.serviceImpl;

import com.imnu.cloudDisk.entity.VueUser;
import com.imnu.cloudDisk.mapper.VueUserMapper;
import com.imnu.cloudDisk.service.VueUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/29 <br>
 */
@Service
public class VueUserServiceImpl implements VueUserService {

    @Resource
    private VueUserMapper vueUserMapper;

    @Override
    public List<VueUser> findAllUser() {
        return vueUserMapper.findAllUser();
    }

    @Override
    public int insertUser(VueUser vueUser) {
        return vueUserMapper.insert(vueUser);
    }

    @Override
    public int deleteVueUserByUid(Integer id) {
        return vueUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public VueUser getVueUserById(Integer id) {
        return vueUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateVueUserById(VueUser vueUser) {
        return vueUserMapper.updateByPrimaryKey(vueUser);
    }

    @Override
    public void updateStatusById(Integer id, Integer u_status) {
        vueUserMapper.updateStatusById(id,u_status);
    }

    @Override
    public List<VueUser> findAllByName(String name) {
        return vueUserMapper.findAllByName(name);
    }

    @Override
    public VueUser login(String username, String password) {
        return vueUserMapper.login(username,password);
    }

    @Override
    public Integer getVidByVueUserName(String username) {
        return vueUserMapper.getVidByVueUserName(username);
    }

    @Override
    public int updateVuePwdByUserName(String username, String pwd) {
        return vueUserMapper.updateVuePwdByUserName(username,pwd);
    }

    @Override
    public void updateIpByVueUser(VueUser vueUser1) {
        vueUserMapper.updateIpByVueUser(vueUser1);
    }

    @Override
    public VueUser getVueByUserName(String username) {
        return vueUserMapper.getVueByUserName(username);
    }
}
