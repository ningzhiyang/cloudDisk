package com.imnu.cloudDisk.service;

import com.imnu.cloudDisk.entity.DownRecord;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/8/5 <br>
 */
public interface DownRecordService {
    void insertRecord(DownRecord downRecord);

    List<DownRecord> findAllRecord();
}
