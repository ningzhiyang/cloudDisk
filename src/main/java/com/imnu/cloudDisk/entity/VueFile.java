package com.imnu.cloudDisk.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * file_v
 * @author 
 */
@Data
public class VueFile implements Serializable {
    private Integer fId;

    /**
     * 文件名称
     */
    private String fName;

    /**
     * 文件后缀
     */
    private String fExt;

    /**
     * 文件路径
     */
    private String fPath;

    /**
     * 文件大小
     */
    private String fSize;

    /**
     * 下载次数
     */
    private Integer fDownCounts;

    /**
     * 上传文件时间
     */
    private String fUploadTime;

    /**
     * vue前台用户id
     */
    private Integer vId;

    /**
     * 是否删除 0未删除  1删除
     */
    private Boolean isDelete;

    private static final long serialVersionUID = 1L;
}