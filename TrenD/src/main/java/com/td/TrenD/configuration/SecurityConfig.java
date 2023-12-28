package com.td.TrenD.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") // "/admin/**" 경로는 ADMIN 권한이 필요
                .antMatchers("/mypage/user").hasAnyRole("USER")
                .antMatchers("/mypage/admin").hasAnyRole("ADMIN")
                .antMatchers("/mypage/boardlist/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/mypage/replylist/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()  // FormLogin 사용 X
                .httpBasic().disable()  // httpBasic 사용 X
                .csrf().disable()       // csrf 보안 사용 X
                .headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
