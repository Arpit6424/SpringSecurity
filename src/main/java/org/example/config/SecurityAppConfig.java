package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityAppConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    HttpSecurity httpSecurity;
    @Bean
    public InMemoryUserDetailsManager setUpUser(){
        UserDetails user1 = User.withUsername("user1")
                                .password( passwordEncoder.encode("password1") )
                                .roles("admin","user","visitor")
                                .build();
        UserDetails user2 = User.withUsername("user2")
                                .password(passwordEncoder.encode("password2") )
                                .roles("user","visitor")
                                .build();
        return new  InMemoryUserDetailsManager(user1,user2);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }


    @Bean
    SecurityFilterChain settingUpSecurity() throws Exception {

//        httpSecurity.authorizeHttpRequests().anyRequest().permitAll();

        httpSecurity.authorizeHttpRequests().requestMatchers("/hi").authenticated();
        httpSecurity.authorizeHttpRequests().requestMatchers("/bye").denyAll();
        httpSecurity.authorizeHttpRequests().requestMatchers("/hello").permitAll();

        httpSecurity.formLogin();
        httpSecurity.httpBasic();

        return httpSecurity.build();
    }

    //mvcHandlerMappingIntrospector
//    @Bean
//    HandlerMappingIntrospector handlerMappingIntrospector(){
//        return new HandlerMappingIntrospector();
//    }


}
