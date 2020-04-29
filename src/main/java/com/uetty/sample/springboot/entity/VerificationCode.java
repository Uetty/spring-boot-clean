package com.uetty.sample.springboot.entity;


import com.uetty.sample.springboot.enums.VerificationCodeType;

import java.util.Date;

public class VerificationCode {

    private Long id;
    private String userId;
    private String value;
    private VerificationCodeType type;
    private Date createTime;
    private Date expireTime;

    public Long getId () {
        return this.id;
    }
    public void setId (Long id) {
        this.id = id;
    }
    public String getUserId () {
        return this.userId;
    }
    public void setUserId (String userId) {
        this.userId = userId;
    }
    public String getValue () {
        return this.value;
    }
    public void setValue (String value) {
        this.value = value;
    }
    public VerificationCodeType getType () {
        return this.type;
    }
    public void setType (VerificationCodeType type) {
        this.type = type;
    }
    public Date getCreateTime () {
        return this.createTime;
    }
    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }
    public Date getExpireTime () {
        return this.expireTime;
    }
    public void setExpireTime (Date expireTime) {
        this.expireTime = expireTime;
    }
}