package com.imnu.cloudDisk.controller.vueData;

import com.alibaba.fastjson.JSONObject;
import com.imnu.cloudDisk.entity.DownRecord;
import com.imnu.cloudDisk.entity.ShareFile;
import com.imnu.cloudDisk.entity.VueFile;
import com.imnu.cloudDisk.entity.VueUser;
import com.imnu.cloudDisk.hadoop.HadoopConfig;
import com.imnu.cloudDisk.service.DownRecordService;
import com.imnu.cloudDisk.service.ShareFileService;
import com.imnu.cloudDisk.service.VueFileService;
import com.imnu.cloudDisk.service.VueUserService;
import com.imnu.cloudDisk.util.MD5Util;
import com.imnu.cloudDisk.util.TimeUtil;
import com.imnu.cloudDisk.util.VueUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/29 <br>
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/vue/file")
public class VueFileController {

    @Autowired
    private HadoopConfig hadoopConfig;
    @Autowired
    private VueFileService vueFileService;
    @Autowired
    private VueUserService vueUserService;
    @Autowired
    private ShareFileService shareFileService;
    @Autowired
    private DownRecordService downRecordService;

    //上传
    @RequestMapping("/uploadFileToHDFS")
    public JSONObject uploadFileToHDFS(@RequestParam("file") MultipartFile file, @RequestParam("username") String username, @RequestParam("sign") String sign, @RequestParam("path") String path, HttpServletRequest request) throws IOException {
        JSONObject json = new JSONObject();
        String fileName = file.getOriginalFilename();
        Integer vId = vueUserService.getVidByVueUserName(username);
        VueFile vueFile1 = vueFileService.findFileIsExitByFileName(vId, fileName);
        if (vueFile1 == null) {
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/vueFile/" + username + "/";
//            String filePath = "D:\\java\\idea_workplace\\cloudDisk\\src\\main\\resources\\static\\vueFile\\" + username + "\\";
            String fileVue = filePath + fileName;
            log.info(fileVue);
            File file1 = new File(fileVue);
            if (!file1.exists()) {
                file1.mkdirs();
            } else {
                file1.delete();
            }
            file.transferTo(file1);
            log.info("上传成功");
            Boolean upFile = hadoopConfig.uploadFileToHDFS(fileVue, "/" + username + path);
            VueFile vueFile = new VueFile();
            vueFile.setFName(fileName);
            vueFile.setFDownCounts(0);
            vueFile.setFUploadTime(TimeUtil.getCurrentTime());
            vueFile.setFPath("hdfs://47.99.39.91:9000/ayCloudDisk" + "/" + username + path);
            vueFile.setIsDelete(false);
            vueFile.setFSize((file.getSize() / 1024) + "KB");
            vueFile.setVId(vId);
            if (fileName.contains(".")) {
                String[] nameArr = fileName.split("\\.");
                vueFile.setFExt(nameArr[1]);
            } else {
                vueFile.setFExt("-");
            }
            vueFileService.insert(vueFile);
            if (upFile) {
                json.put("code", 1);
            } else {
                json.put("code", 0);
            }
            return json;
        } else {
            json.put("code", 0);
            return json;
        }
    }

    //查看列表
    @RequestMapping("/listAll")
//    @RequestParam("username") String username,@RequestParam("sign") String sign
    public JSONObject listAll(@RequestParam("username") String username, @RequestParam("sign") Integer sign) throws IOException {
        log.info(username + "--->" + sign);
        JSONObject json = new JSONObject();
        List<Map<String, Object>> listAll = hadoopConfig.listAll("/" + username + "/");
        Integer vId = vueUserService.getVidByVueUserName(username);
        if (sign == 1) {
            if (listAll == null) {
                json.put("file", null);
                json.put("dir", null);
            } else {
                for (Map<String, Object> stringObjectMap : listAll) {
                    json.put("file", stringObjectMap.get("file"));
                    json.put("dir", stringObjectMap.get("dir"));
                }
            }
        } else if (sign == 2) {
            List<ShareFile> shareFiles = shareFileService.findAllShareFileByVid(vId);
            if (shareFiles == null) {
                json.put("file", null);
                json.put("dir", null);
            } else {
                List<Map<String, Object>> vueFileList = shareFileService.findVueFile(shareFiles);
                json.put("file", vueFileList);
                json.put("dir", null);
            }
        } else {
            List<Map<String, Object>> listAll1 = vueFileService.sortFile(vId, sign);
            if (listAll1 == null) {
                json.put("file", null);
                json.put("dir", null);
            } else {
                for (Map<String, Object> stringObjectMap : listAll1) {
                    json.put("file", stringObjectMap.get("file"));
                    json.put("dir", null);
                }
            }
        }
        return json;
    }

    //下一页
    @RequestMapping("/nextLevel")
    public JSONObject nextLevel(@RequestParam("username") String username, @RequestParam("sign") String sign, @RequestParam("path") String path) throws IOException {
        log.info(username + "--->" + sign + "--->" + "/" + username + path);
        JSONObject json = new JSONObject();
        List<Map<String, Object>> listAll = hadoopConfig.listAll("/" + username + path);
        log.info(String.valueOf(listAll));
        if (listAll == null) {
            json.put("file", null);
            json.put("dir", null);
        } else {
            for (Map<String, Object> stringObjectMap : listAll) {
                json.put("file", stringObjectMap.get("file"));
                json.put("dir", stringObjectMap.get("dir"));
            }
        }
        return json;
    }

    //上一页
    @RequestMapping("/lastLevel")
    public JSONObject lastLevel(@RequestParam("username") String username, @RequestParam("sign") String sign, @RequestParam("path") String path) throws IOException {
        log.info(username + "--->" + sign + "--->" + "/" + username + path);
        JSONObject json = new JSONObject();
        List<Map<String, Object>> listAll = hadoopConfig.listAll("/" + username + path);
        log.info(String.valueOf(listAll));
        if (listAll == null) {
            json.put("file", null);
            json.put("dir", null);
        } else {
            for (Map<String, Object> stringObjectMap : listAll) {
                json.put("file", stringObjectMap.get("file"));
                json.put("dir", stringObjectMap.get("dir"));
            }
        }
        return json;
    }

    //新文件夹
    @RequestMapping("/newfolder")
    public JSONObject newfolder(@RequestParam("username") String username, @RequestParam("sign") String sign, @RequestParam("path") String path, @RequestParam("fname") String fname) throws IOException {
        log.info(username + "--->" + sign + "--->" + "/" + username + path + "--->" + fname);
        JSONObject json = new JSONObject();
        if (hadoopConfig.mkdir("/" + username + path + fname)) {
            json.put("code", 0);
        } else {
            json.put("code", 1);
        }
        return json;
    }

    //新文件
    @RequestMapping("/newtxt")
    public JSONObject newtxt(@RequestParam("username") String username, @RequestParam("sign") String sign, @RequestParam("path") String path, @RequestParam("txtname") String txtname) throws IOException {
        log.info(username + "--->" + sign + "--->" + "/" + username + path + "--->" + txtname);
        JSONObject json = new JSONObject();
        Integer vId = vueUserService.getVidByVueUserName(username);
        String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/vueFile/" + username + "/";
//            String filePath = "D:\\java\\idea_workplace\\cloudDisk\\src\\main\\resources\\static\\vueFile\\" + username + "\\";
        String fileVue = filePath + txtname;
        log.info(fileVue);
        File file1 = new File(fileVue);
        if (!file1.exists()) {
            file1.createNewFile();
        } else {
            file1.delete();
        }
        VueFile vueFile = new VueFile();
        vueFile.setFName(txtname);
        vueFile.setFDownCounts(0);
        vueFile.setFUploadTime(TimeUtil.getCurrentTime());
        vueFile.setFPath("hdfs://47.99.39.91:9000/ayCloudDisk" + "/" + username + path);
        vueFile.setIsDelete(false);
        vueFile.setFExt(txtname.split("\\.")[1]);
        vueFile.setFSize("0KB");
        vueFile.setVId(vId);
        vueFileService.insert(vueFile);
        if (hadoopConfig.mktxt("/" + username + path + txtname)) {
            json.put("code", 0);
        } else {
            json.put("code", 1);
        }
        return json;
    }

    //删除文件
    @RequestMapping("/delfile")
    public JSONObject delFile( @RequestParam("username") String username, @RequestParam("name") String name,@RequestParam("filename") String filename, @RequestParam("sign") Integer sign) throws Exception {
        JSONObject json = new JSONObject();
        Integer vId = vueUserService.getVidByVueUserName(username);
        VueFile vueFile = vueFileService.findFileIsExitByFileName(vId, filename);
        log.info( "----" + username + "----" + name + "----"+filename);
        if (sign == 3){
            VueFile vueFileX = vueFileService.findFileIsExitByFileName1(vId, filename);
            vueFileService.deleteVueFileConfirmByid(vueFileX.getFId());
        }
        if (vueFile !=null){
            VueFile vueFile1 = new VueFile();
            vueFile1.setFId(vueFile.getFId());
            vueFile1.setIsDelete(true);
            vueFileService.updateStatus(vueFile1);
            Boolean del = hadoopConfig.delete("/" + username + name+filename);
            if (!del) {
                json.put("code", 0);
            } else {
                json.put("code", 1);
            }
        }else {
            Boolean del = hadoopConfig.delete("/" + username + name+filename);
            if (del) {
                json.put("code", 0);
            } else {
                json.put("code", 1);
            }
        }
        return json;
    }

    //删除分类文件
    @RequestMapping("/delSortfile")
    public JSONObject delSortfile(@RequestParam("id") Integer id, @RequestParam("username") String username, @RequestParam("name") String name, @RequestParam("sign") Integer sign) throws Exception {
        JSONObject json = new JSONObject();
        if (sign == 3){
            vueFileService.deleteVueFileConfirmByid(id);
        }
        VueFile vueFile1 = new VueFile();
        vueFile1.setFId(id);
        vueFile1.setIsDelete(true);
        vueFileService.updateStatus(vueFile1);
        Boolean del = hadoopConfig.delete("/" + username + name);
        if (!del) {
            json.put("code", 0);
        } else {
            json.put("code", 1);
        }
        return json;
    }

    //下载
    @RequestMapping("/download")
    public void download(@RequestParam("username") String username, @RequestParam("fileName") String fileName, HttpServletResponse response) throws Exception {

        //根据文件信息中文件名字 和 文件存储路径获取文件输入流
        String fpath = ResourceUtils.getURL("classpath:").getPath() + "static/vueFile/" + username + "/";
//        String fpath = "D:\\java\\idea_workplace\\cloudDisk\\src\\main\\resources\\static\\vueFile\\" + username + "\\";
//        //附件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("charset", "UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(fpath, fileName)));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //查看文件
    @RequestMapping("/seeFile")
    public JSONObject seeFile(@RequestParam("filename") String filename, @RequestParam("username") String username) {
        JSONObject json = new JSONObject();
        json.put("sort", VueUtils.getSort(filename));
        json.put("uri", "http://www.shiningstars.cn/cloud/vueFile/" + username + "/" + filename);
        return json;
    }

    //分享文件
    @RequestMapping("/to_shareFile")
    public JSONObject shareFile(@RequestParam("overdue") String overdue, @RequestParam("code") String code, @RequestParam("filename") String filename, @RequestParam("username") String username) {
        JSONObject json = new JSONObject();
        Integer vId = vueUserService.getVidByVueUserName(username);
        VueFile vueFile = vueFileService.findFileIsExitByFileName(vId, filename);
        ShareFile shareFile = new ShareFile();
        shareFile.setFId(vueFile.getFId());
        shareFile.setSCode(code);
        shareFile.setSCreatTime(TimeUtil.getCurrentTime());
        shareFile.setSStatus(false);
        shareFile.setSOverdue(overdue);
        shareFile.setSDownCount(0);
        shareFile.setVId(vId);
        shareFile.setSHref("http://www.shiningstars.cn/AyCloud/#/shareFileCode/"+MD5Util.encode(username + filename + TimeUtil.getCurrentTime()));
        shareFileService.insertShareFileService(shareFile);
        json.put("code", 1);
        return json;
    }


    //取消分享文件
    @RequestMapping("/quitShare")
    public JSONObject quitShare(@RequestParam("id") Integer id) {
        JSONObject json = new JSONObject();
        ShareFile shareFile = new ShareFile();
        shareFile.setSId(id);
        shareFile.setSStatus(true);
        int ret = shareFileService.updateStatus(shareFile);
        log.info(String.valueOf(ret));
        if (ret == 0) {
            json.put("code", 0);
        } else {
            json.put("code", 1);
        }
        return json;
    }

    //还原垃圾箱文件
    @RequestMapping("/huanYuan")
    public JSONObject huanYuan(@RequestParam("id") Integer id, @RequestParam("href") String href, @RequestParam("username") String username, @RequestParam("fname") String fname) throws IOException {
        JSONObject json = new JSONObject();
        VueFile vueFile = new VueFile();
        vueFile.setFId(id);
        vueFile.setIsDelete(false);
        vueFileService.updateStatus(vueFile);
        String fileName = fname;
//        String filePath = "D:\\java\\idea_workplace\\cloudDisk\\src\\main\\resources\\static\\vueFile\\" + username + "\\";
        String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/vueFile/" + username + "/";
        String fileVue = filePath + fileName;
        Boolean res = hadoopConfig.refreshFileToHDFS(fileVue, href);
        if (res) {
            json.put("code", 1);
        } else {
            json.put("code", 0);
        }
        return json;
    }

    //检查连接
    @RequestMapping("/checkUrl")
    public JSONObject shareFile(@RequestParam("sign") String sign) {
        JSONObject json = new JSONObject();
        log.info(sign);
        ShareFile shareFile = shareFileService.getShareFileByHref(sign);
        log.info(String.valueOf(shareFile));
        if (shareFile == null) {
            json.put("code", 0);
        } else {
            json.put("code", 1);
        }
        return json;
    }

    //检查提取码
    @RequestMapping("/checkCode")
    public JSONObject checkCode(@RequestParam("sign") String sign,@RequestParam("code") String code) {
        JSONObject json = new JSONObject();
        log.info(sign);
        ShareFile shareFile = shareFileService.getShareFileByHref(sign);
        log.info(String.valueOf(shareFile));
        if (shareFile == null || !shareFile.getSCode().equals(code)) {
            json.put("code", 0);
        } else {
            json.put("code", 1);
            json.put("fid",shareFile.getFId());
            json.put("sid",shareFile.getSId());
        }
        return json;
    }

    //获取文件
    @RequestMapping("/getFile")
    public JSONObject getFile(@RequestParam("fid") Integer fid) {
        JSONObject json = new JSONObject();
        VueFile vueFile = vueFileService.findFileByFid(fid);
        if (vueFile == null) {
            json.put("code", 0);
        } else {
            json.put("code", 1);
            json.put("vueFile",vueFile);
            json.put("fimg",VueUtils.getImg(vueFile.getFName()));
        }
        return json;
    }

    //获取文件
    @RequestMapping("/downShareFile")
    public void downShareFile(@RequestParam("fid") Integer fid,@RequestParam("sid") Integer sid,@RequestParam("username") String username,HttpServletResponse response) throws UnsupportedEncodingException, FileNotFoundException {

        Integer vId = vueUserService.getVidByVueUserName(username);
        DownRecord downRecord = new DownRecord();
        downRecord.setFId(fid);
        downRecord.setVId(vId);
        downRecordService.insertRecord(downRecord);
        ShareFile shareFile = shareFileService.getShareFileById(sid);
        shareFileService.addDownCount(sid);
        VueUser vueUser = vueUserService.getVueUserById(shareFile.getVId());
        VueFile vueFile = vueFileService.findFileByFid(fid);
        //根据文件信息中文件名字 和 文件存储路径获取文件输入流
        String fpath = ResourceUtils.getURL("classpath:").getPath() + "static/vueFile/" + vueUser.getVAccount() + "/";
//        String fpath = "D:\\java\\idea_workplace\\cloudDisk\\src\\main\\resources\\static\\vueFile\\" + vueUser.getVAccount() + "\\";
//        //附件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("charset", "UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(vueFile.getFName(), "UTF-8"));
        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(fpath, vueFile.getFName())));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
