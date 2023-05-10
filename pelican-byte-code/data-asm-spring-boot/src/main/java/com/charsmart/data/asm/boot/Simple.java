package com.charsmart.data.asm.boot;

import com.charsmart.data.asm.boot.annotation.Task;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/12 5:14 PM
 */
@Task
@Service
@Data
@Accessors(chain = true)
public class Simple {
    private String id;
    private String code;
}
