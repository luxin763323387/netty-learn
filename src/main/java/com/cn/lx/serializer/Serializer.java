package com.cn.lx.serializer;


import com.cn.lx.serializer.impl.JSONSerializer;

/**
 * 自定义序列化
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转化乘二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制转化Java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}
