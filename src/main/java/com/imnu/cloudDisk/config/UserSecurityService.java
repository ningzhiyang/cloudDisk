package com.imnu.cloudDisk.config;

import com.imnu.cloudDisk.entity.SysMenu;
import com.imnu.cloudDisk.entity.SysRole;
import com.imnu.cloudDisk.entity.SysUser;
import com.imnu.cloudDisk.service.SysMenuService;
import com.imnu.cloudDisk.service.SysRoleService;
import com.imnu.cloudDisk.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = userService.getUserByUsername(username);
        if(sysUser == null){
            throw new BadCredentialsException("当前帐号认证失败，请重新输入账户和密码");
        }
        SysRole sysRole = sysRoleService.findRoleByRid(sysUser.getRId());
        String[] sysRoles = sysRole.getRGroup().split(",");
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String mId : sysRoles) {
            SysMenu sysMenu = sysMenuService.findMenuByMid(mId);
            if (sysMenu != null && sysMenu.getMUrl() != null){
                log.error(String.valueOf(sysMenu.getMUrl()));
                grantedAuthorities.add(new SimpleGrantedAuthority(sysMenu.getMUrl()));
            }
        }
        sysUser.setAuthorities(grantedAuthorities);
        return sysUser;
    }
}
