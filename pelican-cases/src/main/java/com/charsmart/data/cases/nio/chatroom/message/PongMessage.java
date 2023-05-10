package com.charsmart.data.cases.nio.chatroom.message;

/**
 * @Author: Wonder
 * @Date: Created on 2022/3/3 下午4:48
 */
public class PongMessage extends Message {
    @Override
    public int getMessageType() {
        return PONG_MESSAGE_TYPE;
    }
}
