package com.example.demo.web.controller;

import com.example.demo.service.MemberService;
import com.example.demo.web.request.SignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequest signupRequest) {
        log.info("Signup request: {}", signupRequest);
        try {
            memberService.saveMember(signupRequest);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Signup successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error during signup: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Signup failed. Please try again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, String password) {
        log.info("login cont: {}", userId+"/ "+password);
        return "main/main";
    }

    @GetMapping("/logout")
    public String logout() {
        return "auth/logout";
    }

    @PostMapping("/logout")
    public String logoutPost() {
        return "auth/logout";
    }

}
