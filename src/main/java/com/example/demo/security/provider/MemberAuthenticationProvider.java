package com.example.demo.security.provider;

import com.example.demo.security.auth.MemberPrincipalDetailService;
import com.example.demo.security.auth.MemberPrincipalDetails;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final MemberPrincipalDetailService memberPrincipalDetailService;

    public MemberAuthenticationProvider(MemberPrincipalDetailService memberPrincipalDetailService) {
        this.memberPrincipalDetailService = memberPrincipalDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        MemberPrincipalDetails userDetails = (MemberPrincipalDetails) memberPrincipalDetailService.loadUserByUsername(username);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("사용자 비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
