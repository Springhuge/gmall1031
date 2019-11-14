package com.jihu.gamallwebservicetest.demo.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * WebService服务接口
 */
@WebService
public interface WebServiceimpl {

    @WebMethod
    String getWeatherByCityname(String name);
}
