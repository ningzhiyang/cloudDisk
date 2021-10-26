package com.imnu.cloudDisk.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_menu
 * @author 
 */
@Data
public class SysMenu implements Serializable {
    private Integer mId;

    /**
     * 一级菜单
     */
    private Integer mFirstMenuId;

    /**
     * 二级菜单
     */
    private Integer mSecondMenuId;

    /**
     * 三级菜单
     */
    private Integer mThirdMenuId;

    /**
     * 菜单url地址
     */
    private String mUrl;

    /**
     * 菜单名称
     */
    private String mName;

    /**
     * 菜单图标
     */
    private String mIcon;

    /**
     * 菜单是否禁用
     */
    private Boolean mStatus;

    private static final long serialVersionUID = 1L;
}