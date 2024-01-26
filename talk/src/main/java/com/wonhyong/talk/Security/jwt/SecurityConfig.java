package com.wonhyong.talk.Security.jwt;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
                http.httpBasic(AbstractHttpConfigurer::disable)
                // 쿠키 기반이 아닌 JWT 기반이므로 사용하지 않음
                .csrf(AbstractHttpConfigurer::disable)
                // CORS 설정
                .cors(AbstractHttpConfigurer::disable)
                // Spring Security 세션 정책 : 세션을 생성 및 사용하지 않음
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/members/new", "/api/members/login", "/api/members", "/chat", "/ws/chat").permitAll()
                        .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll())
                // 에러 핸들링
                .exceptionHandling(e -> e
                        .accessDeniedHandler(new JwtAccessDeniedHandler())
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                // JWT 인증 필터 적용
                .addFilterAfter(new JwtAuthenticationFilter(jwtProvider), ExceptionTranslationFilter.class)
                        .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}