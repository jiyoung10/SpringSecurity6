package com.example.demo.service;

import com.example.demo.domain.entity.Member;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.web.request.SignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    public MemberService(PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void saveMember(SignupRequest signupRequest) {
        // ID 중복 체크
        if(memberRepository.findByUserId(signupRequest.getUserId()).isPresent()) {
            throw new RuntimeException("THIS_USER_ID_IS_ALREADY_EXISTED");
        }
        signupRequest.encodingPassword(passwordEncoder);
        Member member = Member.createMember(signupRequest);
        memberRepository.save(member);
        log.info("SIGNUP_SUCCESS_MEMBER_ID_IS: {}",member.getId());
    }

}
