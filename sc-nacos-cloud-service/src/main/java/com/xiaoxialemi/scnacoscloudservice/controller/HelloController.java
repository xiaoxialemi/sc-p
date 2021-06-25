package com.xiaoxialemi.scnacoscloudservice.controller;

import com.xiaoxialemi.scnacoscloudservice.feign.MyFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoxialemi
 * @ClassName NacosConfigController.java
 * @Description
 * @createTime 2021年06月25日 09:59:00
 */
@RestController
@RefreshScope
public class HelloController {

    @Value(value = "${chinese}")
    private String chinese;
    @Value(value = "${english}")
    private String english;

    @Autowired
    private MyFeignClient myFeignClient;


    @RequestMapping("/hello/sayHi")
    public String url(@RequestParam(value = "language",required = true) String language){

        String url = myFeignClient.url();

        if(chinese.equals(language)){
            return chinese + " " + url;
        }else if (english.equals(language)){
            return english + " " + url;
        }
        return "你瞅啥";
    }

}
