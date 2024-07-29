package com.example.demo.user.service;

import com.example.demo.user.domain.Role;
import com.example.demo.user.domain.UserSet;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<UserSet> _user = userRepository.findByUserId(id);
        if (_user.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        UserSet user = _user.get();
        User.UserBuilder builder = User.withUsername(user.getUserId());
        builder.password(user.getPassword());
        builder.roles(user.getRoles().stream().map(Role::getRoleName).toArray(String[]::new));
        return builder.build();
    }
}
