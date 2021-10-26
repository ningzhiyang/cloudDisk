package com.imnu.cloudDisk.service;

import com.imnu.cloudDisk.entity.ShareFile;
import com.imnu.cloudDisk.entity.VueFile;

import java.util.List;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/8/5 <br>
 */
public interface ShareFileService {
    ShareFile getShareFileByHref(String sign);

    void insertShareFileService(ShareFile shareFile);

    List<ShareFile> findAllShareFileByVid(Integer vId);

    List<Map<String, Object>> findVueFile(List<ShareFile> shareFiles);

    int updateStatus(ShareFile shareFile);

    ShareFile getShareFileById(Integer sid);

    void addDownCount(Integer sid);
}
