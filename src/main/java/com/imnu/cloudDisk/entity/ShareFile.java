package com.imnu.cloudDisk.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * share_file
 * @author 
 */
@Data
public class ShareFile implements Serializable {
    private Integer sId;

    /**
     * 分享连接
     */
    private String sHref;

    /**
     * 提货码
     */
    private String sCode;

    /**
     * 文件id
     */
    private Integer fId;

    /**
     * 分享过期时间
     */
    private String sOverdue;

    /**
     * 创建时间
     */
    private String sCreatTime;

    /**
     * 是否删除
     */
    private Boolean sStatus;

    /**
     * 下载次数
     */
    private Integer sDownCount;
    /**
     * vid
     */
    private Integer vId;
    private static final long serialVersionUID = 1L;
}