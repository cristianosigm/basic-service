package com.cs.sigm.config;

import com.cs.sigm.entity.definition.CSUserRole;
import com.cs.sigm.service.CSUserDetailsService;
import com.cs.sigm.util.JwtAuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class CSSecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/api/v1/security/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/security/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/user/password/reset").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/user/password/code/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/utils/version").permitAll()

                .requestMatchers(HttpMethod.DELETE, "/api/v1/user/**").hasRole(CSUserRole.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/user").hasRole(CSUserRole.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/user").hasRole(CSUserRole.ADMINISTRATOR.name())

                .requestMatchers("/api/v1/company/**").hasRole(CSUserRole.ADMINISTRATOR.name())

                .anyRequest().authenticated())
            .logout(lo -> lo
                .logoutUrl("api/v1/security/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true))
            .authenticationProvider(authenticationProvider())
            .sessionManagement(sm -> sm
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(eh -> eh
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint()));
        // @formatter:on
        return http.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SimpleUrlLogoutSuccessHandler();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CSUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CSAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CSAuthenticationEntryPoint();
    }

}
