package com.charsmart.data.cases.nio.chatroom.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/25 上午11:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMessage extends Message {
    private String userName;
    private String sendName;
    private String msg;

    public ChatMessage(String userName, String sendName, String msg) {
        this.userName = userName;
        this.sendName = sendName;
        this.msg = msg;
    }

    @Override
    public int getMessageType() {
        return CHAT_MESSAGE_TYPE;
    }
}
