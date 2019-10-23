package com.stu.websocket.socketdemo.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: socket-demo
 * @description: websocket服务
 * @author: Mr.Zhang
 * @create: 2019-10-23 18:24
 **/

@Slf4j
@Component
@ServerEndpoint("/websocket/{sid}")
public class WebSocketServer {

    /**  客户端在线人数 */
    private static int onlineCount = 0;
    /** concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    /** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
    private Session session;
    /** 接收sid */
    private String sid="";
    /**
    　* @description: //TODO 打开连接
    　* @param
    　* @return
    　* @author Mr.Zhang
    　* @date 2019/10/23 22:29
    　**/
    @OnOpen
    public void OnOpen(Session session, @PathParam("sid") String sid){
        this.session = session;
        webSocketSet.add(this);
        addOnLineCount();
        this.sid= sid;
        log.info("{}打开连接，在线用户人数为：{}",sid,getOnLineCount());
    }
    /**
    　* @description: //TODO  发送消息
    　* @param
    　* @return
    　* @author Mr.Zhang
    　* @date 2019/10/23 22:29
    　**/
    @OnMessage
    public void OnMessage(String message, Session session) throws IOException{
        log.info("获得的参数：{}",message);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
    　* @description: //TODO 客户端关闭
    　* @param
    　* @return
    　* @author Mr.Zhang
    　* @date 2019/10/23 22:44
    　**/
    @OnClose
    public void OnClose(){
        webSocketSet.remove(this);
        subOnLineCount();
        log.info("有用户关闭连接！当前在线人数为：{}",getOnLineCount());
    }


    /** 连接异常回调此方法 */
    @OnError
    public void OnError(Session session, Throwable error){
        log.info("连接异常：{}",error.getMessage());
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
        log.info("推送消息到窗口"+sid+"，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(sid==null) {
                    item.sendMessage(message);
                }else if(item.sid.equals(sid)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
    　* @description: //TODO 发送消息
    　* @param
    　* @return
    　* @author Mr.Zhang
    　* @date 2019/10/23 22:27
    　**/
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
    　* @description: //TODO 上线客户端加加
    　* @param
    　* @return
    　* @author Mr.Zhang
    　* @date 2019/10/23 18:49
    　**/
    public static synchronized void addOnLineCount(){
        WebSocketServer.onlineCount++;
    }

    /**
    　* @description: //TODO 上线客户端减
    　* @param
    　* @return
    　* @author Mr.Zhang
    　* @date 2019/10/23 18:49
    　**/
    public static synchronized  void subOnLineCount(){
        WebSocketServer.onlineCount--;
    }
    /**
    　* @description: //TODO 获取在线人数
    　* @param
    　* @return
    　* @author Mr.Zhang
    　* @date 2019/10/23 18:48
    　**/
    public static synchronized int getOnLineCount(){
        return WebSocketServer.onlineCount;
    }
}
