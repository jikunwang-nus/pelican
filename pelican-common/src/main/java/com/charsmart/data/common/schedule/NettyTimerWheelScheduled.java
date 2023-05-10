package com.charsmart.data.common.schedule;

import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/28 17:44
 */
public class NettyTimerWheelScheduled extends HashedWheelTimer implements Scheduled {
    @Override
    public void schedule(Runnable runnable, long delay, TimeUnit unit) {
        newTimeout(timeout -> {
            runnable.run();
        }, delay, unit);
    }
}
