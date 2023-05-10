package com.charsmart.data.cases.nio.chatroom.protocal;

import com.charsmart.data.cases.nio.chatroom.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午5:27
 */
@Slf4j
public class SelfIdentifiedDecoder extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        /*1 4字节的magic num*/
        out.writeBytes(new byte[]{1, 2, 3, 4});
        /*2 1字节版本号*/
        out.writeByte(1);
        /*3 序列化算法*/
        out.writeByte(0);
        /*4 1个字节指令类型 */
        out.writeByte(msg.getMessageType());
        /*5 4个字节序列号*/
        out.writeInt(msg.getSequenceId());
        out.writeByte(0);
        /*6 获取内容*/
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();

        /*7 长度*/
        out.writeInt(bytes.length);
        /*8 写入内容*/
        out.writeBytes(bytes);

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
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message msg = (Message) ois.readObject();
        log.debug("{},{},{},{},{},{}", magicNum, version, serializerType, messageType, sequenceId, length);
        log.debug("{}", msg);
        out.add(msg);
    }
}
