package com.xiaoxialemi.scxxljobservice.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxialemi
 * @Description
 * @createTime 2021年06月28日 11:34:00
 */
@Component
public class MyTestJob {

    private static Logger logger = LoggerFactory.getLogger(MyTestJob.class);

    /**
     * 创建job时JobHandler要保持和value的值相同
     */
    @XxlJob(value = "testJobHandler",init = "init",destroy = "destroy")
    public void testJob(){

        XxlJobHelper.log("xiaoxialemi, Test.");
        logger.info("log:xiaoxialemi, Test.");
    }

    public void init(){
        logger.info("init");
    }
    public void destroy(){
        logger.info("destroy");
    }
}
