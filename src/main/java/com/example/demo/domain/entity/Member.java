package com.example.demo.domain.entity;

import com.example.demo.web.request.SignupRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Enumerated
    private Role role;

    @Column(name = "cre_date")
    private LocalDateTime creDate;

    @Column(name = "up_date")
    private LocalDateTime upDate;

    public Member() {}
    public Member(String name, String userId, String password,
                  String email, String address, Role role,
                  LocalDateTime creDate, LocalDateTime upDate) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.creDate = creDate;
        this.upDate = upDate;
    }
    public static Member createMember(SignupRequest signupRequest) {
        return new Member(
                signupRequest.getName(),
                signupRequest.getUserId(),
                signupRequest.getPassword(),
                signupRequest.getEmail(),
                signupRequest.getAddress(),
                Role.ROLE_USER,
                LocalDateTime.now(),
                null

        );
    }

}
