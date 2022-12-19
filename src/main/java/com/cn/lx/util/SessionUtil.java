package com.cn.lx.util;

import com.cn.lx.attribute.Attributes;
import com.cn.lx.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();


    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(),channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static Session unBindSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String  uid){
        return userIdChannelMap.get(uid);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 判断
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }
}
