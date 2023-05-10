package com.charsmart.data.cases.nio.chatroom.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午5:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class LoginMessageType extends Message {
    private String name;
    private String password;

    public LoginMessageType() {
    }

    public LoginMessageType(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public int getMessageType() {
        return LOGIN_MESSAGE_TYPE;
    }
}
