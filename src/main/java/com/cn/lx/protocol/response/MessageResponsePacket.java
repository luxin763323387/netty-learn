package com.cn.lx.protocol.response;

import com.cn.lx.protocol.command.Common;
import com.cn.lx.protocol.command.Packet;
import lombok.Data;

/**
 * 消息响应
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Common.MESSAGE_RESPONSE;
    }
}
