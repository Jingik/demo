package com.example.demo.employment.service;

import com.example.demo.employment.domain.Employment;
import com.example.demo.employment.repository.EmploymentRepository;
import com.example.demo.employment.repository.dto.EmploymentDto;
import com.example.demo.user.domain.UserSet;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmploymentService {

    private final EmploymentRepository employmentRepository;
    private final UserRepository userRepository;

    public Long saveEmployment(Long userId, Employment employment) {
        UserSet findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "인증되지 않은 사용자"));
        employment.setUser(findUser);
        Employment savedEmployment = employmentRepository.save(employment);
        return savedEmployment.getId();
    }

    @Transactional(rollbackFor = ResponseStatusException.class)
    public void updateEmployment(Long employmentId, EmploymentDto updateParam) {
        Employment findEmployment = employmentRepository.findById(employmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 데이터"));

        findEmployment.setCompanyName(updateParam.getCompanyName());
        findEmployment.setPosition(updateParam.getPosition());
        findEmployment.setDepartment(updateParam.getDepartment());
        findEmployment.setStartDate(updateParam.getStartDate());
        findEmployment.setEndDate(updateParam.getEndDate());
        findEmployment.setAchievement(updateParam.getAchievement());
    }

    public EmploymentDto findById(Long employmentId) {
        Employment findEmployment = employmentRepository.findById(employmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 데이터"));

        return new EmploymentDto(findEmployment.getId(), findEmployment.getCompanyName(), findEmployment.getPosition(),
                                findEmployment.getDepartment(), findEmployment.getStartDate(), findEmployment.getEndDate(),
                                findEmployment.getAchievement());
    }

    public void deleteEmployment(Long employmentId) {
        employmentRepository.deleteById(employmentId);
    }

}
