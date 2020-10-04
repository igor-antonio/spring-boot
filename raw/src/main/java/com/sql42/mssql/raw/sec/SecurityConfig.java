package com.sql42.mssql.raw.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/sec/*").authenticated()
		.and().formLogin()
		.defaultSuccessUrl("/sec/index");
	} 	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.ldapAuthentication()
	        .userDnPatterns("uid={0},ou=people")
	        .userSearchBase("ou=people")
	        .userSearchFilter("uid={0}")
	        .groupSearchBase("ou=groups")
	        .groupSearchFilter("uniqueMember={0}")
	        .contextSource()
	        .url("ldap://localhost:2389/dc=sql42,dc=com")
	        .and()
	        .passwordCompare()
	        .passwordEncoder(passwordEncoder())
	        .passwordAttribute("userPassword");		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
} 