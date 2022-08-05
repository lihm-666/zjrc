package com.zjrc.lhm.Service;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;

public class HttpHandlerDome implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        //请求地址
        InetSocketAddress inetSocketAddress = httpExchange.getRemoteAddress();
        System.out.println("请求IP地址：" + inetSocketAddress);
        System.out.println("请求HOST：" + inetSocketAddress.getHostName());
        System.out.println("请求端口：" + inetSocketAddress.getPort());
        //i请求方式
        String requestMethod = httpExchange.getRequestMethod();
        System.out.println("请求方式：" + requestMethod);
        //url
        URI url = httpExchange.getRequestURI();
        System.out.println("URL:" + url);
        //客户端的请求是GET类型
        if (requestMethod.equalsIgnoreCase("GET")){
            //设置服务端响应的编码格式
            Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/html;charset=utf-8");

            String response = "这是一个服务！";

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.getBytes("UTF-8").length);

            OutputStream responseBody = httpExchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(responseBody,"UTF-8");
            writer.write(response);
            writer.close();
            responseBody.close();
        }else {
            //请求报文
            InputStream inputStream = httpExchange.getRequestBody();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i;
            while ((i = inputStream.read()) != -1){
                byteArrayOutputStream.write(i);
            }
            String requestMessage = byteArrayOutputStream.toString();
            System.out.println("请求报文：" + requestMessage);

            //返回报文
            String successMessage = "成功!";
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,successMessage.getBytes("UTF-8").length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(successMessage.getBytes("UTF-8"));
            outputStream.close();
            System.out.println("服务结束！");
        }
    }
}
