package com.clinica.web.service;

import com.clinica.web.model.User;
import com.clinica.web.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        String userRole = user.getRole();
        if (userRole == null || userRole.isEmpty()) {
            userRole = "ROLE_ADMIN";
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userRole));
// În CustomUserDetailsService, înainte de return builder.build();
        System.out.println("--- VERIFICARE ROL ---");
        System.out.println("Utilizator: " + user.getUsername());
        System.out.println("Rol din Baza de Date: " + user.getRole());
        System.out.println("Autorități trimise la Spring: " + authorities);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(user.getEnabled() == 1)// Dacă e 0, contul e blocat
                .authorities(userRole)
                .build();
    }

}
