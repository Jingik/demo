package com.example.demo.employment.domain;

import com.example.demo.user.domain.UserSet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@AllArgsConstructor
public class Employment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserSet user;

    private String companyName;
    private String position;
    private String department;
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;
    private String achievement;

    public Employment() {
    }

    public Employment(String companyName, String position, String department, LocalDate startDate, LocalDate endDate, String achievement) {
        this.companyName = companyName;
        this.position = position;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
        this.achievement = achievement;
    }
}
