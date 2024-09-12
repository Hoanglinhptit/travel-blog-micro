package com.travelblog.user_service.configs.security;

import com.travelblog.user_service.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private  UserEntityRepository userRepository;

//    public SecurityConfig(UserEntityRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        request ->
                                request
                                        .requestMatchers(HttpMethod.GET,"/api/user/**").permitAll()
                                        .requestMatchers(HttpMethod.POST,"/api/user/**").permitAll()
                                        .requestMatchers(HttpMethod.PUT,"/api/user/**").permitAll()
                                        .requestMatchers(HttpMethod.DELETE,"/api/user/**").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/h2-console/**").permitAll()
//                                        .requestMatchers(HttpMethod.GET, "/api/").hasAnyRole("ADMIN", "USER")
//                                        .requestMatchers(HttpMethod.POST, "/api/user/**").hasAnyRole("ADMIN", "USER")
//                                        .requestMatchers(HttpMethod.PUT, "/api/user/**").hasAnyRole("ADMIN", "USER")
//                                        .requestMatchers(HttpMethod.DELETE, "/api/user/**").hasAnyRole("ADMIN", "USER")
//                                        .requestMatchers(webConsoleUrl + "/**")
//                                        .hasRole("ADMIN")
                                        .anyRequest().authenticated()
                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
