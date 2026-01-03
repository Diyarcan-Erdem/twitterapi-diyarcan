package com.example.twitterapi.service;

import com.example.twitterapi.dto.LoginRequest;
import com.example.twitterapi.dto.RegisterRequest;
import com.example.twitterapi.entity.User;
import com.example.twitterapi.exception.AlreadyExistsException;
import com.example.twitterapi.exception.NotFoundException;
import com.example.twitterapi.exception.UnauthorizedException;
import com.example.twitterapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        boolean ok = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!ok) {
            throw new UnauthorizedException("Wrong password");
        }

        return "Login successful";
    }
}
