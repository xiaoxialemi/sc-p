package com.xiaoxialemi.scnacoscloudservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaoxialemi
 * @ClassName
 * @Description
 * @createTime 2021年06月25日 11:30:00
 */
@FeignClient(name = "sc-nacos-cloud")
public interface MyFeignClient {

    @RequestMapping("/config/url")
    String url();

}
