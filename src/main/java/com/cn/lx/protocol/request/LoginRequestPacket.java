package com.cn.lx.protocol.request;

import com.cn.lx.protocol.command.Common;
import com.cn.lx.protocol.command.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Common.LOGIN_REQUEST;
    }
}
