package com.xiaoxialemi.scxxljobservice.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        //获取参数，参数可以自己在创建的时候定义格式
        String paramString = XxlJobHelper.getJobParam();
        JSONObject param = JSON.parseObject(paramString);
        if("1".equals(param.getString("jobType"))){
            logger.info("jobType:1");
        } else if ("2".equals(param.getString("jobType"))){
            //.......
        }


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
