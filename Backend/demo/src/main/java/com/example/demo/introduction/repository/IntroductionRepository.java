package com.example.demo.introduction.repository;

import com.example.demo.introduction.domain.Introduction;
import com.example.demo.introduction.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IntroductionRepository extends JpaRepository<Introduction, Long> {

    @Query("SELECT i FROM Introduction i JOIN i.user u WHERE u.id = :id")
    List<Introduction> findMyIntroductions(Long id);

}
