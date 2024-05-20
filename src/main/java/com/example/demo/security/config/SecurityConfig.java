package com.example.demo.security.config;

import com.example.demo.security.auth.MemberPrincipalDetailService;
import com.example.demo.security.provider.MemberAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    MemberAuthenticationProvider memberAuthenticationProvider;

    MemberPrincipalDetailService memberPrincipalDetailService;

    @Autowired
    public SecurityConfig(MemberAuthenticationProvider memberAuthenticationProvider, MemberPrincipalDetailService memberPrincipalDetailService) {
        this.memberAuthenticationProvider = memberAuthenticationProvider;
        this.memberPrincipalDetailService = memberPrincipalDetailService;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(memberAuthenticationProvider);
    }

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain memberSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            // 권한 설정...
            .authorizeHttpRequests((authorizeRequests) -> {
                authorizeRequests.requestMatchers("/**").permitAll();
                authorizeRequests.requestMatchers("/admin/**").hasAnyRole("ADMIN", "SYS");
                authorizeRequests.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER");
                authorizeRequests.anyRequest().permitAll();
            })
            .formLogin((formLogin) -> {
                formLogin.loginPage("/login")
                        .defaultSuccessUrl("/main")
                        .usernameParameter("userId")
                        .permitAll();
            })
            .logout((logout) -> {
                logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID");
            });

        http
            .rememberMe((rememberMeConfigurer) -> {
                 rememberMeConfigurer.key(secret)
                    .tokenValiditySeconds(60*60*24*7) // 인증 토큰 유효시간(초 단위)
                    .userDetailsService(memberPrincipalDetailService)
                    .rememberMeParameter("remember-me");
           });

        return http.build();
    }
}
