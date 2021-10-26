package com.imnu.cloudDisk.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * sys_role
 * @author 
 */
@Data
public class SysRole implements Serializable {
    private Integer rId;

    /**
     * 角色名称
     */
    private String rName;

    /**
     * 角色备注
     */
    private String rDetail;

    /**
     * 角色菜单组
     */
    private String rGroup;

    private String sysMenuList;

    private static final long serialVersionUID = 1L;
}