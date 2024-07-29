package com.example.demo.introduction.api;

import com.example.demo.introduction.domain.Introduction;
import com.example.demo.introduction.domain.Section;
import com.example.demo.introduction.repository.dto.IntroductionDto;
import com.example.demo.introduction.repository.dto.IntroductionListDto;
import com.example.demo.introduction.service.IntroductionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IntroductionApiController {

    private final IntroductionService introductionService;

    @PostMapping("/api/introduction")
    public CreateIntroductionResponse saveIntroduction(@Valid @RequestBody CreateIntroductionRequest request) {
        Introduction introduction = new Introduction(request.getTitle());
        List<CreateSectionRequest> sections = request.getSections();

        List<Section> collect = sections.stream()
                .map(section -> new Section(section.getSubTitle(), section.getContent()))
                .collect(Collectors.toList());
        Long id = introductionService.saveIntroduction(request.getUserId(), introduction, collect);

        return new CreateIntroductionResponse(id);
    }

    @PutMapping("/api/introduction/{id}")
    public void updateIntroduction(@PathVariable("id") Long id, @Valid @RequestBody IntroductionDto updateParam) {
        introductionService.updateIntroduction(id, updateParam);
    }

    @GetMapping("/api/introduction/{id}")
    public IntroductionDto findById(@PathVariable("id") Long id) {
        return introductionService.findById(id);
    }

    @GetMapping("/api/introductions/{userId}")
    public MyIntroductionList findMyIntroductions(@PathVariable("userId") Long id) {
        List<IntroductionListDto> myIntroductions = introductionService.findMyIntroductions(id);
        return new MyIntroductionList(myIntroductions);
    }

    @DeleteMapping("/api/introduction/{id}")
    public void deleteIntroduction(@PathVariable("id") Long id) {
        introductionService.deleteIntroduction(id);
    }

    @DeleteMapping("/api/introduction/section/{id}")
    public void deleteSection(@PathVariable("id") Long id) {
        introductionService.deleteSection(id);
    }

    @Getter
    static class CreateIntroductionRequest {
        @NotNull
        private Long userId;
        @NotNull
        private String title;
        private List<CreateSectionRequest> sections;
    }

    @Getter
    static class CreateSectionRequest {
        @NotNull
        private String subTitle;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    static class CreateIntroductionResponse {
        private Long id;
    }

    @Getter
    @AllArgsConstructor
    static class MyIntroductionList {
        private List<IntroductionListDto> results;
    }

}
