package com.imnu.cloudDisk.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * down_record
 * @author 
 */
@Data
public class DownRecord{
    private Integer dId;

    private Integer vId;

    private Integer fId;

    private VueUser vueUser;

    private VueFile vueFile;
}