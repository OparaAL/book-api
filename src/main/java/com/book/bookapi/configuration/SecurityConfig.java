package com.book.bookapi.configuration;


import com.book.bookapi.configuration.jwt.JwtFilter;
import com.book.bookapi.exceptions.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
@Profile("dev")
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtFilter jwtFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .exceptionHandling()
                .defaultAuthenticationEntryPointFor(
                        authenticationEntryPoint(),
                        new AntPathRequestMatcher("/login/oauth2/code/google"))
                .and()
                    .authorizeRequests()
                    .antMatchers("/auth/sign-up").permitAll()
                    .antMatchers("/auth/sign-in").permitAll()
                    .antMatchers("/auth/refresh").permitAll()
                    .antMatchers("/auth/test").permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .oauth2Login()
                        .successHandler((httpServletRequest, httpServletResponse, authentication) -> {

                            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
                            httpServletRequest.setAttribute("oauthToken", oauth2Token);
                            httpServletRequest.setAttribute("auth", authentication);

                            httpServletRequest
                                    .getRequestDispatcher(String.format("/auth/login/oauth2/%s",oauth2Token.getAuthorizedClientRegistrationId()))
                                    .forward(httpServletRequest, httpServletResponse);
                        });


        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}