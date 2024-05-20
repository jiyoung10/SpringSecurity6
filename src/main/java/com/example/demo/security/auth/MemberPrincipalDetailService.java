package com.example.demo.security.auth;

import com.example.demo.domain.entity.Member;
import com.example.demo.domain.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberPrincipalDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberPrincipalDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member =  memberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 아이디가 없습니다."));
        log.info("loadUserByUsername username : {}",username);
        return new MemberPrincipalDetails(member);
    }
}
