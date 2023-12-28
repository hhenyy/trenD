package com.td.TrenD.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 여기서는 간단한 예시로 "admin" userId만을 사용하겠습니다.
        if ("admin".equals(userId)) {
            return new User("admin", "password",
                    getAuthorities("ROLE_ADMIN"));
        }
        // 여러 사용자를 처리해야 하는 경우 사용자 정보를 데이터베이스나 다른 곳에서 가져와야 합니다.

        throw new UsernameNotFoundException("User not found with userId: " + userId);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}
