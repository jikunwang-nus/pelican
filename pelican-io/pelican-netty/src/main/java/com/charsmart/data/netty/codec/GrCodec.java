package com.charsmart.data.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/23 10:29 PM
 */
public class GrCodec {
    private final ChannelHandler encoder = new GrEncoder();
    private final ChannelHandler decoder = new GrDecoder();

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }

    static class GrEncoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        }
    }

    static class GrDecoder extends MessageToByteEncoder {
        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
//            out.writeBytes((ByteBuf) msg);
        }
    }
}
