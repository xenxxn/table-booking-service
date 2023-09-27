package com.xenxxn.tablebooking.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenxxn.tablebooking.global.login.filter.JsonMemberEmailPasswordAuthenticationFilter;
import com.xenxxn.tablebooking.global.login.handler.LoginFailureHandler;
import com.xenxxn.tablebooking.global.login.handler.LoginSuccessJWTProvideHandler;
import com.xenxxn.tablebooking.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final LoginService loginService;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/register","/").permitAll()
                .anyRequest().authenticated();

        http.addFilterAfter(jsonMemberEmailPasswordLoginFilter(), LogoutFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler(){
        return new LoginSuccessJWTProvideHandler();
    }

    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

    @Bean
    public JsonMemberEmailPasswordAuthenticationFilter jsonMemberEmailPasswordLoginFilter() {
        JsonMemberEmailPasswordAuthenticationFilter jsonMemberEmailPasswordLoginFilter
                = new JsonMemberEmailPasswordAuthenticationFilter(objectMapper);
        jsonMemberEmailPasswordLoginFilter.setAuthenticationManager(authenticationManager());
        jsonMemberEmailPasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
        jsonMemberEmailPasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return jsonMemberEmailPasswordLoginFilter;
    }

}
