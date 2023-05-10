package com.charsmart.data.common.schedule;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/28 17:24
 */
public interface Scheduled {
    void schedule(Runnable runnable, long delay, TimeUnit unit);
}
