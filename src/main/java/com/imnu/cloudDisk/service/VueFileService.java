package com.imnu.cloudDisk.service;

import com.imnu.cloudDisk.entity.VueFile;

import java.util.List;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/8/3 <br>
 */
public interface VueFileService {

    void insert(VueFile vueFile);
    

    void updateStatus(VueFile vueFile1);
    
    List<Map<String, Object>> sortFile(Integer vId, Integer sign);

    VueFile findFileByFileName(Integer vId, String s);

    VueFile findFileIsExitByFileName(Integer vId, String fileName);

    VueFile findFileByFid(Integer fid);

    List<VueFile> findAllFile();

    int deleteVueFileByid(Integer id);

    void updateFnameById(VueFile vf);

    void deleteVueFileConfirmByid(Integer id);

    VueFile findFileIsExitByFileName1(Integer vId, String filename);
}
