package com.chunjaestudy.config;

import com.chunjaestudy.account.AccountService;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AccountService accountService;
    private final DataSource dataSource;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .mvcMatchers("/node_modules/**")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
            .mvcMatchers("/","/login","sign-up","/check-email-token","email-login","check-email-login","/login-link")
            .permitAll()
            .mvcMatchers(HttpMethod.GET,"profile/*").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .and()
            .logout().logoutSuccessUrl("/")
            .and()
            .rememberMe()
            .userDetailsService(accountService)
            .tokenRepository(tokenRepository())
            .and().build();
    }

    private PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}
