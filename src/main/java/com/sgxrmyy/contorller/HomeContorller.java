package com.sgxrmyy.contorller;

import com.sgxrmyy.util.WxUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by loverabbit on 2017/6/15.
 */
@Controller
public class HomeContorller {

    @ResponseBody
    @GetMapping("/wx")
    public String weixin(String signature, String timestamp, String nonce, String echostr) {
        System.out.println(signature + "|" + timestamp + "|" + nonce + "|" + echostr);

        boolean b = WxUtil.checkWX(signature, timestamp, nonce);
        if (b) {
            return echostr;
        }
        return null;
    }

    @GetMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }
}
