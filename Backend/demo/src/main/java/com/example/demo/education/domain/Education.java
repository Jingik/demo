package com.example.demo.education.domain;

import com.example.demo.user.domain.UserSet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@AllArgsConstructor
public class Education {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserSet user;

    private String universityName;
    private String degree;
    private String major;
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;
    @Temporal(TemporalType.DATE)
    private LocalDate graduateDate;

    public Education() {
    }

    public Education(String universityName, String degree, String major, LocalDate startDate, LocalDate graduateDate) {
        this.universityName = universityName;
        this.degree = degree;
        this.major = major;
        this.startDate = startDate;
        this.graduateDate = graduateDate;
    }
}
