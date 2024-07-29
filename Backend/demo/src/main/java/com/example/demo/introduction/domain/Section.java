package com.example.demo.introduction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Many;

@Entity
@Getter @Setter
@AllArgsConstructor
public class Section {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "introduction_id")
    private Introduction introduction;

    private String subTitle;
    private String content;

    public Section() {
    }

    public Section(String subTitle, String content) {
        this.subTitle = subTitle;
        this.content = content;
    }
}
