package com.j2digital.team5.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.j2digital.team5.security.jwt.JwtEntryPoint;
import com.j2digital.team5.security.jwt.JwtFilter;
import com.j2digital.team5.security.service.UserDetailServiceImpl;


@Configuration
@EnableWebSecurity
public class MainSecurityConfig {

	@Autowired
	UserDetailServiceImpl userDetailService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtEntryPoint jwtEntryPoint;

	@Autowired
	JwtFilter jwtFilter;

	AuthenticationManager authenticationManager;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
		authenticationManager = builder.build();
		http.authenticationManager(authenticationManager);
		http.csrf().disable().cors().disable();
		http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated();
	//	http.authorizeRequests().antMatchers("/auth/**").permitAll().anyRequest().authenticated();
		http.exceptionHandling().authenticationEntryPoint(jwtEntryPoint);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
