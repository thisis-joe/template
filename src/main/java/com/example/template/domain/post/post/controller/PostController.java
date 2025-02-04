package com.example.template.domain.post.post.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class PostController {
    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}