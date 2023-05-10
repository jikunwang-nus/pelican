package com.charsmart.data.cases.nio.chatroom.protocal;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午6:46
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolFrameDecoder() {
        this(1024, 12, 4, 0, 0);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
