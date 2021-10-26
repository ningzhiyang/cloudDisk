package com.imnu.cloudDisk.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/31 <br>
 */
public class VueUtils {
    public static String getImg(String name) {
        List<String> docList = new ArrayList<>();
        docList.add("doc");
        docList.add("docx");
        List<String> excelList = new ArrayList<>();
        excelList.add("xls");
        excelList.add("xlsx");
        List<String> videoList = new ArrayList<>();
        videoList.add("mp4");
        List<String> picList = new ArrayList<>();
        picList.add("jpg");
        picList.add("png");
        picList.add("jpeg");
        picList.add("bmp");
        picList.add("gif");
        picList.add("psd");
        picList.add("svg");
        picList.add("JPG");
        picList.add("PNG");
        picList.add("JPEG");
        picList.add("BMP");
        picList.add("GIF");
        picList.add("PSD");
        picList.add("SVG");
        List<String> musicList = new ArrayList<>();
        musicList.add("mp3");
        List<String> pptList = new ArrayList<>();
        pptList.add("ppt");
        pptList.add("pptx");
        if (name.contains(".")){
            String [] nameArr = name.split("\\.");
            if (docList.contains(nameArr[1])){
                return "world.svg";
            }else if (excelList.contains(nameArr[1])){
                return "excel.svg";
            }else if (videoList.contains(nameArr[1])){
                return "video.svg";
            }else if (picList.contains(nameArr[1])){
                return "picture.svg";
            }else if (musicList.contains(nameArr[1])){
                return "music.svg";
            }else if (pptList.contains(nameArr[1])){
                return "ppt.svg";
            }else if ("zip".equals(nameArr[1])){
                return "zip.svg";
            }else if ("txt".equals(nameArr[1])){
                return "txt.svg";
            }else if ("pdf".equals(nameArr[1])){
                return "PDF.svg";
            }else{
                return "File.svg";
            }
        }else{
            return "File.svg";
        }
    }

    public static String getSort(String name) {
        List<String> docList = new ArrayList<>();
        docList.add("doc");
        docList.add("docx");
        List<String> excelList = new ArrayList<>();
        excelList.add("xls");
        excelList.add("xlsx");
        List<String> pptList = new ArrayList<>();
        pptList.add("ppt");
        pptList.add("pptx");
        List<String> videoList = new ArrayList<>();
        videoList.add("mp4");
        List<String> picList = new ArrayList<>();
        picList.add("jpg");
        picList.add("png");
        picList.add("jpeg");
        picList.add("bmp");
        picList.add("gif");
        picList.add("psd");
        picList.add("svg");
        picList.add("JPG");
        picList.add("PNG");
        picList.add("JPEG");
        picList.add("BMP");
        picList.add("GIF");
        picList.add("PSD");
        picList.add("SVG");
        List<String> musicList = new ArrayList<>();
        musicList.add("mp3");
        if (name.contains(".")){
            String [] nameArr = name.split("\\.");
            if (docList.contains(nameArr[1])){
                return "world";
            }else if (excelList.contains(nameArr[1])){
                return "excel";
            }else if (videoList.contains(nameArr[1])){
                return "video";
            }else if (picList.contains(nameArr[1])){
                return "picture";
            }else if (musicList.contains(nameArr[1])){
                return "music";
            }else if (pptList.contains(nameArr[1])){
                return "ppt";
            }else if ("zip".equals(nameArr[1])){
                return "zip";
            }else if ("txt".equals(nameArr[1])){
                return "txt";
            }else if ("pdf".equals(nameArr[1])){
                return "pdf";
            }else{
                return "File";
            }
        }else{
            return "File";
        }
    }
}
