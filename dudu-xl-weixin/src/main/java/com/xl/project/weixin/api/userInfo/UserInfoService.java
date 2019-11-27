package com.xl.project.weixin.api.userInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xl.po.Weiuser;
import com.xl.project.weixin.api.accessToken.AccessTokenThread;
import com.xl.project.weixin.util.WeixinUtil;
import com.xl.service.WeiuserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    //@Autowired
    //private AccessTokenRedis accessTokenRedis; //使用redis数据库时 导入

    @Autowired
    private WeiuserService weiuserService;
    private static final String USER_INFO_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    public void userInfo(String openid){
        Weiuser weiuserBean =  weiuserService.selectByOpenid(openid);
        if (weiuserBean==null){
            //1 obt jsonObject
            JSONObject jsonObject = obtJSONObjectByOpenid(openid);
            //2  jsonObject  weiuser
            Weiuser weiuser = obtJSONWeiuser(jsonObject);
            //3
            addWeiuser(weiuser);
        }else {
            //不做或者做更新
        }
    }



    /**
     * 根据openid获取用户信息
     * @param openid
     * @return
     */
    public JSONObject obtJSONObjectByOpenid(String openid){
        String url = USER_INFO_URL.replace("ACCESS_TOKEN",AccessTokenThread.accessTokenVal).replace("OPENID",openid);

        JSONObject infoJson = WeixinUtil.httpRequest(url,"GET",null);

        return infoJson;
    }

    /**
     * 将得到的用户jsonObject对象转成Weiuser对象
     * @param jsonObject
     * @return
     */
    public Weiuser obtJSONWeiuser(JSONObject jsonObject){
        Weiuser weiuser = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            weiuser = objectMapper.readValue(jsonObject.toString(),Weiuser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return weiuser;
    }

    /**
     * 添加个人信息
     * @param weiuser
     * @return
     */
    public int addWeiuser(Weiuser weiuser){

        return weiuserService.insertSelective(weiuser);
    }




}
