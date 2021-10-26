package com.imnu.cloudDisk.serviceImpl;

import com.imnu.cloudDisk.entity.SysMenu;
import com.imnu.cloudDisk.mapper.SysMenuMapper;
import com.imnu.cloudDisk.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Override
    public SysMenu findMenuByMid(String mId) {
        return sysMenuMapper.selectByPrimaryKey(Integer.parseInt(mId));
    }

    @Override
    public List<SysMenu> findAllMenu() {
        return sysMenuMapper.findAllMenu();
    }

    @Override
    public int insertSysMenu(SysMenu sysMenu) {
        return sysMenuMapper.insert(sysMenu);
    }

    @Override
    public int deleteSysMenuById(Integer id) {
        return sysMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysMenu getSysMenuById(Integer id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateSysMenuByUid(SysMenu sysMenu) {
        return sysMenuMapper.updateByPrimaryKey(sysMenu);
    }

    @Override
    public void updateStatusById(Integer id, Integer u_status) {
        sysMenuMapper.updateStatusById(id, u_status);
    }

    @Override
    public List<SysMenu> getMenuByName(String name) {
        return sysMenuMapper.getMenuByName(name);
    }

    @Override
    public List<SysMenu> getSysMenuByValue(int a) {
        return sysMenuMapper.getSysMenuByValue(a);
    }
}
