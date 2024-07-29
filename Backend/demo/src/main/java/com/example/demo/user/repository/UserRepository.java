package com.example.demo.user.repository;

import com.example.demo.user.domain.UserSet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserSet, Long> {
    Optional<UserSet> findByEmail(String email);
    Optional<UserSet> findByUserId(String userId);
}
