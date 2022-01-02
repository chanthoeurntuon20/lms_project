package com.amk.lms.services.impl;

import com.amk.lms.Repositories.TokenRepository;
import com.amk.lms.exceptions.RefreshTokenException;
import com.amk.lms.models.entities.TokenDto;
import com.amk.lms.models.res.users.LoginRes;
import com.amk.lms.services.TokenDtoService;
import com.amk.lms.utils.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class TokenDtoServiceImpl implements TokenDtoService {

    @Value("${jwt.refresh.token.millisecond}")
    private long refreshms;

    @Autowired
    private TokenRepository tokenRepository;


    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Override
    public String createRefreshToken(String token, int userRef) {

        TokenDto refreshToken = new TokenDto();

        refreshToken.setExpired(Instant.now().plusMillis(refreshms));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setToken(token);
        refreshToken.setUserRef(userRef);

        tokenRepository.saveAndFlush(refreshToken);
        return refreshToken.getRefreshToken();
    }

    @Override
    public boolean revokeToken(String RefreshToken) throws Exception {


        TokenDto refreshToken = tokenRepository.findByRefreshToken(RefreshToken);
        if (refreshToken != null) {
            refreshToken.setIsRevoke("Y");
            tokenRepository.saveAndFlush(refreshToken);
            return true;
        } else
            throw new Exception("Refresh token not exist !");
    }

    @Override
    public TokenDto verifyRefreshToken(String RefreshToken, boolean isRevoke) throws RefreshTokenException {
        TokenDto refreshToken = tokenRepository.findByRefreshToken(RefreshToken);
        if (refreshToken == null)
            throw new RefreshTokenException("Refresh token not exist !");
        if (refreshToken.getIsRefresh().equalsIgnoreCase("Y"))
            throw new RefreshTokenException("Refresh token already used !");
        if (refreshToken.getIsRevoke().equalsIgnoreCase("Y"))
            throw new RefreshTokenException("Refresh token already revoked !");
        if (refreshToken.getExpired().compareTo(Instant.now()) < 0)
            throw new RefreshTokenException("Refresh token was expired. Please make a new  request");

        if (isRevoke) {
            refreshToken.setIsRevoke("Y");
        } else {
            refreshToken.setIsRefresh("Y");
        }

        refreshToken = tokenRepository.saveAndFlush(refreshToken);

        return refreshToken;
    }

    @Override
    //public String generateNewRefreshToken(String token, int userRef,int refId) throws Exception {
    public LoginRes generateNewRefreshToken(String refreshToken) throws Exception {


        TokenDto tokenDto = this.verifyRefreshToken(refreshToken, false);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                tokenDto.getUser().getEmail());


        final String token = jwtUtility.generateToken(userDetails, userDetails.getAuthorities(), tokenDto.getUser().getChannelCode());

        //   final String newRefreshToken=tokenDtoService.generateNewRefreshToken(token,userRepository.findByEmail(userDetails.getUsername()).getUserId(),tokenDto.getId());


        TokenDto refreshTokenDto = new TokenDto();

        refreshTokenDto.setExpired(Instant.now().plusMillis(refreshms));
        refreshTokenDto.setRefreshToken(UUID.randomUUID().toString());
        refreshTokenDto.setToken(token);
        refreshTokenDto.setUserRef(tokenDto.getUserRef());
        refreshTokenDto.setRefId(tokenDto.getId());

        refreshTokenDto = tokenRepository.saveAndFlush(refreshTokenDto);

        return new LoginRes(refreshTokenDto.getRefreshToken());

    }


}
