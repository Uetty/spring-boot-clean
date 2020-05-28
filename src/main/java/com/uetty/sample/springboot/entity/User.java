package com.uetty.sample.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uetty.sample.springboot.enums.UserStatusEnum;
import com.uetty.sample.springboot.enums.UserSysRoleEnum;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    private String id;
    private String username;
    private String displayName;
    @JsonIgnore
    private String password;
    private String phoneNumber;
    private String email;
    private UserStatusEnum status;
    private UserSysRoleEnum sysRole;
    private Date lastLoginTime;
    private Integer loginFailedTimes;
    private Date createTime;
    private Date updateTime;

}