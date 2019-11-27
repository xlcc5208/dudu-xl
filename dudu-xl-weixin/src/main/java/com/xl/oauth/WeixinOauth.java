package com.xl.oauth;

import com.xl.project.weixin.main.MenuManager;
import com.xl.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("oauth")
public class WeixinOauth {

    @RequestMapping("weixin")
    public void oauth(HttpServletResponse response){

        String redirect_uri = MenuManager.REAL_URL + "oauth/invoke";

        try {
            redirect_uri = URLEncoder.encode(redirect_uri,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //用户同意网页授权  ,获取code
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + MenuManager.appId +
                "&redirect_uri=" + redirect_uri +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=java10000" +
                "#wechat_redirect";
        try {
            //重定向
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("invoke")
    public String invoke(HttpServletRequest request){
        //得到code
       String code = request.getParameter("code");
       String state = request.getParameter("state");
        //通过code 获取 accesstoken
       String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
               "appid=" +  MenuManager.appId +
               "&secret=" + MenuManager.appSecret +
               "&code=" + code +
               "&grant_type=authorization_code";
       //发送请求
       JSONObject  jsonObject = WeixinUtil.httpRequest(url,"GET",null);
       //得到accesstoken
       String access_token = jsonObject.getString("access_token");
       //得到openId
       String openId = jsonObject.getString("openid");

       String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?" +
               "access_token=" + access_token +
               "&openid=" + openId +
               "&lang=zh_CN";
        System.out.println(userInfoUrl);
       JSONObject infoJson = WeixinUtil.httpRequest(userInfoUrl,"GET",null);
        System.out.println(infoJson);
       request.setAttribute("infoJson",infoJson);
       return "oauth";
    }

}
