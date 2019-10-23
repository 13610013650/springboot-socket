package com.stu.websocket.socketdemo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @program: socket-demo
 * @description: websocket配置文件
 * @author: Mr.Zhang
 * @create: 2019-10-23 01:59
 **/
@Component
@EnableWebSocket
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
