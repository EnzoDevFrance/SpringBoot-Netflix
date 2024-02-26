package com.projet.netflix.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import com.projet.netflix.dto.request.RefreshTokenRequest;
import com.projet.netflix.dto.response.RefreshTokenResponse;
import com.projet.netflix.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
    ResponseCookie generateRefreshTokenCookie(String token);
    String getRefreshTokenFromCookies(HttpServletRequest request);
    void deleteByToken(String token);
    ResponseCookie getCleanRefreshTokenCookie();
}
