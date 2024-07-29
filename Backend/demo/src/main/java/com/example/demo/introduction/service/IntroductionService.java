package com.example.demo.introduction.service;

import com.example.demo.introduction.domain.Introduction;
import com.example.demo.introduction.domain.Section;
import com.example.demo.introduction.repository.IntroductionRepository;
import com.example.demo.introduction.repository.SectionRepository;
import com.example.demo.introduction.repository.dto.IntroductionListDto;
import com.example.demo.introduction.repository.dto.IntroductionDto;
import com.example.demo.introduction.repository.dto.SectionDto;
import com.example.demo.user.domain.UserSet;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntroductionService {

    private final IntroductionRepository introductionRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;

    public Long saveIntroduction(Long userId, Introduction introduction, List<Section> sections) {
        UserSet findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "인증되지 않은 사용자"));
        introduction.setUser(findUser);
        introduction.addSections(sections);

        Introduction savedIntroduction = introductionRepository.save(introduction);
        sectionRepository.saveAll(sections);
        return savedIntroduction.getId();
    }

    @Transactional(rollbackFor = ResponseStatusException.class)
    public void updateIntroduction(Long introductionId, IntroductionDto updateParam) {
        Introduction findIntroduction = introductionRepository.findById(introductionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 데이터"));
        findIntroduction.setTitle(updateParam.getTitle());

        List<Section> findSections = findIntroduction.getSections();
        List<SectionDto> updateSectionParams = updateParam.getSections();

        for (int i = 0; i < findSections.size(); i++) {
            findSections.get(i).setSubTitle(updateSectionParams.get(i).getSubTitle());
            findSections.get(i).setContent(updateSectionParams.get(i).getContent());
        }
    }

    public IntroductionDto findById(Long id) {
        Introduction findIntroduction = introductionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 데이터"));

        List<SectionDto> collect = findIntroduction.getSections().stream()
                .map(section -> new SectionDto(section.getId(), section.getSubTitle(), section.getContent()))
                .collect(Collectors.toList());
        return new IntroductionDto(findIntroduction.getId(), findIntroduction.getTitle(), collect);
    }

    public List<IntroductionListDto> findMyIntroductions(Long userId) {
        return introductionRepository.findMyIntroductions(userId).stream()
                .map(introduction -> new IntroductionListDto(introduction.getId(), introduction.getTitle()))
                .collect(Collectors.toList());
    }

    public void deleteIntroduction(Long id) {
        introductionRepository.deleteById(id);
    }

    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }
}
