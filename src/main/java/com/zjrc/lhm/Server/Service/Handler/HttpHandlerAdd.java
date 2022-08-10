package com.zjrc.lhm.Server.Service.Handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.zjrc.lhm.Util.ParamMapUtils;
import com.zjrc.lhm.Util.RequestInfoUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpHandlerAdd implements HttpHandler {

    public void handle(HttpExchange httpExchange) {
        //请求地址
        RequestInfoUtils.requestInfo(httpExchange);
        //i请求方式
        String requestMethod = httpExchange.getRequestMethod();
        System.out.println("请求方式：" + requestMethod);
        //url
        URI uri = httpExchange.getRequestURI();
        System.out.println("URL:" + uri);
        List<Integer> list = null;
        try {
            //客户端的请求是GET类型
            if (requestMethod.equalsIgnoreCase("GET")){
                //设置服务端响应的编码格式
                Headers responseHeaders = httpExchange.getResponseHeaders();
                responseHeaders.set("Content-Type", "text/html;charset=utf-8");

                String requestMessage = uri.getQuery();
                list = getRequestParam(requestMessage);
                String response = list.get(0) + " + " + list.get(1) + " = " + list.get(2);

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
                //json格式，需转换
                String requestMessage = byteArrayOutputStream.toString();
                list = getRequestParam(requestMessage);
                String response = list.get(0) + " + " + list.get(1) + " = " + list.get(2);

                //返回报文
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.getBytes("UTF-8").length);
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(response.getBytes("UTF-8"));
                outputStream.close();
                System.out.println("服务结束！");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * 获取请求参数
     * @param paramStr
     * @return
     * @throws Exception
     */
    public List<Integer> getRequestParam(String paramStr) {

        Map<String, String> stringMap = ParamMapUtils.getParamMap(paramStr);
        int a = Integer.parseInt(stringMap.get("a"));
        int b = Integer.parseInt(stringMap.get("b"));
        int result = a + b;
        List<Integer> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(result);

        return list;
    }
}
