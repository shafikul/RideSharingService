package com.sagar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DriverPrincipalDetailsService driverPrincipalDetailsService;

    public SecurityConfig(DriverPrincipalDetailsService driverPrincipalDetailsService){
        this.driverPrincipalDetailsService = driverPrincipalDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/v1/p/**").permitAll()
                .antMatchers("/v1/a/**").hasRole("ADMIN")
                .antMatchers("/v1/d/**").hasAnyRole("DRIVER", "ADMIN")
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public AuthenticationProvider authProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.driverPrincipalDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }
}
