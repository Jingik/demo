package com.example.demo.user.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String id;

    @Size(min = 3, max = 25)
    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String username;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @Size(min = 3, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @Size(min = 3, message = "비밀번호 확인은 최소 8자 이상이어야 합니다.")
    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String confirmPassword;
}
