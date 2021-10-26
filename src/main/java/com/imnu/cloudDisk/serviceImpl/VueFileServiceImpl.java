package com.imnu.cloudDisk.serviceImpl;

import com.imnu.cloudDisk.entity.VueFile;
import com.imnu.cloudDisk.mapper.VueFileMapper;
import com.imnu.cloudDisk.service.VueFileService;
import com.imnu.cloudDisk.util.VueUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/8/3 <br>
 */
@Service
@Slf4j
public class VueFileServiceImpl implements VueFileService {

    @Resource
    private VueFileMapper vueFileMapper;

    @Override
    public void insert(VueFile vueFile) {
        vueFileMapper.insert(vueFile);
    }

    @Override
    public VueFile findFileByFileName(Integer vId, String name) {
        return vueFileMapper.findFileByFileName(vId, name);
    }

    @Override
    public VueFile findFileIsExitByFileName(Integer vId, String fileName) {
        return vueFileMapper.findFileIsExitByFileName(vId,fileName);
    }

    @Override
    public VueFile findFileByFid(Integer fid) {
        return vueFileMapper.findFileByFid(fid);
    }

    @Override
    public List<VueFile> findAllFile() {
        return vueFileMapper.findAllFile();
    }

    @Override
    public int deleteVueFileByid(Integer id) {
        return vueFileMapper.updateStatusById(id);
    }

    @Override
    public void updateFnameById(VueFile vf) {
        vueFileMapper.updateFnameById(vf);
    }

    @Override
    public void deleteVueFileConfirmByid(Integer id) {
        vueFileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public VueFile findFileIsExitByFileName1(Integer vId, String filename) {
        return vueFileMapper.findFileIsExitByFileName1(vId,filename);
    }

    @Override
    public void updateStatus(VueFile vueFile1) {
        vueFileMapper.updateStatus(vueFile1);
    }

    @Override
    public List<Map<String, Object>> sortFile(Integer vId, Integer sign) {
        List<VueFile> vueFiles = vueFileMapper.findFileByVid(vId);
        List<Map<String, Object>> lm = new ArrayList<>();
        Map<String, Object> mp = new HashMap<>();

        if (sign == 12) {
            List<Map<String, Object>> pList = new ArrayList<>();
            for (VueFile vueFile : vueFiles) {
                if (VueUtils.getSort(vueFile.getFName()).equals("picture") && !vueFile.getIsDelete()) {
                    log.info(String.valueOf("ws:" + 12));
                    Map<String, Object> picture = new HashMap<>();
                    picture.put("name", vueFile.getFName());
                    picture.put("mtime", vueFile.getFUploadTime());
                    picture.put("size", vueFile.getFSize());
                    picture.put("img", "picture.svg");
                    picture.put("id", vueFile.getFId());
                    log.info(String.valueOf("ws:" + picture));
                    pList.add(picture);
                    mp.put("file", pList);
                    lm.add(mp);
                }
            }
        } else if (sign == 14) {
            List<Map<String, Object>> vList = new ArrayList<>();
            for (VueFile vueFile : vueFiles) {
                if (VueUtils.getSort(vueFile.getFName()).equals("video") && !vueFile.getIsDelete()) {
                    Map<String, Object> video = new HashMap<>();
                    video.put("name", vueFile.getFName());
                    video.put("mtime", vueFile.getFUploadTime());
                    video.put("size", vueFile.getFSize());
                    video.put("img", "video.svg");
                    video.put("id", vueFile.getFId());
                    vList.add(video);
                    mp.put("file", vList);
                    lm.add(mp);
                }
            }
        } else if (sign == 15) {
            List<Map<String, Object>> mList = new ArrayList<>();
            for (VueFile vueFile : vueFiles) {
                if (VueUtils.getSort(vueFile.getFName()).equals("music") && !vueFile.getIsDelete()) {
                    Map<String, Object> music = new HashMap<>();
                    music.put("name", vueFile.getFName());
                    music.put("mtime", vueFile.getFUploadTime());
                    music.put("size", vueFile.getFSize());
                    music.put("img", "music.svg");
                    music.put("id", vueFile.getFId());
                    mList.add(music);
                    mp.put("file", mList);
                    lm.add(mp);
                }
            }
        } else if (sign == 3) {
            List<Map<String, Object>> hList = new ArrayList<>();
            for (VueFile vueFile : vueFiles) {
                if (vueFile.getIsDelete()) {
                    Map<String, Object> huishou = new HashMap<>();
                    huishou.put("name", vueFile.getFName());
                    huishou.put("mtime", vueFile.getFUploadTime());
                    huishou.put("size", vueFile.getFSize());
                    huishou.put("img", VueUtils.getImg(vueFile.getFName()));
                    huishou.put("id",vueFile.getFId());
                    huishou.put("href",vueFile.getFPath());
                    hList.add(huishou);
                    mp.put("file", hList);
                    lm.add(mp);
                }
            }
        } else if (sign == 13) {
            List<Map<String, Object>> wList = new ArrayList<>();
            for (VueFile vueFile : vueFiles) {
                if (!VueUtils.getSort(vueFile.getFName()).equals("picture") && !VueUtils.getSort(vueFile.getFName()).equals("video") && !VueUtils.getSort(vueFile.getFName()).equals("music") && !vueFile.getIsDelete()) {

                    log.info(vueFile.getFName());
                    Map<String, Object> wendang = new HashMap<>();
                    wendang.put("name", vueFile.getFName());
                    wendang.put("mtime", vueFile.getFUploadTime());
                    wendang.put("size", vueFile.getFSize());
                    wendang.put("img", VueUtils.getImg(vueFile.getFName()));
                    wendang.put("id", vueFile.getFId());
                    wList.add(wendang);
                    mp.put("file", wList);
                    lm.add(mp);

                }
            }
        }

        return lm;
    }


}
