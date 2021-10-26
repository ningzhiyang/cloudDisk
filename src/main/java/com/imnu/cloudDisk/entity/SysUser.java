package com.imnu.cloudDisk.entity;

import java.io.Serializable;
import java.util.Collection;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * sys_user
 * @author 
 */
@Data
public class SysUser implements UserDetails {
    private Integer uId;

    /**
     * 用户照片
     */
    private String uImg;

    /**
     * 用户账户
     */
    private String uAccount;

    /**
     * 用户密码
     */
    private String uPassword;

    /**
     * 用户登录的ip
     */
    private String uIp;

    /**
     * 用户登录的时间
     */
    private String uLoginTime;

    /**
     * 用户是否禁用
     */
    private int uStatus;
    /**
     * 角色id
     */
    private Integer rId;

    /**
     * 用户权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.uPassword;
    }

    @Override
    public String getUsername() {
        return this.uAccount;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}