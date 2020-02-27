package com.vion.backend.repository;

import com.vion.backend.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    boolean existsByToken(String token);
    void deleteByToken(String token);
}
