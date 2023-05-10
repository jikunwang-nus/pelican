package com.charsmart.data.cases.nio.chatroom.protocal;

import com.charsmart.data.cases.utils.JsonUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Author: Wonder
 * @Date: Created on 2022/3/3 下午5:23
 */
public interface Serializer {
    <T> byte[] serialize(T object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);

    enum Algorithm implements Serializer {
        Java {
            @Override
            public <T> byte[] serialize(T object) {

                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化异常", e);
                }
            }

            @Override
            @SuppressWarnings("unchecked")
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (ClassNotFoundException | IOException e) {
                    throw new RuntimeException("反序列化异常", e);
                }
            }
        },
        Json {
            @Override
            public <T> byte[] serialize(T object) {
                return JsonUtils.toJson(object).getBytes();
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                String json = new String(bytes);
                return JsonUtils.toObject(json, clazz);
            }
        }
    }
}
