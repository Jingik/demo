package com.example.demo.user.service;

import com.example.demo.user.domain.Role;
import com.example.demo.user.domain.UserSet;
import com.example.demo.user.repository.RoleRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        Optional<UserSet> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            // 새로운 사용자 생성
            UserSet user = new UserSet();
            user.setUsername(oAuth2User.getAttribute("name"));
            user.setEmail(email);
            user.setPassword(""); // OAuth2 로그인 사용자는 비밀번호가 필요하지 않음

            // 기본 역할 설정 (예: ROLE_USER)
            Role userRole = roleRepository.findByRoleName("ROLE_USER").orElseGet(() -> {
                Role newRole = new Role("ROLE_USER");
                return roleRepository.save(newRole);
            });

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            userRepository.save(user);
        }

        return oAuth2User;
    }
}
