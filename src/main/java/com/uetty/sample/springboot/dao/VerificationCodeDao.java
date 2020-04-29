package com.uetty.sample.springboot.dao;

import com.uetty.sample.springboot.entity.VerificationCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VerificationCodeDao {

    VerificationCode getById(Long id);

    boolean insert(VerificationCode verificationCode);

    boolean update(VerificationCode verificationCode);

    boolean delete(Long id);
}