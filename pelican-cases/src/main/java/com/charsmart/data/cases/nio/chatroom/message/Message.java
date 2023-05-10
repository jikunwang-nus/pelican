package com.charsmart.data.cases.nio.chatroom.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午5:31
 */
@Data
public abstract class Message implements Serializable {
    public abstract int getMessageType();

    private int sequenceId;
    private int messageType;

    static int LOGIN_MESSAGE_TYPE = 0;
    static int LOGIN_RESPONSE_MESSAGE_TYPE = 1;
    static int CHAT_MESSAGE_TYPE = 2;
    static int PING_MESSAGE_TYPE = 3;
    static int PONG_MESSAGE_TYPE = 4;
    static Map<Integer, Class<?>> clazzMap = new HashMap<>();

    static {
        clazzMap.put(0, LoginMessageType.class);
        clazzMap.put(1, LoginResponseMessage.class);
        clazzMap.put(2, ChatMessage.class);
        clazzMap.put(3, PingMessage.class);
        clazzMap.put(4, PongMessage.class);
    }

    public static Class<?> getRuntimeClass(int index) {
        return clazzMap.get(index);
    }
}
