package com.ruoyi.framework.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Semaphore;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * websocket 消息处理
 *
 * @author ruoyi
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer {

    private static ConcurrentHashMap<Long, CopyOnWriteArraySet<WebSocketServer>> userwebSocketMap = new ConcurrentHashMap<Long, CopyOnWriteArraySet<WebSocketServer>>();

    private static ConcurrentHashMap<Long, Integer> count = new ConcurrentHashMap<Long, Integer>();

    private Long userId;

    /*
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") final Long userId) {
        this.session = session;
        this.userId = userId;
        System.out.println("session:" + session);
        System.out.println("userId:" + userId);
        if (!exitUser(userId)) {
            initUserInfo(userId);
        } else {
            CopyOnWriteArraySet<WebSocketServer> webSocketTestSet = getUserSocketSet(userId);
            webSocketTestSet.add(this);
            userCountIncrease(userId);
        }
        System.out.println("有" + userId + "新连接加入！当前在线人数为" + getCurrUserCount(userId));
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        CopyOnWriteArraySet<WebSocketServer> webSocketTestSet = userwebSocketMap.get(userId);
        //从set中删除
        webSocketTestSet.remove(this);
        //在线数减1
        userCountDecrement(userId);
        System.out.println("有一连接关闭！当前在线人数为" + getCurrUserCount(userId));
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        CopyOnWriteArraySet<WebSocketServer> webSocketSet = userwebSocketMap.get(userId);
       /* System.out.println("来自客户端" + userId + "的消息:" + message);
        //群发消息
        for (WebSocketTest item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }*/
    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */

    public void sendMessage(String message) throws IOException {
        System.out.println("服务端推送" + userId + "的消息:" + message);
        this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。   我是在有代办消息时 调用此接口 向指定用户发送消息
     *
     * @param message
     * @throws IOException
     */

    public void sendMessage(Long userId, String message) {
        System.out.println("服务端推送" + userId + "的消息:" + message);
        CopyOnWriteArraySet<WebSocketServer> webSocketSet = userwebSocketMap.get(userId);
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.session.getBasicRemote().sendText(message);
//                item.session.getBasicRemote().sendObject();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }


    public boolean exitUser(Long userId) {
        return userwebSocketMap.containsKey(userId);
    }

    public CopyOnWriteArraySet<WebSocketServer> getUserSocketSet(Long userId) {
        return userwebSocketMap.get(userId);
    }

    public void userCountIncrease(Long userId) {
        if (count.containsKey(userId)) {
            count.put(userId, count.get(userId) + 1);
        }
    }


    public void userCountDecrement(Long userId) {
        if (count.containsKey(userId)) {
            count.put(userId, count.get(userId) - 1);
        }
    }

    public void removeUserCount(Long userId) {
        count.remove(userId);
    }

    public Integer getCurrUserCount(Long userId) {
        return count.get(userId);
    }

    private void initUserInfo(Long userId) {
        CopyOnWriteArraySet<WebSocketServer> webSocketTestSet = new CopyOnWriteArraySet<WebSocketServer>();
        webSocketTestSet.add(this);
        userwebSocketMap.put(userId, webSocketTestSet);
        count.put(userId, 1);
    }
}
