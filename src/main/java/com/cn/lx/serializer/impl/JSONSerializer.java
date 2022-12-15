package com.cn.lx.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.cn.lx.serializer.Serializer;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return 1;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
