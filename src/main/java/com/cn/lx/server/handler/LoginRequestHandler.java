package com.cn.lx.server.handler;

import com.cn.lx.protocol.request.LoginRequestPacket;
import com.cn.lx.protocol.response.LoginResponsePacket;
import com.cn.lx.session.Session;
import com.cn.lx.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * 登入请求类型
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String uid = randomUserId();
            loginResponsePacket.setUserId(uid);
            System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");
            SessionUtil.bindSession(new Session(uid, loginRequestPacket.getUserName()), ctx.channel());
            //LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
        ctx.channel().writeAndFlush(loginResponsePacket);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
