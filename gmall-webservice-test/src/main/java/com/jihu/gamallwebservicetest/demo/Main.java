package com.jihu.gamallwebservicetest.demo;



import com.jihu.gamallwebservicetest.demo.service.impl.WeatherService;

import javax.xml.ws.Endpoint;

public class Main {

    public static void main(String[] args) {
        //发送天气服务
        Endpoint.publish("http://localhost:8090/ws_server/weather",new WeatherService());
        System.out.println("发布天气服务成功");
    }


}
