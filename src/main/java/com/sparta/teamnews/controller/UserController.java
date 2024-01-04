package com.sparta.teamnews.controller;

import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.UserService;
import com.sparta.teamnews.service.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/user/signup")    //회원가입
    public String signupUser(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "redirect:/api/user/login";
    }

    @ResponseBody
    @PostMapping("/user/login")     //로그인
    public ApiResponseDto loginUser(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        userService.loginUser(userRequestDto, response);
        return new ApiResponseDto("로그인완료", HttpStatus.OK.value());
    }

    @ResponseBody
    @PostMapping("/user/logout")    //로그아웃
    public UserResponseDto logoutUser(HttpServletRequest request) {
        return userService.logoutUser(request);
    }

    @ResponseBody
    @GetMapping("/user/profile")
    public UserResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getProfile(userDetails);
    }

    @ResponseBody
    @PutMapping("/user/profile")     //프로필정보 수정
    public String updateProfile(@RequestBody ProfileRequestDto profileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updateProfile(profileRequestDto, userDetails);
        return "redirect:/api/user/mypage";
    }

    @ResponseBody
    @PutMapping("/user/profile/password")       //비밀번호 수정
    public String updatePassword(@RequestBody PwdRequestDto pwdRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updatePassword(pwdRequestDto, userDetails);
        return "redirect:/api/user/passwordchange";
    }
}
