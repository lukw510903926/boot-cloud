package com.tykj.cloud.common.web;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类用于完成下载操作，把输入流写到response的输出流中， 然后response把文件返回到请求页面
 * <p>
 *
 * @version 1.0 <br>
 * @creatdate 2014-6-23 下午5:44:16 <br>
 */

public class DownloadUtil {

    protected static final Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

    private static final Map<String, String> application_Map = new HashMap<String, String>();

    private static final String default_application = "application/octet-stream";

    static {
        application_Map.put("xls", "application/vnd.ms-excel");
        application_Map.put("xlsx", "application/vnd.ms-excel");
        application_Map.put("doc", "application/msword");
        application_Map.put("docx", "application/msword");
        application_Map.put("pdf", "application/pdf");
        application_Map.put("ppt", "application/vnd.ms-powerpoint");
        application_Map.put("pptx", "application/vnd.ms-powerpoint");
        application_Map.put("rar", "application/octet-stream");
        application_Map.put("zip", "application/zip");
        application_Map.put("txt", "application/txt");

    }

    private DownloadUtil() {
    }

    /**
     * 第一个参数是文件在硬盘的路径，第二个参数是下载显示的文件名，第三个参数是response
     *
     * @param localPath
     * @param showName
     * @param resp
     */
    public static void downloadFile(String localPath, String showName, HttpServletResponse resp) {

        // 下载时候显示的文件名
        String downloadName;
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            downloadName = URLEncoder.encode(showName, "UTF-8");
            resp.reset();
            String suffix = StringUtils.lowerCase(showName.substring(showName.lastIndexOf('.') + 1));
            String application = application_Map.containsKey(suffix) ? application_Map.get(suffix) : default_application;
            resp.setContentType(application + "; charset=utf-8");
            resp.addHeader("Content-Disposition", "attachment; filename=" + downloadName);
            fis = new FileInputStream(localPath);
            IOUtils.copy(fis, os);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 第一个参数是文件输入流，第二个参数是下载显示的文件名，第三个参数是response
     *
     * @param inputStream
     * @param showName
     * @param resp
     */
    public static void downloadFile(InputStream inputStream, String showName, HttpServletResponse resp) {

        try {
            String downloadName = URLEncoder.encode(showName, "UTF-8");
            resp.reset();
            String suffix = StringUtils.lowerCase(showName.substring(showName.lastIndexOf('.') + 1));
            String application = application_Map.containsKey(suffix) ? application_Map.get(suffix) : default_application;
            resp.setContentType(application + "; charset=utf-8");
            resp.addHeader("Content-Disposition", "attachment; filename=" + downloadName);
            IOUtils.copy(inputStream, resp.getOutputStream());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
