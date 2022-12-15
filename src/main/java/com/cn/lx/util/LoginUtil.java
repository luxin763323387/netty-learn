package com.cn.lx.util;

import com.cn.lx.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * 登入util
 */
public class LoginUtil {

    /**
     * attr 赋值
     * @param channel
     */
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 获取 attr
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
