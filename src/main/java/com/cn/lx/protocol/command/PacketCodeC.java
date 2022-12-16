package com.cn.lx.protocol.command;

import com.cn.lx.protocol.request.LoginRequestPacket;
import com.cn.lx.protocol.request.MessageRequestPacket;
import com.cn.lx.protocol.response.LoginResponsePacket;
import com.cn.lx.protocol.response.MessageResponsePacket;
import com.cn.lx.serializer.Serializer;
import com.cn.lx.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();
    public static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Common.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Common.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Common.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Common.MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }


    /**
     * 首先，我们需要创建一个 ByteBuf，这里我们调用 Netty 的 ByteBuf 分配器来创建，ioBuffer() 方法会返回适配 io 读写相关的内存，它会尽可能创建一个直接内存，直接内存可以理解为不受 jvm 堆管理的内存空间，写到 IO 缓冲区的效果更高。
     * 接下来，我们将 Java 对象序列化成二进制数据包。
     * 最后，我们对照本小节开头协议的设计以及上一小节 ByteBuf 的 API，逐个往 ByteBuf 写入字段，即实现了编码过程，到此，编码过程结束。
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBufAllocator byteBufAllocator,Packet packet){

        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf =byteBufAllocator.ioBuffer();

        // 2.进行序列化
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3.实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public void encode(ByteBuf byteBuf,Packet packet){

        // 1.进行序列化
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2.实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    /**
     * 我们假定 decode 方法传递进来的 ByteBuf 已经是合法的（在后面小节我们再来实现校验），即首四个字节是我们前面定义的魔数 0x12345678，这里我们调用 skipBytes 跳过这四个字节。
     * 这里，我们暂时不关注协议版本，通常我们在没有遇到协议升级的时候，这个字段暂时不处理，因为，你会发现，绝大多数情况下，这个字段几乎用不着，但我们仍然需要暂时留着。
     * 接下来，我们调用 ByteBuf 的 API 分别拿到序列化算法标识、指令、数据包的长度。
     * 最后，我们根据拿到的数据包的长度取出数据，通过指令拿到该数据包对应的 Java 对象的类型，根据序列化算法标识拿到序列化对象，将字节数组转换为 Java 对象，至此，解码过程结束。
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf){
        // 跳过魔数
        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;

    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }

}
