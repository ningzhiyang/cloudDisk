package com.imnu.cloudDisk.mapper;

import com.imnu.cloudDisk.entity.ShareFile;

import java.util.List;

public interface ShareFileMapper {
    int deleteByPrimaryKey(Integer sId);

    int insert(ShareFile record);

    int insertSelective(ShareFile record);

    ShareFile selectByPrimaryKey(Integer sId);

    int updateByPrimaryKeySelective(ShareFile record);

    int updateByPrimaryKey(ShareFile record);

    ShareFile getShareFileByHref(String sign);

    List<ShareFile> findAllShareFileByVid(Integer vId);

    int updateStatus(ShareFile shareFile);

    void addDownCount(Integer sid);
}