package com.imnu.cloudDisk.serviceImpl;

import com.imnu.cloudDisk.entity.SysRole;
import com.imnu.cloudDisk.mapper.SysRoleMapper;
import com.imnu.cloudDisk.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysRole findRoleByRid(Integer rId) {
        return sysRoleMapper.findRoleByRid(rId);
    }

    @Override
    public List<SysRole> findAllRole() {
        return sysRoleMapper.findAllRole();
    }

    @Override
    public int insertSysRole(SysRole sysRole) {
        return sysRoleMapper.insert(sysRole);
    }

    @Override
    public int deleteysRoleById(Integer id) {
        return sysRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysRole getSysRoleById(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateSysRoleById(SysRole sysRole) {
        return sysRoleMapper.updateByPrimaryKey(sysRole);
    }

    @Override
    public int updateSysRoleGroupById(SysRole sysRole) {
        return sysRoleMapper.updateSysRoleGroupById(sysRole);
    }
}
