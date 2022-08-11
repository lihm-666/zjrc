package com.zjrc.lhm.Server.Service.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.zjrc.lhm.Server.Entry.Student;
import com.zjrc.lhm.Util.JDBCUtils;
import com.zjrc.lhm.Util.ParamMapUtils;
import com.zjrc.lhm.Util.RequestInfoUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class HttpHandlerSelect implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        //请求地址
        RequestInfoUtils.requestInfo(httpExchange);
        //请求方式
        String requestMethod = httpExchange.getRequestMethod();
        System.out.println("请求方式：" + requestMethod);
        //url
        URI uri = httpExchange.getRequestURI();
        System.out.println("URL:" + uri);
        //数据库查询sql
        String sql = "select * from Student where id = ?";

        //客户端的请求是GET类型
        if (requestMethod.equalsIgnoreCase("GET")){
            //设置服务端响应的编码格式
            Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/html;charset=utf-8");

            //执行sql
            Map<String, String> paramMap = ParamMapUtils.getParamMap(uri.getQuery());
            List<Student> students = JDBCUtils.executeQuery(sql, new Student(), paramMap.get("id"));
            String response = JSON.toJSONString(students);

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
            //获取的数据是json类型
            String requestMessage = byteArrayOutputStream.toString();
            System.out.println("请求报文：" + requestMessage);

            JSONObject jsonObject = JSONObject.parseObject(requestMessage);
            String id = jsonObject.getString("id");
            //执行sql
            List<Student> students = JDBCUtils.executeQuery(sql, new Student(), id);

            //返回报文
            String response = JSON.toJSONString(students);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.getBytes("UTF-8").length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes("UTF-8"));
            outputStream.close();
            System.out.println("服务结束！");

        }
    }
}
