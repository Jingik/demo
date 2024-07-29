package com.example.demo.employment.api;

import com.example.demo.employment.domain.Employment;
import com.example.demo.employment.repository.dto.EmploymentDto;
import com.example.demo.employment.service.EmploymentService;
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
public class EmploymentApiController {

    private final EmploymentService employmentService;

    @PostMapping("/api/employment")
    public CreateEmploymentResponse createEmployment(@Valid @RequestBody CreateEmploymentRequest request) {
        Employment employment = new Employment(request.getCompanyName(), request.getPosition(),
                                            request.getDepartment(), request.getStartDate(), request.getEndDate(), request.getAchievement());
        Long id = employmentService.saveEmployment(request.getUserId(), employment);
        return new CreateEmploymentResponse(id);
    }

    @PutMapping("/api/employment/{id}")
    public void updateEmployment(@PathVariable("id") Long id, @Valid @RequestBody EmploymentDto updateParam) {
        employmentService.updateEmployment(id, updateParam);
    }

    @GetMapping("/api/employment/{id}")
    public EmploymentDto findById(@PathVariable("id") Long id) {
        return employmentService.findById(id);
    }

    @DeleteMapping("/api/employment/{id}")
    public void deleteEmployment(@PathVariable("id") Long id) {
        employmentService.deleteEmployment(id);
    }

    @Getter
    static class CreateEmploymentRequest {
        @NotNull
        private Long userId;
        @NotNull
        private String companyName;
        @NotNull
        private String position;
        private String department;
        @NotNull
        private LocalDate startDate;
        private LocalDate endDate;
        private String achievement;
    }

    @Getter
    @AllArgsConstructor
    static class CreateEmploymentResponse {
        private Long id;
    }

}
