package com.stu.websocket.socketdemo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: socket-demo
 * @description: 测试
 * @author: Mr.Zhang
 * @create: 2019-10-23 00:58
 **/
@RestController
public class TestController {

    @GetMapping("test")
    public String test(){
        return "test";
    }


}
