package com.denysenko.telegramopenapiproject.services;

import com.denysenko.telegramopenapiproject.repositories.AdminRepository;
import com.denysenko.telegramopenapiproject.security.jwt.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecurityUserService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Profile with username " + username + " not found"));
        return new AuthenticatedUser(admin);
    }
}
