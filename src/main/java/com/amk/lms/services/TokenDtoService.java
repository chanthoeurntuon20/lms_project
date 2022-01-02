package com.amk.lms.services;

import com.amk.lms.exceptions.RefreshTokenException;
import com.amk.lms.models.entities.TokenDto;
import com.amk.lms.models.res.users.LoginRes;

public interface TokenDtoService {

    String createRefreshToken(String token, int userRef);

    boolean revokeToken(String RefreshToken) throws Exception;

    TokenDto verifyRefreshToken(String RefreshToken, boolean isRevoke) throws RefreshTokenException;

    LoginRes generateNewRefreshToken(String refreshToken) throws Exception;
}
