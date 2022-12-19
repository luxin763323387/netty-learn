package com.cn.lx.protocol.response;

import com.cn.lx.protocol.command.Common;
import com.cn.lx.protocol.command.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponsePacket extends Packet {

    private String message;

    private String fromUserId;

    private String fromUserName;

    @Override
    public Byte getCommand() {
        return Common.MESSAGE_RESPONSE;
    }
}
