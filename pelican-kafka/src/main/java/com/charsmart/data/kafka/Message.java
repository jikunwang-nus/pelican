package com.charsmart.data.kafka;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author: Wonder
 * @Date: Created on 2022/7/12 3:52 PM
 */
@Data
@Accessors(chain = true)
public class Message {
    private long id;
    private String message;
    private LocalDateTime createTime;
}
