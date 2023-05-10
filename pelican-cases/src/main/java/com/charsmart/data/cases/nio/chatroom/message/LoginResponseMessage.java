package com.charsmart.data.cases.nio.chatroom.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/25 上午11:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponseMessage extends Message {
    public LoginResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    private boolean success;
    private String reason;
    @Override
    public int getMessageType() {
        return LOGIN_RESPONSE_MESSAGE_TYPE;
    }
}
