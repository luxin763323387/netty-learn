package com.cn.lx.client.handler;

import com.cn.lx.protocol.command.Packet;
import com.cn.lx.protocol.command.PacketCodeC;
import com.cn.lx.protocol.request.LoginRequestPacket;
import com.cn.lx.protocol.response.LoginResponsePacket;
import com.cn.lx.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 我们实现在客户端连接上服务端之后，立即登录。在连接上服务端之后，Netty 会回调到 ClientHandler 的 channelActive() 方法
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("flash");
        loginRequestPacket.setPassword("pwd");

        // 编码
        // ctx.alloc 获取分配器（allocate）
        ByteBuf encodeBuff = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 写数据
        // 获取当前链接(Netty 对连接的抽象为channel)
        // writeAndFlush() 把二进制数据写到服务端
        ctx.channel().writeAndFlush(encodeBuff);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + ": 客户端接受登录成功消息");
        ByteBuf byteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                //LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        }
    }
}
