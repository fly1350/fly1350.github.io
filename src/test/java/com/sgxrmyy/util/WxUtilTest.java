package com.sgxrmyy.util;

import com.sgxrmyy.domain.AccessToken;
import org.junit.Test;

/**
 * Created by loverabbit on 2017/6/16.
 */
public class WxUtilTest {

    @Test
    public void getAccessToken() throws InterruptedException {
        AccessToken accessToken = WxUtil.getAccessToken();
        System.out.println(accessToken.getAccessToken());
        System.out.println(accessToken.getExpiresIn());
        accessToken = WxUtil.getAccessToken();
        System.out.println(accessToken.getAccessToken());
        System.out.println(accessToken.getExpiresIn());
        Thread.sleep(6000);
        accessToken = WxUtil.getAccessToken();
        System.out.println(accessToken.getAccessToken());
        System.out.println(accessToken.getExpiresIn());
    }
}