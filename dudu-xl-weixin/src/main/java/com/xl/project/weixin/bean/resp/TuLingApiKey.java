package com.xl.project.weixin.bean.resp;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class TuLingApiKey {

    public Map<String,String> obtApiKey(){
        int line = 1;
        String temp =null;
        BufferedReader apiKey = null;
        Map<String,String> map = new HashMap<>();
        try {
            apiKey = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:/TuLingApiKey.txt"))));
            while ((temp = apiKey.readLine()) !=null){
                map.put("key"+line,temp);
                System.out.println(map.get("key"+line));
                line++;
            }
            apiKey.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(apiKey != null){
                try {
                    apiKey.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
        int line = 1;
        String temp =null;
        BufferedReader apiKey = null;
        Map<String,String> map = new HashMap<>();
        try {
            apiKey = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:/TuLingApiKey.txt"))));
            while ((temp = apiKey.readLine()) !=null){
                map.put("key"+line,temp);
                System.out.println(map.get("key"+line));
                line++;
            }
            apiKey.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(apiKey != null){
                try {
                    apiKey.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }




}
