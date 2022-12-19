package com.cn.lx.protocol.command;

import com.cn.lx.protocol.request.LoginRequestPacket;
import com.cn.lx.serializer.Serializer;
import com.cn.lx.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;


public class PacketCodeCTest {
    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("123");
        loginRequestPacket.setUserName("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT ,loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}
