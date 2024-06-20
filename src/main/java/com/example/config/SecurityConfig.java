package com.example.config;

import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
@Configuration
public class SecurityConfig {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtTokenFilter jwtTokeFilter;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return MD5Util.getMd5(rawPassword.toString()).equals(encodedPassword);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers("/category/admin/**").hasRole("ADMIN")
                    .requestMatchers("/category/lang").permitAll()
                    .requestMatchers("/types/admin/**").hasRole("ADMIN")
                    .requestMatchers("/types/lang").permitAll()
                    .requestMatchers("/sms/admin/*").hasRole("ADMIN")
                    .requestMatchers("/sms/*").hasAnyRole("ADMIN","USER")
                    .requestMatchers("/region/admin/**").hasRole("ADMIN")
                    .requestMatchers("/region/lang").permitAll()
                    .requestMatchers("/profile/admin/**").hasRole("ADMIN")
                    .requestMatchers("/profile/update/current").hasAnyRole("ADMIN","USER")
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/email/admin/*").hasRole("ADMIN")
                    .requestMatchers("/email/*").hasAnyRole("ADMIN","USER")
                    .requestMatchers("/article/moderator/**").hasRole("MODERATOR")
                    .requestMatchers("/article/publisher/**").hasRole("PUBLISHER")
                    .requestMatchers("/article/all/**").permitAll()
                    .requestMatchers("/attach/**").permitAll()
                    .requestMatchers("/reaction/*").hasAnyRole("ADMIN","USER","MODERATOR","PUBLISHER")
                    .anyRequest()
                    .authenticated();
        });
        http.addFilterBefore(jwtTokeFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
