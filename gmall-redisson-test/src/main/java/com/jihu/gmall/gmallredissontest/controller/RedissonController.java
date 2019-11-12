package com.jihu.gmall.gmallredissontest.controller;

import com.jihu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

/**
 * 用来测试Redisson
 */
@Controller
public class RedissonController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("testRedisson")
    @ResponseBody
    public String testRedisson(){
        Jedis jedis = redisUtil.getJedis();
        String v = jedis.get("k");
        if(StringUtils.isBlank(v)){
            v = "1";
        }
        System.out.println("->"+v);

        jedis.set("k",(Integer.parseInt(v)+1)+"");
        jedis.close();
        //RLock lock = redissonClient.getLock("lock");
        return "success";
    }
}
