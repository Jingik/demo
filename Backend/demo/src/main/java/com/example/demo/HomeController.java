package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;

import com.example.demo.user.domain.UserCreateForm;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index"; // index.html 파일을 반환합니다.
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model, @AuthenticationPrincipal Object principal) {
        if (principal instanceof User) {
            User user = (User) principal;
            model.addAttribute("username", user.getUsername());
        } else if (principal instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) principal;
            model.addAttribute("username", oauth2User.getAttribute("name"));
        }
        return "home"; // home.html 파일을 반환합니다.
    }

    @GetMapping("/additional-info")
    public String additionalInfoForm(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        UserCreateForm userCreateForm = new UserCreateForm();
        userCreateForm.setEmail(oauth2User.getAttribute("email"));
        model.addAttribute("userCreateForm", userCreateForm);
        return "additional-info";
    }

    @PostMapping("/additional-info")
    public String additionalInfoSubmit(@Valid @ModelAttribute UserCreateForm userCreateForm, BindingResult bindingResult, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (bindingResult.hasErrors()) {
            return "additional-info";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "additional-info";
        }

        // OAuth2User로부터 정보를 가져와서 사용자 생성
        try {
            userService.create(userCreateForm.getId(), userCreateForm.getEmail(), userCreateForm.getPassword(), userCreateForm.getUsername());
        } catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "additional-info";
        }

        return "redirect:/home";
    }
}
