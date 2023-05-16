package com.charsmart.data.distributed.log.intercepter;

import com.charsmart.data.distributed.log.GlobalTrace;
import com.charsmart.data.distributed.log.context.GlobalTraceContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/10 16:53
 */
public class FeignCallRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        GlobalTrace trace = GlobalTraceContextHolder.get();
        if (trace != null) {
            long traceId = trace.getTraceId();
            template.header("trace-id", String.valueOf(traceId));
        }
    }
}
