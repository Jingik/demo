package com.example.demo.introduction.repository;

import com.example.demo.introduction.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
