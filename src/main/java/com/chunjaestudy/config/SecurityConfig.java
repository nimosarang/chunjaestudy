package com.chunjaestudy.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//            .requestMatchers("/favicon.ico")
//            .requestMatchers("/error")
//            .requestMatchers(toH2Console());
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .mvcMatchers("/node_modules/**")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
            .mvcMatchers("/","/login","sign-up","check-email","/check-email-token","email-login","check-email-login","/login-link")
            .permitAll()
            .mvcMatchers(HttpMethod.GET,"profile/*").permitAll()
            .anyRequest().authenticated()
//            .and()
//            .formLogin().loginPage("/login").permitAll()
//            .and()
//            .logout().logoutSuccessUrl("/")
//            .and()
//            .rememberMe().userDetailsService(accountService).tokenRepository(tokenRepository())
            .and().build();
    }

//    @Bean
//    public PasswordEncoder scryptPasswordEncoder() {
//        return new SCryptPasswordEncoder(
//            16,
//            8,
//            1,
//            32,
//            64);
//    }
}
