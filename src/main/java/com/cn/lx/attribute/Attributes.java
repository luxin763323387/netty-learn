package com.cn.lx.attribute;

import com.cn.lx.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
