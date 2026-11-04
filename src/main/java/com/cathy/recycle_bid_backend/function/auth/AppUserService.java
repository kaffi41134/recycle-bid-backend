package com.cathy.recycle_bid_backend.function.auth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cathy.recycle_bid_backend.data.dao.AppUserRepository;
import com.cathy.recycle_bid_backend.data.entity.AppUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

        AppUser entity = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("帳號或密碼錯誤"));

        return User.withUsername(entity.getUsername())
                .password(entity.getPasswordHash())
                .authorities("ROLE_USER")
                .disabled(!entity.isEnabled())
                .build();
    }

}
