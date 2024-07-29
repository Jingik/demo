package com.example.demo.education.service;

import com.example.demo.education.domain.Education;
import com.example.demo.education.repository.EducationRepository;
import com.example.demo.education.repository.dto.EducationDto;
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
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;

    public Long saveEducation(Long userId, Education education) {
        UserSet findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "인증되지 않은 사용자"));
        education.setUser(findUser);
        Education savedEducation = educationRepository.save(education);
        return savedEducation.getId();
    }

    @Transactional(rollbackFor = ResponseStatusException.class)
    public void updateEducation(Long educationId, EducationDto updateParam) {
        Education findEducation = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 데이터"));

        findEducation.setUniversityName(updateParam.getUniversityName());
        findEducation.setDegree(updateParam.getDegree());
        findEducation.setMajor(updateParam.getMajor());
        findEducation.setStartDate(updateParam.getStartDate());
        findEducation.setGraduateDate(updateParam.getGraduateDate());
    }

    public EducationDto findById(Long educationId) {
        Education findEducation = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 데이터"));

        return new EducationDto(findEducation.getId(), findEducation.getUniversityName(),
                                findEducation.getDegree(), findEducation.getMajor(),
                                findEducation.getStartDate(), findEducation.getGraduateDate());
    }

    public void deleteEducation(Long educationId) {
        educationRepository.deleteById(educationId);
    }

}
