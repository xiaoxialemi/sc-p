package com.xiaoxialemi.scservicesample.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoxialemi
 * @ClassName ByeByeController.java
 * @Description
 * @createTime 2021年06月23日 18:30:00
 */
@RestController
public class ByeByeController {

    @RequestMapping("/byeBye/{name}")
    public String byeByeTo(@PathVariable("name") String name){

        return "byebye " + name;
    }

}
