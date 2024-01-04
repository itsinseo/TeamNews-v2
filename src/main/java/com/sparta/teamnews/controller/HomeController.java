package com.sparta.teamnews.controller;

import com.sparta.teamnews.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/page")
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/user/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/user/my-page")
    public String myPage() {
        return "my-page";
    }

    @GetMapping("/post")
    public String getPost(Model model, @RequestParam("postnum") Long id) {
        postService.getPost(id);
        model.addAttribute("postnum", id);
        return "detail";
    }

    @GetMapping("/user/new-post")
    public String newPostPage() {
        return "new-post";
    }

    @GetMapping("/user/password-change")
    public String passwordChange() {
        return "password-change";
    }

}