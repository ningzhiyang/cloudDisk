package com.imnu.cloudDisk.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/27 <br>
 */
public class FileUtil {
    //用于存储服务器
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+"/"+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
