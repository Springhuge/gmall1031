package com.jihu.gmall.gmallmanageweb;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageWebApplicationTests {

    @Test
    public void contextLoads() throws IOException, MyException {
        String tracker = GmallManageWebApplication.class.getResource("/tracker.conf").getPath();//获得配置文件的路径

        ClientGlobal.init(tracker);

        TrackerClient trackerClient = new TrackerClient();

        //获得一个trackerServer的实例
        TrackerServer trackerServer = trackerClient.getConnection();

        //通过tracker获得一个Storage连接客户端
        StorageClient storageClient =  new StorageClient(trackerServer,null);

        String[] uploadInfos =  storageClient.upload_file("E:/image/u=2381566726,3189538030&fm=26&gp=0.jpg","jpg",null);

        String url = "http://192.168.164.131";
        for (String uploadInfo : uploadInfos) {
            url += "/"+uploadInfo;
        }
        System.out.println(url);
    }

}
