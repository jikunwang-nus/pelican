package com.charsmart.data.common.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/28 17:37
 */
public class JDKExecutorScheduled implements Scheduled {
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

    @Override
    public void schedule(Runnable runnable, long delay, TimeUnit unit) {
        scheduledExecutorService.schedule(runnable, delay, unit);
    }
}
