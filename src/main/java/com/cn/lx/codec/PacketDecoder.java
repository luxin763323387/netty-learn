package com.cn.lx.codec;

import com.cn.lx.protocol.command.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码，把二进制数据转换成Java对象
 */
public class PacketDecoder extends ByteToMessageDecoder {

    /**
     *
     * @param ctx
     * @param in
     * @param out 添加解码后的数据，传递到下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
        out.add(PacketCodeC.INSTANCE.decode(in));
    }
}
