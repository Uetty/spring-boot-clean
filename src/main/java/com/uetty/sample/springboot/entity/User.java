package com.uetty.sample.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uetty.sample.springboot.enums.UserStatusEnum;
import com.uetty.sample.springboot.enums.UserSysRoleEnum;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class User implements UserDetails, CredentialsContainer {

    private String id;
    private String username;
    private String displayName;
    private String password;
    private String phoneNumber;
    private String email;
    private UserStatusEnum status;
    private UserSysRoleEnum sysRole;
    private Date lastLoginTime;
    private Integer loginFailedTimes;
    private Date createTime;
    private Date updateTime;

    List<Role> roleList = new ArrayList<>();

    public String getId () {
        return this.id;
    }
    public void setId (String id) {
        this.id = id;
    }
    public String getUsername () {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername (String username) {
        this.username = username;
    }
    public String getDisplayName () {
        return this.displayName;
    }
    public void setDisplayName (String displayName) {
        this.displayName = displayName;
    }

    @Override
    public Collection<Role> getAuthorities() {
        return roleList;
    }

    @JsonIgnore
    public String getPassword () {
        return this.password;
    }
    public void setPassword (String password) {
        this.password = password;
    }
    public String getPhoneNumber () {
        return this.phoneNumber;
    }
    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail () {
        return this.email;
    }
    public void setEmail (String email) {
        this.email = email;
    }
    public UserStatusEnum getStatus () {
        return this.status;
    }
    public void setStatus (UserStatusEnum status) {
        this.status = status;
    }
    public UserSysRoleEnum getSysRole () {
        return this.sysRole;
    }
    public void setSysRole (UserSysRoleEnum sysRole) {
        this.sysRole = sysRole;
    }
    public Date getLastLoginTime () {
        return this.lastLoginTime;
    }
    public void setLastLoginTime (Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public Integer getLoginFailedTimes () {
        return this.loginFailedTimes;
    }
    public void setLoginFailedTimes (Integer loginFailedTimes) {
        this.loginFailedTimes = loginFailedTimes;
    }
    public Date getCreateTime () {
        return this.createTime;
    }
    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime () {
        return this.updateTime;
    }
    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}