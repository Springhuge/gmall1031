package com.jihu.gamll.common.util;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

public class PmsUploadUtil {


    public static String uploadImage(MultipartFile multipartFile){
        String imgUrl = "http://192.168.164.131";

        TrackerServer trackerServer = null;

        //上传图片到服务器
        String tracker = PmsUploadUtil.class.getResource("/tracker.conf").getPath();//获得配置文件的路径

        try {
            ClientGlobal.init(tracker);

            TrackerClient trackerClient = new TrackerClient();

            //获得一个trackerServer的实例
            trackerServer = trackerClient.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //通过tracker获得一个Storage连接客户端
        StorageClient storageClient =  new StorageClient(trackerServer,null);

        try {
            byte[] bytes = multipartFile.getBytes();//获得上传的二级制对象

            //获得文件后缀名
            String originalFilename = multipartFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            String extName = originalFilename.substring(i+1);

            String[] uploadInfos = storageClient.upload_file(bytes,extName,null);

            for (String uploadInfo : uploadInfos) {
                imgUrl += "/"+uploadInfo;
            }
            System.out.println(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgUrl;
    }

}
