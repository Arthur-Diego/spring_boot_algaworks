/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.springboot.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *
 * @author Arthur
 */
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Override
    public boolean supports(MethodParameter mp, Class<? extends HttpMessageConverter<?>> type) {
        return mp.getMethod().getName().equals("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken t,
            MethodParameter mp, MediaType mt,
            Class<? extends HttpMessageConverter<?>> type,
            ServerHttpRequest req, ServerHttpResponse resp) {

        HttpServletRequest request = ((ServletServerHttpRequest) req).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse) resp).getServletResponse();

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) t;

        String refreshToken = token.getRefreshToken().getValue();
        adicionarRefreshTokenNoCookie(refreshToken, request, response);
        removerRefreshTokenDoBody(token);
        return t;

    }

    private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }

    private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath(request.getContextPath() + "/oauth/token");
        refreshTokenCookie.setMaxAge(2592000);
        response.addCookie(refreshTokenCookie);
    }

}
