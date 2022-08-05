package com.zjrc.lhm.Server.Service;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

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
        URI uri = httpExchange.getRequestURI();
        System.out.println("URL:" + uri);
        //客户端的请求是GET类型
        if (requestMethod.equalsIgnoreCase("GET")){
            //设置服务端响应的编码格式
            Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/html;charset=utf-8");

            try {
                List<Integer> list = getRequestParam(httpExchange);
                String response = list.get(0) + "+" + list.get(1) + " = " + list.get(2);

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.getBytes("UTF-8").length);
                try {
                    System.out.println(getRequestParam(httpExchange));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                OutputStream responseBody = httpExchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(responseBody,"UTF-8");
                writer.write(response);
                writer.close();
                responseBody.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
    /**
     * 获取请求参数
     * @param httpExchange
     * @return
     * @throws Exception
     */
    private List<Integer> getRequestParam(HttpExchange httpExchange) throws Exception {
        String paramStr = "";

        if (httpExchange.getRequestMethod().equals("GET")) {
            //GET请求读queryString
            paramStr = httpExchange.getRequestURI().getQuery();
        } else {
            //非GET请求读请求体
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), "utf-8"));
            StringBuilder requestBodyContent = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                requestBodyContent.append(line);
            }
            paramStr = requestBodyContent.toString();
        }

        Map<String, String> stringMap = getParamMap(paramStr);
        int a = Integer.parseInt(stringMap.get("a"));
        int b = Integer.parseInt(stringMap.get("b"));
        int result = a + b;
        List<Integer> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(result);

        return list;
    }

    private Map<String, String> getParamMap(String query) {
        if (query == null || query.isEmpty()) return Collections.emptyMap();

        return Stream.of(query.split("&"))
                .filter(s -> !s.isEmpty())
                .map(kv -> kv.split("=", 2))
                .collect(Collectors.toMap(x -> x[0], x-> x[1]));

    }
}
