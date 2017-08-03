package com.sgxrmyy.domain;

import lombok.Data;

/**
 * Created by loverabbit on 2017/6/16.
 */
@Data
public class AccessToken {

    private String accessToken;
    private int expiresIn;
}
