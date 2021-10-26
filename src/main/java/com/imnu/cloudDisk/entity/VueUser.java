package com.imnu.cloudDisk.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * v_user
 * @author 
 */
@Data
public class VueUser implements Serializable {
    private Integer vId;

    /**
     * vue账户
     */
    private String vAccount;

    /**
     * vue密码
     */
    private String vPwd;

    /**
     * vue图片
     */
    private String vImg;

    /**
     * vue手机号
     */
    private String vPhone;

    /**
     * 是否禁用 0禁用  1 使用
     */
    private Integer vStatus;
    /*
        * 登录IP
     */
    private String vLoginIp;

    private String vLoginTime;

    private static final long serialVersionUID = 1L;
}