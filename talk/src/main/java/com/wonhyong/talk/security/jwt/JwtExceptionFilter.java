package com.wonhyong.talk.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            //만료 에러
            setResponse(response, "TOKEN EXPIRED");
        } catch (MalformedJwtException e) {
            //변조 에러
            setResponse(response, "TOKEN FORMAT ERROR");
        } catch (Exception e) {
            setResponse(response, "UNKNOWN ERROR WHILE AUTHENTICATE");
        }
    }

    private void setResponse(HttpServletResponse response, String exception) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(exception);
    }
}
