package com.example.demo.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PasswordResetToken;

@Repository("passwordResetTokenRepository")
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    void deleteByUserEmail(String email);

    @Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.expirationTime <= :expirationTime")
    void deleteByExpirationTime(@Param("expirationTime") LocalDateTime expirationTime);
    PasswordResetToken findByUserEmail(String email);
}
