package com.jihu.gamallwebservicetest.demo.service.impl;


import com.jihu.gamallwebservicetest.demo.service.WebServiceimpl;

import javax.jws.WebService;

/**
 * WebService天气服务实现类
 */
@WebService
public class WeatherService implements WebServiceimpl {


    @Override
    public String getWeatherByCityname(String name) {
        return name+"天气晴朗";
    }
}
