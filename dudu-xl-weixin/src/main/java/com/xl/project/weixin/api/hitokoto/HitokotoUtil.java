package com.xl.project.weixin.api.hitokoto;


import com.xl.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class HitokotoUtil {

    private static final String HITOKOTO_API_URLP = "https://v1.hitokoto.cn/?c=f";

    public String sendMessage(){

        JSONObject jsonObject = WeixinUtil.httpRequest(HITOKOTO_API_URLP,"GET",null);
        String string = (String) jsonObject.get("hitokoto");

        return string;
    }



}
