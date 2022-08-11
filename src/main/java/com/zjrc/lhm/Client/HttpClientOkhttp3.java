package com.zjrc.lhm.Client;

import com.zjrc.lhm.Util.OkHttpUtils;
import okhttp3.*;

import java.io.IOException;

public class HttpClientOkhttp3 {

    public static void main(String[] args) {
        System.out.println(doPost());
    }

    private static String doGet(){
        String result = OkHttpUtils.builder().url("http://127.0.0.1:8881/lhm/select")
                //参数可添加多个
                .addParam("id", "2")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .get()
                //同步还是异步
                .sync();
        return result;
    }
    private static String doPost(){
        String result = OkHttpUtils.builder().url("http://127.0.0.1:8881/lhm/insert")
                //参数可添加多个
                .addParam("name", "李宗洋")
                .addParam("age","22")
                .addParam("sex","男")
                .addParam("address","河南省南阳市")
                .addParam("hobby","运动和玩抽卡游戏")
                .addParam("score","100")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post(true)
                //同步还是异步
                .sync();
        return result;
    }
}
