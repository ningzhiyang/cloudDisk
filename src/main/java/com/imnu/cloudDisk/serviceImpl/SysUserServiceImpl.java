package com.imnu.cloudDisk.serviceImpl;

import com.imnu.cloudDisk.entity.SysUser;
import com.imnu.cloudDisk.mapper.SysUserMapper;
import com.imnu.cloudDisk.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser getUserByUsername(String username) {
        return sysUserMapper.getUserByUsername(username);
    }

    @Override
    public void updateSysUser(SysUser user) {
        sysUserMapper.updateSysUser(user);
    }

    @Override
    public List<SysUser> findAllUser() {
        return sysUserMapper.findAllUser();
    }

    @Override
    public Integer countAllUser() {
        return sysUserMapper.countAllUser();
    }

    @Override
    public int insertSysUser(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    @Override
    public int deleteSysUserByUid(Integer uid) {
        return sysUserMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int updateSysUserByUid(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKey(sysUser);
    }

    @Override
    public SysUser getSysUserByUid(Integer id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateStatusByUid(Integer uid, Integer u_status) {
        sysUserMapper.updateStatusByUid(uid,u_status);
    }

    @Override
    public List<SysUser> findSysUserByUserName(String name) {
        return sysUserMapper.findSysUserByUserName(name);
    }

    @Override
    public List<SysUser> findAllByName(String name) {
        return sysUserMapper.findAllByName(name);
    }
}
