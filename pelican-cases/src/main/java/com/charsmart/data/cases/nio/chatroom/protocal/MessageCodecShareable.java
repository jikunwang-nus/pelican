package com.charsmart.data.cases.nio.chatroom.protocal;

import com.charsmart.data.cases.nio.chatroom.message.Message;
import com.charsmart.data.cases.nio.chatroom.Config;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午6:34
 * 必须和LongFieldBasedFrameDecoder一起使用
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecShareable extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        /*1 4字节的magic num*/
        out.writeBytes(new byte[]{1, 2, 3, 4});
        /*2 1字节版本号*/
        out.writeByte(1);
        /*3 序列化算法*/
        Serializer.Algorithm algorithm = Config.getAlgorithm();
        int ordinal = algorithm.ordinal();
        out.writeByte(ordinal);
        /*4 1个字节指令类型 */
        out.writeByte(msg.getMessageType());
        /*5 4个字节序列号*/
        out.writeInt(msg.getSequenceId());
        out.writeByte(0);
        /*6 获取内容*/
        byte[] bytes = algorithm.serialize(msg);
        /*7 长度*/
        out.writeInt(bytes.length);
        /*8 写入内容*/
        out.writeBytes(bytes);
        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        /*默认只有一种序列化方式*/
        Serializer.Algorithm algorithm = Serializer.Algorithm.values()[serializerType];
        /*获取运行时类型*/
        Class<?> clazz = Message.getRuntimeClass(messageType);
        Object object = algorithm.deserialize(clazz, bytes);
        out.add(object);
    }
}
