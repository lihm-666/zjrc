package com.zjrc.lhm.Util;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;

public class RequestInfoUtils {

    public static void requestInfo(HttpExchange httpExchange){
        //请求地址
        InetSocketAddress inetSocketAddress = httpExchange.getRemoteAddress();
        System.out.println("请求IP地址：" + inetSocketAddress);
        System.out.println("请求HOST：" + inetSocketAddress.getHostName());
        System.out.println("请求端口：" + inetSocketAddress.getPort());
    }
}
