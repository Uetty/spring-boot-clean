package com.uetty.sample.springboot.entity;


import com.uetty.sample.springboot.enums.VerificationCodeType;
import lombok.Data;

import java.util.Date;

@Data
public class VerificationCode {

    private Long id;
    private String userId;
    private String value;
    private VerificationCodeType type;
    private Date createTime;
    private Date expireTime;

}