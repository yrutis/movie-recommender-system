package ch.uzh.ifi.seal.ase.mrs.memberservice.config;

import ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security Configurations. Requires Basic Authentication on all endpoints except api/signup/**
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailService;

    /**
     * Constructor, autowires the user details service
     *
     * @param userDetailService Service that provides information about the users
     */
    @Autowired
    public SecurityConfig(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    /**
     * Requires Basic Authentication on all endpoints except api/signup/**
     * Disables CSRF
     *
     * @param http HttpSecurity
     * @throws Exception Exception on failed auth
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests().antMatchers("/api/signup/**").permitAll().antMatchers("/api/login/**").permitAll().anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    /**
     * Set the AuthenticationManagerBuilder
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception Exception on wrongly configured AuthenticationManagerBuilder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    /**
     * Create a password encoder
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}