package com.zjrc.lhm.Server.Service.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.zjrc.lhm.Server.Entry.Student;
import com.zjrc.lhm.Util.JDBCUtils;
import com.zjrc.lhm.Util.JsonTransformation;
import com.zjrc.lhm.Util.RequestInfoUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HttpHandlerUpdate implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        //请求地址
        RequestInfoUtils.requestInfo(httpExchange);
        //请求方式
        String requestMethod = httpExchange.getRequestMethod();
        System.out.println("请求方式：" + requestMethod);
        //url
        URI uri = httpExchange.getRequestURI();
        System.out.println("URL:" + uri);
        //客户端的请求是GET类型
        if (requestMethod.equalsIgnoreCase("GET")){
            //设置服务端响应的编码格式
            Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/html;charset=utf-8");

            String response = "请求方式不支持该服务！";

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_LENGTH_REQUIRED,response.getBytes("UTF-8").length);

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
            //将获取的json字符串转为bean对象
            String requestMessage = byteArrayOutputStream.toString();
            System.out.println("请求报文：" + requestMessage);
            Student student = JSON.parseObject(requestMessage, Student.class);

            //插入到数据库
            String sql = "update Student set name = ?, age = ?, sex = ?, address = ?, email = ?, hobby = ?, phone = ?, score = ? where id = ?";
            Connection connection = JDBCUtils.getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setObject(1,student.getName());
                preparedStatement.setObject(2,student.getAge());
                preparedStatement.setObject(3,student.getSex());
                preparedStatement.setObject(4,student.getAddress());
                preparedStatement.setObject(5,student.getEmail());
                preparedStatement.setObject(6,student.getHobby());
                preparedStatement.setObject(7,student.getPhone());
                preparedStatement.setObject(8,student.getScore());
                preparedStatement.setObject(9,student.getId());

                int success = preparedStatement.executeUpdate();

                String message = "";
                if (success > 0){
                    //返回报文
                    message = "修改数据成功!";

                }else {
                    message = "修改数据失败！";
                }
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,message.getBytes("UTF-8").length);
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(message.getBytes("UTF-8"));
                outputStream.close();
                JDBCUtils.close(preparedStatement,connection);
                System.out.println("服务结束！");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }




        }
    }
}
