package com.xiaoxialemi.scnacoscloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoxialemi
 * @ClassName NacosConfigController.java
 * @Description
 * @createTime 2021年06月25日 09:59:00
 */
@RestController
@RefreshScope
public class NacosConfigController {

    @Value(value = "${xiaoxialemi.url:http://www.youtube.com}")
    private String url;

    @RequestMapping("/config/url")
    public String url(){

        return url;
    }

}
