package com.sgxrmyy.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.sgxrmyy.domain.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by loverabbit on 2017/6/15.
 */
@Slf4j
public class WxUtil {

    enum MethodName {
        GET, POST
    }

    private static AccessToken accessToken = new AccessToken();
    private static long getAccessTokenTime;

    public static final String TOKEN = "sgxrmyy";
    public static final String ACCESS_TOKEN_STR = "ACCESS_TOKEN";
    public static final String APPID = "wx70dc5b4ec26d8182";
    public static final String APPSECRET = "ae0a72c69b1adf07cea74d13b2fac58f";
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
    public static final String SERVER_ADDRESS_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static final String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    public static boolean checkWX(String signature, String timestamp, String nonce) {
        String[] strArr = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(strArr);
        StringBuffer temp = new StringBuffer();
        for (String s : strArr) {
            temp.append(s);
        }
        return Sha.SHA1(temp.toString()).equals(signature);
    }

    public static AccessToken getAccessToken() {
        if (beOverdue()) {
            getAccessTokenTime = new Date().getTime();
            accessToken = doGetStr(accessToken, ACCESS_TOKEN_URL);
        }

        return accessToken;
    }

    private static boolean beOverdue() {
        Date date = new Date();
        long time = date.getTime();
        if (accessToken.getAccessToken() != null) {

            long expiresIn = accessToken.getExpiresIn() * 1000 + 5000;
            return getAccessTokenTime + expiresIn >= time;
        }
        return true;
    }


    public static <E> E doGetStr(E e, String url) {
        CloseableHttpClient httpClient = HttpUtil.getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //执行get请求
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //响应状态
//            System.out.println("status:" + httpResponse.getStatusLine());
            //判断响应实体是否为空
            if (entity != null) {
//                System.out.println("contentEncoding:" + entity.getContentEncoding());
//                System.out.println("response content:" + EntityUtils.toString(entity));
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
                return (E) objectMapper.readValue(EntityUtils.toString(entity, "UTF-8"), e.getClass());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            HttpUtil.releaseHttpClient(httpClient);
        }
        return null;
    }

    public static <E> E doPostStr(E e, String url, String outStr) {
        CloseableHttpClient httpClient = HttpUtil.getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
            //执行post请求
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //响应状态
//            System.out.println("status:" + httpResponse.getStatusLine());
            //判断响应实体是否为空
            if (entity != null) {
//                System.out.println("contentEncoding:" + entity.getContentEncoding());
//                System.out.println("response content:" + EntityUtils.toString(entity));
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
                return (E) objectMapper.readValue(EntityUtils.toString(entity, "UTF-8"), e.getClass());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            HttpUtil.releaseHttpClient(httpClient);
        }
        return null;
    }


}

