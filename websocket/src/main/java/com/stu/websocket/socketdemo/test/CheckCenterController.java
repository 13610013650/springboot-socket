package com.stu.websocket.socketdemo.test;

import com.stu.websocket.socketdemo.common.ApiReturnResult;
import com.stu.websocket.socketdemo.config.WebSocketServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @program: socket-demo
 * @description: 推送消息 接口
 * @author: Mr.Zhang
 * @create: 2019-10-23 22:33
 **/
@RestController
@RequestMapping(value = "/websocket")
public class CheckCenterController {
    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }

    //推送数据接口
    @ResponseBody
    @RequestMapping("/push/{cid}")
    public ApiReturnResult<Object> pushToWeb(@PathVariable("cid") String cid, String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiReturnResult.error(cid+"#"+e.getMessage());
        }
        return ApiReturnResult.success(cid);
    }

}
