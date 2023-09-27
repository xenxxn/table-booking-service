package com.xenxxn.tablebooking.global.jwt.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface JwtService {
    String createAccessToken(String memberEmail);
    String createRefreshToken();

    void updateRefreshToken(String memberEmail, String refreshToken);
    void destroyRefreshToken(String memberEmail);

    void sendToken(HttpServletResponse response, String accessToken, String refreshToken) throws IOException;

    void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken);
    void sendAccessToken(HttpServletResponse response, String accessToken);
    String extractAccessToken(HttpServletRequest request) throws IOException, ServletException;

    String extractRefreshToken(HttpServletRequest request) throws IOException, ServletException;

    String extractMemberEmail(String accessToken);

    void setAccessTokenHeader(HttpServletResponse response, String accessToken);
    void setRefreshTokenHeader(HttpServletResponse response, String refreshToken);
}
