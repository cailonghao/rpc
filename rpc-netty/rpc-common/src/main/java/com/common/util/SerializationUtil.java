package com.common.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cai
 * 序列化工具类
 * */
public class SerializationUtil {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    public SerializationUtil() {

    }
    /**
     * 序列化
     * */
    public static <T> byte[] serialize(T obj){
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<T> schema = getSchema(clazz);
        return ProtostuffIOUtil.toByteArray(obj,schema,buffer);
    }
    /**
     * 反序列化
     * */
    public static <T> T deserialize(byte[] data, Class<T> clazz){
        T message = objenesis.newInstance(clazz);
        Schema<T> schema = getSchema(clazz);
        ProtostuffIOUtil.mergeFrom(data,message,schema);
        return message;
    }
    /**
     * 文档描述
     * */
    private static <T> Schema<T> getSchema(Class<T> clazz){
        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
        if(schema == null){
            schema = RuntimeSchema.getSchema(clazz);
            cachedSchema.put(clazz,schema);
        }
        return schema;
    }
}
