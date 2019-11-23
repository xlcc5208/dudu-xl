package com.xl.project.weixin.api.accessToken;

import com.xl.project.weixin.main.MenuManager;
import com.xl.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;

public class AccessTokenThread extends Thread {
    //设置时间内获取一次accessToken
    public static String accessTokenVal;

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public void run(){

        while(true){

            try {
                accessTokenVal = getAccessTokenVal();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public String getAccessTokenVal(){
        String accessToken = ACCESS_TOKEN_URL.replace("APPID", MenuManager.appId).replace("APPSECRET",MenuManager.appSecret);
        JSONObject jsonObject = WeixinUtil.httpRequest(accessToken,"GET",null);
        accessToken = (String) jsonObject.get("access_token");
        return accessToken;
    }


}
