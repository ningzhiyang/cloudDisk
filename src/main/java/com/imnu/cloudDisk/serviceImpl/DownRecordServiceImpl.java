package com.imnu.cloudDisk.serviceImpl;

import com.imnu.cloudDisk.entity.DownRecord;
import com.imnu.cloudDisk.mapper.DownRecordMapper;
import com.imnu.cloudDisk.service.DownRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/8/5 <br>
 */
@Service
public class DownRecordServiceImpl implements DownRecordService {
    @Resource
    private DownRecordMapper downRecordMapper;
    @Override
    public void insertRecord(DownRecord downRecord) {
        downRecordMapper.insert(downRecord);
    }

    @Override
    public List<DownRecord> findAllRecord() {
        return downRecordMapper.findAllRecord();
    }
}
