package com.abdelrahman.myreads.MyReads.repository;

import com.abdelrahman.myreads.MyReads.model.PasswordVerificationCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordVerificationCodeRepository extends CrudRepository<PasswordVerificationCode, Long> {
    Optional<PasswordVerificationCode> findByCode(String code);

}
