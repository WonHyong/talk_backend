package com.wonhyong.talk.Security.jwt;

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
    protected void doFilterInternal(@NotNull HttpServletRequest request, HttpServletResponse response, @NotNull FilterChain filterChain) throws IOException {
        response.setCharacterEncoding("utf-8");

        try{
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            //만료 에러
            setResponse(response, "TOKEN EXPIRED");
        } catch (MalformedJwtException e) {
            //변조 에러
            setResponse(response, e.getMessage());
        } catch (Exception e) {
            setResponse(response, e.getMessage());
        }
    }

    private void setResponse(HttpServletResponse response, String exception) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(exception);
    }
}
