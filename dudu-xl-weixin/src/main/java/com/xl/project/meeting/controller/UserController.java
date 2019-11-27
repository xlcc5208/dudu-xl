package com.xl.project.meeting.controller;

import com.xl.po.User;
import com.xl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("login")
    public String login(@RequestParam("email") final String email,
                        @RequestParam("wid") final Integer wid){
        User user = userService.selectByEmail(email);
        if (user!=null){//用户是否存在

            if (user.getWid()!=null){//判断邮箱是否已被绑定
                return "1";//1-> 表示邮箱已被帮绑定
            }else {
                userService.updateByEmail(wid,email);
                return "2";//绑定成功
            }
        }else {
            return "3";//该邮箱不存在,无法进行登录功能
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "updateUser")
    public String updateUser(User user){
        System.out.println(user);
        int num = userService.updateByPrimaryKeySelective(user);
        return num+"";
    }



}
