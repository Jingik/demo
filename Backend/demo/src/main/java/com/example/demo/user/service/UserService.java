package com.example.demo.user.service;

import com.example.demo.user.domain.UserSet;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSet create(String id, String username, String email, String password) {
        Optional<UserSet> existingUser = userRepository.findByUserId(id);
        if (existingUser.isPresent()) {
            throw new DataNotFoundException("이미 존재하는 사용자입니다.");
        }

        UserSet user = new UserSet();
        user.setUserId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }

    public UserSet getUser(String id) {
        Optional<UserSet> user = userRepository.findByUserId(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    // 비밀번호를 암호화하여 저장하는 메소드
    public void saveUser(UserSet user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // 비밀번호 변경 기능
    public UserSet changePassword(String id, String newPassword) {
        UserSet user = getUser(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        saveUser(user);
        return user;
    }

    // 사용자 삭제 기능
    public void deleteUser(String id) {
        UserSet user = getUser(id);
        this.userRepository.delete(user);
    }

    // 사용자 인증 기능
    public boolean authenticate(String userId, String rawPassword) {
        Optional<UserSet> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            UserSet user = userOptional.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        } else {
            return false;
        }
    }
}
