package com.jihu.gamallwebservicetest.demo;

import com.jihu.gamallwebservicetest.demo.service.impl.WeatherService;
import com.jihu.gamallwebservicetest.demo.service.impl.WeatherServiceService;

public class ClitenTest {

    public static void main(String[] args) {
        WeatherServiceService factory = new WeatherServiceService();
        WeatherService servicePort = factory.getWeatherServicePort();
        String weather = servicePort.getWeatherByCityname("深圳");
        System.out.println(weather);
    }
}
