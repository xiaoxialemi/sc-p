package com.xiaoxialemi.scnacosboot.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author xiaoxialemi
 * @ClassName NacosController.java
 * @Description
 * @createTime 2021年06月23日 20:41:00
 */
@RequestMapping("config")
@RestController
public class NacosController {

    @NacosValue(value = "${xiaoxialemi.url:www.baidu.com}",autoRefreshed = true)
    private String url;

    @NacosInjected
    private NamingService namingService;

    @RequestMapping("/url")
    public String testController(){
        return url;
    }

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

}
