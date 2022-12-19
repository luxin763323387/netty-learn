package com.cn.lx.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    // 用户唯一性标识
    private String userId;
    private String userName;

    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}
