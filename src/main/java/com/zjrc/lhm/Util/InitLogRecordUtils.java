package com.zjrc.lhm.Util;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executor;

public class InitLogRecordUtils {
    public static void initLog() {
        FileInputStream fileInputStream = null;
        try {
            Properties prop = new Properties();
            //nacos远程地址
            String serverAddr = "192.168.44.128:8848";
            //Data Id
            String dataId = "Nacos_log4j.properties";
            String group = "zjrc_lhm_GROUP";
            prop.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
            ConfigService configService = NacosFactory.createConfigService(prop);
            String content = configService.getConfig(dataId, group, 5000);
            InputStream inputStream = new ByteArrayInputStream(content.getBytes("utf-8"));
            prop.load(inputStream);//加载键值对信息
            NacosListenerUtils.listener(configService,dataId,group);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
