package com.denysenko.telegramopenapiproject.services;

import com.denysenko.telegramopenapiproject.exceptions.InputValidationException;
import com.denysenko.telegramopenapiproject.model.Admin;
import com.denysenko.telegramopenapiproject.model.dto.CredentialsDTO;
import com.denysenko.telegramopenapiproject.repositories.AdminRepository;
import com.denysenko.telegramopenapiproject.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {

    private final AdminRepository adminRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String register(CredentialsDTO credentialsDTO){
        if(adminRepository.existsByUsername(credentialsDTO.getUsername())){
            throw new InputValidationException("Email is already taken");
        }else {
            Admin admin = new Admin();
            admin.setUsername(credentialsDTO.getUsername());
            admin.setPassword(passwordEncoder.encode(credentialsDTO.getPassword()));
            adminRepository.save(admin);
            String token = jwtTokenUtils.createToken(credentialsDTO.getUsername());
            return token;
        }
    }

    public String authenticate(CredentialsDTO credentials){
        var userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());
        if(passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())) {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword()
                    ));
            return jwtTokenUtils.createToken(credentials.getUsername());
        }else throw new AccessDeniedException("User is not registered");
    }
}
