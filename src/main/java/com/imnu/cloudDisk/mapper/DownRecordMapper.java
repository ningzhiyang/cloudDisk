package com.imnu.cloudDisk.mapper;

import com.imnu.cloudDisk.entity.DownRecord;

import java.util.List;

public interface DownRecordMapper {
    int deleteByPrimaryKey(Integer dId);

    int insert(DownRecord record);

    int insertSelective(DownRecord record);

    DownRecord selectByPrimaryKey(Integer dId);

    int updateByPrimaryKeySelective(DownRecord record);

    int updateByPrimaryKey(DownRecord record);

    List<DownRecord> findAllRecord();
}