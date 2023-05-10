package com.charsmart.data.cases.nio.chatroom.protocal;

import com.charsmart.data.cases.nio.chatroom.message.LoginMessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午6:00
 */
public class TestSelfIdentifiedDecoder {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new SelfIdentifiedDecoder()
        );
        LoginMessageType message = new LoginMessageType("wonder", "123456");
        channel.writeOutbound(message);
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new SelfIdentifiedDecoder().encode(null, message, buf);

        ByteBuf s1 = buf.slice(0, 100);
        ByteBuf s2 = buf.slice(100, buf.readableBytes() - 100);
        s1.retain();
        channel.writeInbound(s1);
        channel.writeInbound(s2);
    }
}
