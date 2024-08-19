package com.delivery.storeadmin.config.security;

import java.util.List;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private List<String> SWAGGER = List.of(
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrfConfig -> csrfConfig.disable())
            .authorizeHttpRequests(it -> {
                    it
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll() // resource 에 대해서는 모든 요청 허용

                        // swagger 는 인증 없이 통과
                        .requestMatchers(SWAGGER.toArray(new String[0])).permitAll()

                        // open-api 는 인증 없이 통과
                        .requestMatchers("/open-api/**").permitAll()

                        // 그 외 모든 요청은 인증 사용
                        .anyRequest().authenticated();
                })
            .formLogin(Customizer.withDefaults()); // default login view 를 사용하지 않도록 설정

        return httpSecurity.build();
    }

}
