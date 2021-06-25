package com.xiaoxialemi.scnacosboot.config;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxialemi
 * @ClassName NacosConfig.java
 * @Description
 * @createTime 2021年06月23日 20:36:00
 */
@Configuration
@NacosPropertySource(dataId = "example",autoRefreshed = true)
public class NacosConfig {
}
