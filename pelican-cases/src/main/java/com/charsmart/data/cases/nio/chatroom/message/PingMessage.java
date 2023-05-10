package com.charsmart.data.cases.nio.chatroom.message;

/**
 * @Author: Wonder
 * @Date: Created on 2022/3/3 下午4:47
 */
public class PingMessage extends Message {
    @Override
    public int getMessageType() {
        return PING_MESSAGE_TYPE;
    }
}
