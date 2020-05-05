package com.uetty.sample.springboot.entity;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
