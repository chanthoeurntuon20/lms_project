package com.amk.lms.Repositories;

import com.amk.lms.models.entities.TokenDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenDto, Integer> {
    TokenDto findByToken(String token);

    TokenDto findByRefreshToken(String RefreshToken);
}
