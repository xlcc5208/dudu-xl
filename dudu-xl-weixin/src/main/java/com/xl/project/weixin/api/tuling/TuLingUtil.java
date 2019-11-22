package com.xl.project.weixin.api.tuling;

import com.xl.project.weixin.api.tuling.bean.InputText;
import com.xl.project.weixin.api.tuling.bean.Perception;
import com.xl.project.weixin.api.tuling.bean.TuLingBean;
import com.xl.project.weixin.api.tuling.bean.UserInfo;
import com.xl.project.weixin.util.WeixinUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TuLingUtil {
    //图灵API接入地址
    private  static  final  String TULING_API_URL="http://openapi.tuling123.com/openapi/api/v2";

    public String sendMessage(String msg,String apiKey){
        //生产json对象 fb5a78bb2e79482d8075acd90b13231d
        JSONObject jsonObject = sendJsonObject(msg,apiKey);
        //对指定的API 地址发送post请求
        System.out.println(jsonObject.toString());
        JSONObject object = WeixinUtil.httpRequest(TULING_API_URL,"POST",jsonObject.toString());
        //System.out.println(object.toString());
        JSONArray jArray = (JSONArray) object.get("results");

        JSONObject object1 = (JSONObject) jArray.get(0);
        JSONObject object2 = (JSONObject) object1.get("values");

        String result = (String) object2.get("text");

        return result;
    }

    /**
     *
     * @param msg 用户发送的文本
     * @param apiKey 机器人的apiKey
     * @return 要发送的json对象
     */
    public JSONObject sendJsonObject(String msg, String apiKey){

        InputText inputText =new InputText();
        inputText.setText(msg);

        Perception perception = new Perception();
        perception.setInputText(inputText);

        UserInfo userInfo = new UserInfo();
        userInfo.setApiKey(apiKey);
        userInfo.setUserId("java10000");

        TuLingBean tuLingBean = new TuLingBean();
        tuLingBean.setPerception(perception);
        tuLingBean.setUserInfo(userInfo);

        JSONObject jsonObject = JSONObject.fromObject(tuLingBean);
        return jsonObject;

    }






}
