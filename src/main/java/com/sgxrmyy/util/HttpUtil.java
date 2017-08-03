package com.sgxrmyy.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by loverabbit on 2017/6/16.
 */
public class HttpUtil {

    enum MethodName{
        GET,POST,DELETE,PUT
    }

    public static CloseableHttpClient getHttpClient(){
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        return httpClientBuilder.build();
    }


    public static void releaseHttpClient(CloseableHttpClient closeableHttpClient){
        try {
            //关闭流并释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
