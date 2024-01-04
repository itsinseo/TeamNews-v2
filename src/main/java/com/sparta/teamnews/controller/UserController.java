package com.sparta.teamnews.controller;

import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.UserService;
import com.sparta.teamnews.service.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teamnews/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponseDto signupUser(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return new ApiResponseDto("회원가입 완료", HttpStatus.OK.value());
    }

    @PostMapping("/login")
    public ApiResponseDto loginUser(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        userService.loginUser(userRequestDto, response);
        return new ApiResponseDto("로그인 완료", HttpStatus.OK.value());
    }

    @PostMapping("/logout")
    public UserResponseDto logoutUser(HttpServletRequest request) {
        return userService.logoutUser(request);
    }

    @GetMapping("/profile")
    public UserResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getProfile(userDetails);
    }

    @PutMapping("/profile")
    public ApiResponseDto updateProfile(@RequestBody ProfileRequestDto profileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.updateProfile(profileRequestDto, userDetails);
    }

    @PutMapping("/profile/password")
    public ApiResponseDto updatePassword(@RequestBody PasswordRequestDto passwordRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.updatePassword(passwordRequestDto, userDetails);
    }
}
