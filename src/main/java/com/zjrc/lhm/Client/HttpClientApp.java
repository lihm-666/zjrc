package com.zjrc.lhm.Client;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * lhm
 *
 */
public class HttpClientApp
{
    private static final Logger logger = LoggerFactory.getLogger(HttpClientApp.class);
    public static void main( String[] args )
    {
        String url="http://127.0.0.1:8881/lhm/test";
        String requestStr="httpserver测试练习";
        String contentType="application/json";
        String charset="UTF-8";
        //language=JSON
        String jsons = "{\n" +
                "  \"name\": \"刘克寒\",\n" +
                "  \"age\": 21,\n" +
                "  \"sex\": \"男\"\n" +
                "}";
        String ss=doPost( url,  jsons,  contentType,  charset) ;


        System.out.println("返回内容为：" +ss);
    }
    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url     请求的URL地址
     * @param jsons  请求的参数,可以为null
     * @param charset 字符集
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, String jsons, String contentType, String charset) {
        //使用HttpClient发送请求
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        try {
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000); // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(30000); // 设置读数据超时时间(单位毫秒)

            method.setRequestEntity(new StringRequestEntity(jsons, contentType, charset));

            client.executeMethod(method);
            System.out.println("返回的状态码为：" +method.getStatusCode());
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                String resultStr= IOUtils.toString(method.getResponseBodyAsStream(),charset);
                System.out.println("resultStr:"+resultStr);
                return resultStr;

            }
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1.getMessage());
            return "";
        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！" + e.toString());
            return "";
        } finally {
            method.releaseConnection();
        }
        return null;
    }
    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url     请求的URL地址
     * @param reqStr  请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doPostMuStr(String url, Part[] reqStr) {
        HttpClient client = new HttpClient();

        PostMethod method = new PostMethod(url);
        try {
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000); // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(30000); // 设置读数据超时时间(单位毫秒)
            method.setRequestEntity(new MultipartRequestEntity(reqStr, method.getParams()));
            client.executeMethod(method);
            System.out.println("返回的状态码为：" +method.getStatusCode());
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                return IOUtils.toString(method.getResponseBodyAsStream(),"UTF-8");
            }
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1.getMessage());
            return "";
        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！" + e.toString());
            return "";
        } finally {
            method.releaseConnection();
        }
        return null;
    }
}

