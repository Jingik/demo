package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.user.service.UserSecurityService;
import com.example.demo.user.repository.UserRepository;

@Configuration
public class AppConfig {

    @Bean
    public UserSecurityService userSecurityService(UserRepository userRepository) {
        return new UserSecurityService(userRepository);
    }
}
