package com.example.demo.user.api;

import com.example.demo.user.domain.UserCreateForm;
import com.example.demo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword().equals(userCreateForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getId(), userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword());
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String password = payload.get("password");

        boolean isAuthenticated = userService.authenticate(userId, password);

        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", isAuthenticated);
        if (isAuthenticated) {
            response.put("message", "로그인 성공");
        } else {
            response.put("message", "로그인 실패");
        }

        return response;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login_form";
    }
}
