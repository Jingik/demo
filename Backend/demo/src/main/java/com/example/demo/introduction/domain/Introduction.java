package com.example.demo.introduction.domain;

import com.example.demo.user.domain.UserSet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
public class Introduction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserSet user;

    @OneToMany(mappedBy = "introduction",fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    private String title;

    public Introduction() {
    }

    public Introduction(String title) {
        this.title = title;
    }

    public void addSections(List<Section> sections) {
        for (Section section : sections) {
            section.setIntroduction(this);
            this.sections.add(section);
        }
    }

}
