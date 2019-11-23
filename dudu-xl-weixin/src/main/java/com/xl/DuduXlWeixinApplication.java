package com.xl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan//在SpringBootApplication使用此注解后, 项目中的servlet,Filter,Listener可以使用@WebServlet,@WebFilter,@WebListener注解,自动注册,无需其他代码
public class DuduXlWeixinApplication {

    public static void main(String[] args) {
        SpringApplication.run(DuduXlWeixinApplication.class, args);
    }

}
