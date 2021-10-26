package com.imnu.cloudDisk.mapper;

import com.imnu.cloudDisk.entity.VueFile;

import java.util.List;

public interface VueFileMapper {
    int deleteByPrimaryKey(Integer fId);

    int insert(VueFile record);

    int insertSelective(VueFile record);

    VueFile selectByPrimaryKey(Integer fId);

    int updateByPrimaryKeySelective(VueFile record);

    int updateByPrimaryKey(VueFile record);

    VueFile findFileByFileName(Integer vId,String name);

    void updateStatus(VueFile vueFile1);

    List<VueFile> findFileByVid(Integer vId);

    VueFile findFileIsExitByFileName(Integer vId, String fileName);

    VueFile findFileByFid(Integer fid);

    List<VueFile> findAllFile();

    int updateStatusById(Integer id);

    void updateFnameById(VueFile vf);

    VueFile findFileIsExitByFileName1(Integer vId, String filename);
}