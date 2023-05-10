package com.charsmart.data.common.schedule;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/28 17:26
 */
public class TimerScheduled {
    private Timer timer = new Timer();

    public void schedule(Runnable runnable, long delay, TimeUnit unit) {
        if (unit == TimeUnit.SECONDS) {
            delay *= 1000;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delay);
    }
}
