package org.generation.italy.pizza.demo.security;

import org.generation.italy.pizza.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

//indichiamo questa classe come coonfiguaratore di rotte
@Configuration
public class SecurityConf {
	
	//indichiamo in base al role a quali rotte posso accedere 
	@Bean
	public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeHttpRequests()	
				//indichiamo in base al path il role che potra accedere
				.requestMatchers("/user", "/user/**").hasAuthority("user")
				.requestMatchers("/admin", "/admin/**").hasAuthority("admin")
				.requestMatchers("/useradmin", "/useradmin/**").hasAnyAuthority("USER", "ADMIN")	
				.requestMatchers("/**").permitAll()
			.and().formLogin()
			.and().logout()
		;

		return http.build();
	}
	
	@Bean
	public UserDetailsService getuseDetailsService() {
		
		return new UserService();
	}
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(getuseDetailsService());
		provider.setPasswordEncoder(getPasswordEncoder());
		
		return provider;
	}
	
	// Bean per Integrazione con Thymeleaf
	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}

}
