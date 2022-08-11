package com.zjrc.lhm.Client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
/*
   因为HttpURLConnection是一个抽象类，所以不能被直接实例化，通过URL.openConnection()方法得到HttpURLConnection的父类，然后再强转成
HttpURLConnection对象实例。
   无论是post请求还是get请求，connect()函数实际上只是建立了一个与服务器的tcp连接，并没有实际发送http请求。但是也可以不通过connect()方法
来建立连接，因为getOutputStream()方法会隐式的进行connect。
   方法的调用顺序非常重要，对connection对象的一切配置(就是各种set方法)必须在connect()方法之前调用，getOutputStream()方法必须在
getInputStream()之前调用。
   前面说到了各种方法的调用顺序，那么为什么要这样呢。首先HttpURLConnection对象被创建，然后被指定各种选项（例如连接超时时间，是否使用缓存，
是否读取输出流等），然后与服务端建立连接。如果已经连接成功再设置这些选项将会报错。
   post请求参数是放在正文里面的，正文通过outputStream流写入的，实际上outputStream是个字符串流，往里面写入的东西不会立即发送到网络，
而是存在于内存缓冲区中，待outputStream流关闭时，根据输入的内容生成http正文。在getInputStream()函数调用的时候，就会把准备好的http
请求正式发送到服务器了，然后返回一个输入流，用于读取服务器对于此次http请求的返回信息。即使outputStream流没有关闭，在调用getInputStream()之后再写入参数也无效了。
   参数的内容一定要规范，不能胡乱修改，不然会出现 500的错误。

 */

public class HttpClientURL {

    private static URL url = null;
    private static HashMap<String, String> path_parma = new HashMap<String, String>();

    public static void main(String[] agrs) {
//        try {
//            url = new URL("http://127.0.0.1:8881/lhm/insert");
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        doPost(url);

        path_parma.put("id", "4");
        String path = "http://127.0.0.1:8881/lhm/select";
        String s = doGet(path);
        System.out.println(s);

    }

    private static String doPost(URL url) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        String line = null;
        try {

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true, 默认情况下是false;
            httpURLConnection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(8000);
            httpURLConnection.setRequestProperty("Content-type", "application/json");
            // 建立连接
            // httpURLConnection.connect();
            // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
            // 所以在开发中不调用上述的connect()也可以)。
            // 消息包中 发送数据
            //language=JSON
            String jsons = "{\n" +
                    "  \"name\": \"李振\",\n" +
                    "  \"age\": 21,\n" +
                    "  \"sex\": \"男\"\n" +
                    "}";
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsons.getBytes("utf-8"));

            // 接收数据，读取数据
            InputStream in = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder read = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                read.append(line);
            }
            line = read.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    private static String doGet(String path){
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            //GET请求直接在链接后面拼上请求参数
            String mPath = path + "?";
            int count = 0;
            for(String key:path_parma.keySet()){
                if (count == 0){
                    mPath += key + "=" + path_parma.get(key);
                }else {
                    mPath += "&" + key + "=" + path_parma.get(key);
                }

            }
            URL url = new URL(mPath);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut,也默认为false
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //连接服务器
            conn.connect();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输入流
        finally{
            try{
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

}