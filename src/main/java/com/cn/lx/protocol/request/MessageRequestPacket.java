package com.cn.lx.protocol.request;

import com.cn.lx.protocol.command.Common;
import com.cn.lx.protocol.command.Packet;
import lombok.Data;

/**
 * 消息发送
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    public MessageRequestPacket(){};

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Common.MESSAGE_REQUEST;
    }
}
