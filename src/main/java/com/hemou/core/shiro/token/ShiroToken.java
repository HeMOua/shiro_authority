package com.hemou.core.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

public class ShiroToken extends UsernamePasswordToken implements Serializable {

    public ShiroToken(String username, String password) {
        super(username, password);
    }

    public String getPassWord(){
        return String.valueOf(this.getPassword());
    }
}
