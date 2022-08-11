package com.zjrc.lhm.Server;

import com.sun.net.httpserver.HttpServer;
import com.zjrc.lhm.Server.Service.Handler.*;
import com.zjrc.lhm.Util.InitLogRecord;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadHttpServer {
    //启动端口8881
    private static final int port = 8881;
    private static final String httpContext = "/lhm";
    private static final int nThread = 8;

    public static void main(String[] args) {
        HttpServer httpServer;
        InitLogRecord.initLog();
        try{
            httpServer = HttpServer.create(new InetSocketAddress(port),0);
            httpServer.createContext(httpContext + "/insert",new HttpHandlerInsert());
            httpServer.createContext(httpContext + "/add",new HttpHandlerAdd());
            httpServer.createContext(httpContext + "/select",new HttpHandlerSelect());
            httpServer.createContext(httpContext + "/update",new HttpHandlerUpdate());
            httpServer.createContext(httpContext + "/delete",new HttpHandlerDelete());
            //设置并发数
            ExecutorService executor = Executors.newFixedThreadPool(nThread);
            httpServer.setExecutor(executor);
            httpServer.start();

            System.out.println("服务器启动端口：" + port);
            System.out.println("服务器根节点：" + httpContext);
            System.out.println("服务器并发数：" + nThread);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
