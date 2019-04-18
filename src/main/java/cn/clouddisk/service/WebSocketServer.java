package cn.clouddisk.service;

import cn.clouddisk.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ServerEndpoint("/chat/{fromUser}")
@Service
public class WebSocketServer {
    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static Map<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收user
    private String fromUser = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("fromUser") String fromUser) {
        this.session = session;
        webSocketMap.put(fromUser, this);     //加入map中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:" + fromUser + ",当前在线人数为" + getOnlineCount());
        this.fromUser = fromUser;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userList", webSocketMap.keySet());
            jsonObject.put("flag", "addUser");
            sendInfo(jsonObject.toJSONString(), fromUser, fromUser);
            sendInfo("{\"userList\":[\"" + fromUser + "\"],\"flag\":\"addUser\"}", fromUser, "others");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(this.fromUser);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        try {
            sendInfo("{\"userList\":[\"" + this.fromUser + "\"],\"flag\":\"removeUser\"}", fromUser, "others");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        if (StringUtils.isNotBlank(message)) {
            JSONArray messages = JSONArray.parseArray(message);
            for (int i = 0; i < messages.size(); i++) {
                try {
                    //解析发送的报文
                    JSONObject messagesJSONObject = messages.getJSONObject(i);
                    String toUser = messagesJSONObject.getString("toUser");
                    String contentText = messagesJSONObject.getString("contentText");
                    //传送给对应用户的websocket
                    if (StringUtils.isNotBlank(toUser) && StringUtils.isNotBlank(contentText)) {
                        WebSocketServer eSocket = webSocketMap.get(toUser);
                        //需要进行转换，fromUser
                        if (eSocket != null) {
                            messagesJSONObject.remove("toUser");
                            messagesJSONObject.put("fromUser", this.fromUser);
                            eSocket.sendMessage(JSON.toJSONString(messagesJSONObject));
                            //此处可以放置相关业务代码，例如存储到数据库
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("fromUser") String fromUser, @PathParam("toUser") String toUser) throws IOException {
        log.info("推送消息到窗口" + toUser + "，推送内容:" + message);

        try {
            //这里可以设定只推送给这个sid的，为null则全部推送
            if (toUser == null) {
                for (Map.Entry<String, WebSocketServer> item : webSocketMap.entrySet()) {
                    item.getValue().sendMessage(message);
                }
            } else if ("others".equals(toUser)) {
                for (Map.Entry<String, WebSocketServer> item : webSocketMap.entrySet()) {
                    if (!item.getValue().fromUser.equals(fromUser)) item.getValue().sendMessage(message);
                }
            } else {
                webSocketMap.get(toUser).sendMessage(message);
            }
        } catch (IOException e) {
//                continue;
        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
