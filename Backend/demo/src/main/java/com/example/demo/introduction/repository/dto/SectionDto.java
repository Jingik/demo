package com.example.demo.introduction.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class SectionDto {

    private Long id;
    private String subTitle;
    private String content;

}
