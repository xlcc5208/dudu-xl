package com.xl.project.meeting.oauth;

import com.xl.po.User;
import com.xl.po.Weiuser;
import com.xl.project.weixin.main.MenuManager;
import com.xl.project.weixin.util.WeixinUtil;
import com.xl.service.UserService;
import com.xl.service.WeiuserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller("weixinOauth01")
@RequestMapping("oauth")
public class WeixinOauth {

    @Autowired
    private UserService userService;
    @Autowired
    private WeiuserService weiuserService;

    /**
     * 个人中心按钮
     * @param response
     */
    @RequestMapping("weixin/user")
    public void oauth(HttpServletResponse response){
        //回调链接
        String redirect_uri = MenuManager.REAL_URL + "oauth/weixin/user/invoke";

            try {
            redirect_uri = URLEncoder.encode(redirect_uri,"UTF-8");
        } catch (
        UnsupportedEncodingException e) {
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
        } catch (
        IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("weixin/user/invoke")
    public String invoke(HttpServletRequest request){
        //得到code
        String code = request.getParameter("code");
       // String state = request.getParameter("state");
        //通过code 获取 accesstoken
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" +  MenuManager.appId +
                "&secret=" + MenuManager.appSecret +
                "&code=" + code +
                "&grant_type=authorization_code";
        //发送请求
        JSONObject jsonObject = WeixinUtil.httpRequest(url,"GET",null);
        //得到accesstoken
        String access_token = jsonObject.getString("access_token");
        //得到openId
        String openId = jsonObject.getString("openid");

        Weiuser weiuser = weiuserService.selectByOpenid(openId);
        if (weiuser==null){
            //用户信息不存在,需要重新关注
            System.out.println("用户微信个人信息不存在");
        }else{
            //判断当前的微信用户是否进行登录(绑定)功能
            User user = userService.selectByWid(weiuser.getId());
            if (user==null){//判断是否绑定
                request.setAttribute("wid",weiuser.getId());
                return "weixin/login";
            }else {//已绑定 跳到目标页面
                request.setAttribute("user",user);
                return "weixin/user/userInfo";
            }

        }
        return "oauth";
    }

    /**
     * 会议发布
     * @param response
     */

    @RequestMapping("weixin/MeetingPub")
    public void oauthMeetingPub(HttpServletResponse response){
        //回调链接
        String redirect_uri = MenuManager.REAL_URL + "oauth/weixin/MeetingPub/invoke";

        try {
            redirect_uri = URLEncoder.encode(redirect_uri,"UTF-8");
        } catch (
                UnsupportedEncodingException e) {
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
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("weixin/MeetingPub/invoke")
    public String meetingPubInvoke(HttpServletRequest request){
        //得到code
        String code = request.getParameter("code");
        // String state = request.getParameter("state");
        //通过code 获取 accesstoken
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" +  MenuManager.appId +
                "&secret=" + MenuManager.appSecret +
                "&code=" + code +
                "&grant_type=authorization_code";
        //发送请求
        JSONObject jsonObject = WeixinUtil.httpRequest(url,"GET",null);
        //得到accesstoken
        String access_token = jsonObject.getString("access_token");
        //得到openId
        String openId = jsonObject.getString("openid");


        Weiuser weiuser = weiuserService.selectByOpenid(openId);
        if (weiuser==null){
            //数据库weiuser表中没有微信信息

        }else {
            User user = userService.selectByWid(weiuser.getId());
            if (user==null){//未登录(绑定) 功能
                request.setAttribute("wid",weiuser.getId());
                return "weixin/login";
            }else {//已绑定  判断角色是发单组还是抢单组
                if (1==user.getRid()){//发单 --> 发单页面
                    request.setAttribute("uid",user.getId());
                    return "weixin/meetingPub/meetingPub";
                }else if (2==user.getRid()){//抢单 --> 无权限页面

                    return "weixin/unauth";

                }else {
                    return "";//其它情况
                }
            }
        }
        return "oauth";
    }

}
