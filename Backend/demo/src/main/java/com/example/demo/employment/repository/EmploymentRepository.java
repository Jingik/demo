package com.example.demo.employment.repository;

import com.example.demo.employment.domain.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploymentRepository extends JpaRepository<Employment, Long> {
}
