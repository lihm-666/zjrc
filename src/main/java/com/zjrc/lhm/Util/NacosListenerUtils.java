package com.zjrc.lhm.Util;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.io.IOException;
import java.util.concurrent.Executor;

public class NacosListenerUtils {

    public static void listener(ConfigService configService, String dataId, String group) throws NacosException {
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String s) {
                try {
                    JDBCUtils.reStartJdbc();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NacosException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
