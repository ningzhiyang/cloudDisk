package com.imnu.cloudDisk.hadoop;

import com.alibaba.fastjson.JSONObject;
import com.imnu.cloudDisk.util.VueUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/29 <br>
 */
@Component
@Slf4j
public class HadoopConfig {

    @Autowired
    private HdfsConfig hdfsConfig;

    /**
     * 文件是否存在
     * @return Boolean
     */
    public Boolean exitFile(String p) throws IOException {
        String uri = hdfsConfig.getNameNode()+hdfsConfig.getNameSpace()+p;
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
        Path path = new Path(uri);
        boolean exitf = fileSystem.exists(path);//true是存在   false是不存在
        fileSystem.close();
        return exitf;
    }

    /**
     * 创建文件夹
     * @return Boolean true创建成功  false创建失败，已经存在文件夹
     */
    public Boolean mkdir(String p) throws IOException {
        String uri = hdfsConfig.getNameNode()+hdfsConfig.getNameSpace()+p;
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
        Path path = new Path(uri);
        boolean exitf = fileSystem.exists(path);//true是存在   false是不存在
        if (!exitf){
            fileSystem.mkdirs(path);
            fileSystem.close();
            return true;
        }else {
            return false;
        }
    }

    /**
     * 列出文件夹或文件
     * @return List
     */
    public List<Map<String, Object>> listAll(String p) throws IOException {
        if (StringUtils.isEmpty(p)) {
            return null;
        }
        if (!exitFile(p)) {
            return null;
        }
        String uri = hdfsConfig.getNameNode()+hdfsConfig.getNameSpace()+p;
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
        Path path = new Path(uri);
        FileStatus[] fileStatus = fileSystem.listStatus(path);
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> fileList = new ArrayList<>();
        List<Map<String, Object>> dirList = new ArrayList<>();
        Map<String, Object> listMap = new HashMap<>();
        if (fileStatus != null && fileStatus.length > 0) {
            for (FileStatus fs : fileStatus) {
                String name = fs.getPath().getName();
                VueUtils vueUtils = new VueUtils();
                Map<String, Object> fileMap = new HashMap<>();
                Map<String, Object> dirMap = new HashMap<>();
                if (!fs.isDirectory()){
                    fileMap.put("filePath", fs.getPath());
                    fileMap.put("name", fs.getPath().getName());
                    SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //格式化当前系统日期
                    String dateTime = dateFm.format(fs.getModificationTime());
                    fileMap.put("mtime", dateTime);
                    fileMap.put("size", fs.getLen());
                    fileMap.put("img", vueUtils.getImg(name));
                    fileList.add(fileMap);
                    listMap.put("file",fileList);
                    list.add(listMap);
                }else {
                    dirMap.put("filePath", fs.getPath());
                    dirMap.put("name", fs.getPath().getName());
                    SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //格式化当前系统日期
                    String dateTime = dateFm.format(fs.getModificationTime());
                    dirMap.put("mtime", dateTime);
                    dirMap.put("size", fs.getLen());
                    dirMap.put("img", "folder.svg");
                    dirList.add(dirMap);
                    listMap.put("dir",dirList);
                    list.add(listMap);
                }
            }
            log.info(String.valueOf(list));
            fileSystem.close();
            return list;
        } else {
            fileSystem.close();
            return null;
        }
    }

    /**
     * 读取HDFS文件内容
     * @param path
     * @return
     * @throws Exception
     */
    public String readFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!exitFile(path)) {
            return null;
        }
        String uri = hdfsConfig.getNameNode() + hdfsConfig.getNameSpace() + path;
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
        Path srcPath = new Path(uri);
        FSDataInputStream inputStream = null;
        try {
            inputStream = fileSystem.open(srcPath);
            // 防止中文乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = "";
            StringBuffer sb = new StringBuffer();
            while ((lineTxt = reader.readLine()) != null) {
                sb.append(lineTxt);
            }
            return sb.toString();
        } finally {
            inputStream.close();
            fileSystem.close();
        }
    }

    /**
     * 删除文件或目录
     * @param path
     * @return true是删除  false否
     * @throws Exception
     */
    public Boolean delete(String path) throws Exception {
        String uri = hdfsConfig.getNameNode() + hdfsConfig.getNameSpace() + path;
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
        Path srcPath = new Path(uri);
        boolean isdel = fileSystem.delete(srcPath,false);
        fileSystem.close();
        return isdel;
    }

    /**
     * HDFS重命名文件
     * @param oldName 旧的路径名称
     * @param newName 新的路径名称
     * @return true false
     * @throws Exception
     */
    public boolean renameFile(String oldName, String newName) throws Exception {
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            return false;
        }
        String uri = hdfsConfig.getNameNode() + hdfsConfig.getNameSpace();
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
        // 原文件目标路径
        Path oldPath = new Path(uri+oldName);
        // 重命名目标路径
        Path newPath = new Path(uri+newName);
        boolean isOk = fileSystem.rename(oldPath, newPath);
        fileSystem.close();
        return isOk;
    }

    /**
     * 文件上传至 HDFS
     */
    public Boolean uploadFileToHDFS(String srcFile, String destPath) throws IOException {
        String uri = hdfsConfig.getNameNode() + hdfsConfig.getNameSpace();
        Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "1");
        configuration.set("dfs.client.use.datanode.hostname","true");
        FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
        // 源文件路径
        Path srcPath = new Path(srcFile);
        Path dstPath = new Path(uri+destPath);
        boolean exitf = fileSystem.exists(dstPath);//true是存在   false是不存在
        if (exitf){
            log.info(srcFile+"----->"+uri+destPath);
            // 实现文件上传
            fileSystem.copyFromLocalFile(srcPath, dstPath);
            fileSystem.close();
            return true;
        }else {
            return false;
        }
    }

    /**
     * 从 HDFS 下载文件
     */
    public void downFile(String hdfsFile, String destPath) throws IOException {
        String uri = hdfsConfig.getNameNode() + hdfsConfig.getNameSpace();
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
        Path hdfsPath = new Path(uri+hdfsFile);
        Path dstPath = new Path(destPath);
        fileSystem.copyToLocalFile(hdfsPath, dstPath);
        fileSystem.close();
    }
    /**
     * 创建文件
     */
    public boolean mktxt(String p) throws IOException {
        String uri = hdfsConfig.getNameNode()+hdfsConfig.getNameSpace()+p;
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
        Path path = new Path(uri);
        boolean exitf = fileSystem.exists(path);//true是存在   false是不存在
        if (!exitf){
            FSDataOutputStream outputStream = fileSystem.create(path);
            outputStream.close();
            fileSystem.close();
            return true;
        }else {
            return false;
        }
    }

    /**
     * 打开HDFS上的文件并返回java对象
     * @param path
     * @return
     * @throws Exception
     */
    public JSONObject openFileToObject(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!exitFile(path)) {
            return null;
        }
        String jsonStr = readFile(path);
        return JSONObject.parseObject(jsonStr);
    }

    public Boolean refreshFileToHDFS(String fileVue, String href) throws IOException {
        String uri = hdfsConfig.getNameNode() + hdfsConfig.getNameSpace();
        Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "1");
        configuration.set("dfs.client.use.datanode.hostname","true");
        FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
        // 源文件路径
        Path srcPath = new Path(fileVue);
        Path dstPath = new Path(href);
        boolean exitf = fileSystem.exists(dstPath);//true是存在   false是不存在
        if (exitf){
            log.info(fileVue+"----->"+href);
            // 实现文件上传
            fileSystem.copyFromLocalFile(srcPath, dstPath);
            fileSystem.close();
            return true;
        }else {
            return false;
        }
    }

    public Integer userdSpace(String username) throws IOException {
        String uri = hdfsConfig.getNameNode() + hdfsConfig.getNameSpace() + "/" + username;
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
        Path path = new Path(uri);
        Integer x = Math.toIntExact(fileSystem.getContentSummary(path).getSpaceConsumed());
        fileSystem.close();
        log.info(String.valueOf(x));
        return x;
    }

    public boolean deleteFile(String path) throws IOException {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(path), configuration);
        Path srcPath = new Path(path);
        boolean isdel = fileSystem.delete(srcPath,false);
        fileSystem.close();
        return isdel;
    }
}
