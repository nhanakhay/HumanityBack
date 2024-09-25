package org.example.humanity.authentication.config;

import lombok.RequiredArgsConstructor;
import org.example.humanity.authentication.config.AddedSecurityConfiguration;
import org.example.humanity.authentication.config.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter authFilter;
    private final AddedSecurityConfiguration addedSecurityConfiguration;

    @Autowired
    public SecurityConfiguration(JwtAuthenticationFilter authFilter, AddedSecurityConfiguration addedSecurityConfiguration) {
        this.authFilter = authFilter;
        this.addedSecurityConfiguration = addedSecurityConfiguration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "api/admin/registerAdmin", "/v3/api-docs", "/v3/api-docs/***", "/swagger-resources/**",
                                "/swagger-ui/***", "/webjars/springfox-swagger-ui/**").permitAll()
                        .requestMatchers("/api/user/**").hasAuthority("USER")
                        .requestMatchers("/api/admin/**", "/api/profile/get_all_profile", "/api/profile/delete_profile").hasAuthority("ADMIN")
                        .requestMatchers("/api/profile/**").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated() // Protect all other endpoints
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
                )
                .authenticationProvider(addedSecurityConfiguration.authenticationProvider()) // Custom authentication provider
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }



}
