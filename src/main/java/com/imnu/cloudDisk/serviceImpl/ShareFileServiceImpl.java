package com.imnu.cloudDisk.serviceImpl;

import com.imnu.cloudDisk.entity.ShareFile;
import com.imnu.cloudDisk.entity.VueFile;
import com.imnu.cloudDisk.mapper.ShareFileMapper;
import com.imnu.cloudDisk.mapper.VueFileMapper;
import com.imnu.cloudDisk.service.ShareFileService;
import com.imnu.cloudDisk.util.VueUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/8/5 <br>
 */
@Service
public class ShareFileServiceImpl implements ShareFileService {
    @Resource
    private ShareFileMapper shareFileMapper;
    @Resource
    private VueFileMapper vueFileMapper;
    @Override
    public ShareFile getShareFileByHref(String sign) {
        return shareFileMapper.getShareFileByHref(sign);
    }

    @Override
    public void insertShareFileService(ShareFile shareFile) {
        shareFileMapper.insert(shareFile);
    }

    @Override
    public List<ShareFile> findAllShareFileByVid(Integer vId) {
        return shareFileMapper.findAllShareFileByVid(vId);
    }

    @Override
    public List<Map<String, Object>> findVueFile(List<ShareFile> shareFiles) {
        List<Map<String, Object>> lm = new ArrayList<>();
        for (ShareFile shareFile : shareFiles) {
            VueFile vueFile = vueFileMapper.selectByPrimaryKey(shareFile.getFId());
            Map<String, Object> vf = new HashMap<>();
            vf.put("name", vueFile.getFName());
            vf.put("mtime", vueFile.getFUploadTime());
            vf.put("size", vueFile.getFSize());
            vf.put("img", VueUtils.getImg(vueFile.getFName()));
            vf.put("id", shareFile.getSId());
            vf.put("href", shareFile.getSHref());
            vf.put("code", shareFile.getSCode());
            lm.add(vf);
        }
        return lm;
    }

    @Override
    public int updateStatus(ShareFile shareFile) {
        return shareFileMapper.updateStatus(shareFile);
    }

    @Override
    public ShareFile getShareFileById(Integer sid) {
        return shareFileMapper.selectByPrimaryKey(sid);
    }

    @Override
    public void addDownCount(Integer sid) {
        shareFileMapper.addDownCount(sid);
    }
}
