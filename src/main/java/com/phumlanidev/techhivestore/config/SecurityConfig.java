package com.phumlanidev.techhivestore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p> comment </p>.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  public static final String ADMIN = "admin";
  public static final String USER = "user";
  private final JwtAuthConverter jwtAuthConverter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * <p> comment </p>.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/products/**").permitAll()
                    .requestMatchers("/api/v1/store/**").permitAll()
                    .requestMatchers("/api/v1/admin/**").hasRole(ADMIN)
                    .requestMatchers("/api/v1/user/**").hasRole(USER)
                    .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
  }
}
