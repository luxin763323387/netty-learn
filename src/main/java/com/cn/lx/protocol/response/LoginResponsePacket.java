package com.cn.lx.protocol.response;

import com.cn.lx.protocol.command.Packet;
import lombok.Data;

import static com.cn.lx.protocol.command.Common.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;
    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
