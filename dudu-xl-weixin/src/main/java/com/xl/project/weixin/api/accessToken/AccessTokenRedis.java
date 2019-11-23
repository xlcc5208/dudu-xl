package com.xl.project.weixin.api.accessToken;


import com.xl.project.weixin.main.MenuManager;
import com.xl.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class AccessTokenRedis {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> string;

    private static final String REDIS_ACCESS_TOKEN_KEY = "accessToken";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //在redis中获取accessToken
    public String obtRedisAccessToken(){
        if (redisTemplate.hasKey(REDIS_ACCESS_TOKEN_KEY)){
            String accessToken = string.get(REDIS_ACCESS_TOKEN_KEY);
            System.out.println("redis中查询:");
            return accessToken;
        }else {
            String accessTokenVal = obtWeiXinAccessToken();
            string.set(REDIS_ACCESS_TOKEN_KEY,accessTokenVal,5, TimeUnit.SECONDS);
            System.out.println("weinxin中获取:");
            return accessTokenVal;
        }
    }

    //去微信获取accessToken
    private String obtWeiXinAccessToken(){
        String accessTokenUrl = ACCESS_TOKEN_URL.replace("APPID", MenuManager.appId).replace("APPSECRET",MenuManager.appSecret);
        JSONObject jsonObject = WeixinUtil.httpRequest(accessTokenUrl,"GET",null);
        String accessToken = (String) jsonObject.get("access_token");
        return accessToken;
    }



}
