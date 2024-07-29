package com.example.demo.education.api;

import com.example.demo.education.domain.Education;
import com.example.demo.education.repository.dto.EducationDto;
import com.example.demo.education.service.EducationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EducationApiController {

    private final EducationService educationService;

    @PostMapping("/api/education")
    public CreateEducationResponse createEducation(@Valid @RequestBody CreateEducationRequest request) {
        Education education = new Education(request.getUniversityName(), request.getDegree(),
                                            request.getMajor(), request.getStartDate(), request.getGraduateDate());
        Long id = educationService.saveEducation(request.getUserId(), education);
        return new CreateEducationResponse(id);
    }

    @PutMapping("/api/education/{id}")
    public void updateEducation(@PathVariable("id") Long id, @Valid @RequestBody EducationDto updateParam) {
        educationService.updateEducation(id, updateParam);
    }

    @GetMapping("/api/education/{id}")
    public EducationDto findById(@PathVariable("id") Long id) {
        return educationService.findById(id);
    }

    @DeleteMapping("/api/education/{id}")
    public void deleteEducation(@PathVariable("id") Long id) {
        educationService.deleteEducation(id);
    }

    @Getter
    static class CreateEducationRequest {
        @NotNull
        private Long userId;
        @NotNull
        private String universityName;
        @NotNull
        private String degree;
        @NotNull
        private String major;
        @NotNull
        private LocalDate startDate;
        private LocalDate graduateDate;
    }

    @Getter
    @AllArgsConstructor
    static class CreateEducationResponse {
        private Long id;
    }

}
