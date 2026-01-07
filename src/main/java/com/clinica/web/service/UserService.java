package com.clinica.web.service;


import com.clinica.web.dto.UserDto;
import com.clinica.web.model.User;
import com.clinica.web.repository.UserRepository;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";
    private JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public User save(UserDto userDto) {
        validatePassword(userDto.getPassword());
        validateEmail(userDto.getEmail());

        User user = new User();
        user.setUsername(userDto.getUsername());

        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // bcrypt
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setEnabled(1);

        return userRepository.save(user);
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private void validatePassword(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Parola este obligatorie");
        }

        if (!password.matches(PASSWORD_REGEX)) {
            throw new IllegalArgumentException(
                    "Parola trebuie să aibă minim 8 caractere, o literă mare, " +
                            "o literă mică, o cifră și un caracter special"
            );
        }

    }
    private void validateEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Emailul este obligatoriu");
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex != email.lastIndexOf('@')) {
            throw new IllegalArgumentException("Emailul trebuie să conțină un singur caracter @");
        }

        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        if (localPart.isBlank()) {
            throw new IllegalArgumentException("Partea locală a emailului este invalidă");
        }

        if (!domainPart.contains(".")) {
            throw new IllegalArgumentException("Domeniul emailului este invalid");
        }

        if (domainPart.startsWith(".") || domainPart.endsWith(".")) {
            throw new IllegalArgumentException("Domeniul emailului este invalid");
        }

        String[] parts = domainPart.split("\\.");
        String tld = parts[parts.length - 1];

        if (tld.length() < 2 || tld.length() > 6) {
            throw new IllegalArgumentException("Codul de domeniu este invalid");
        }

        if (!domainPart.equalsIgnoreCase("gmail.com") &&
                !domainPart.equalsIgnoreCase("yahoo.com")) {

            throw new IllegalArgumentException("Domeniul emailului nu este acceptat");
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        String sql = "SELECT username, role ,id ,enabled FROM Users";


        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();

            user.setUsername(rs.getString("username"));
            user.setRole(rs.getString("role"));
            user.setId(rs.getInt("id"));
            user.setEnabled(rs.getInt("enabled"));
            return user;
        });
    }

    public void toggleStatus(Long id) {

        String sql = "UPDATE Users SET enabled = CASE WHEN enabled = 1 THEN 0 ELSE 1 END WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
