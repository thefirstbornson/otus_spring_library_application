package ru.otus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfiguration(AuthenticationService authenticationService, AccessDeniedHandler accessDeniedHandler) {
        this.authenticationService = authenticationService;
        this.accessDeniedHandler = accessDeniedHandler;
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/books", "/authors","/genres","/bookcomments").hasAnyRole("VIEWER")
                    .antMatchers("/editbook","/savebook","/removebook"
                            ,"/editauthor","/saveauthor","/removeauthor"
                            ,"/editgenre","/savegenre","/removegenre"
                            ,"/editbookcomment","/savebookcomment","/removebookcomment").hasAnyRole("ADMIN")
                    .antMatchers("/editbook","/editauthor","/editgenre","/editbookcomment"
                            ,"/savebook","/saveauthor","/savegenre", "/savebookcomment").hasAnyRole("EDITOR")

                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .permitAll()
                .and()
                .logout()
                    .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());

    }
}
