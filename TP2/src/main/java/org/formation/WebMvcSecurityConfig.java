package org.formation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class WebMvcSecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(WebMvcSecurityConfig.class);

	@Autowired
	UserDetailsService userDetailService;

	


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		super.configure(http);
		
		http.sessionManagement().maximumSessions(2).and().and().logout().logoutUrl("/logout")                   
		  .logoutSuccessUrl("/")             
		  .invalidateHttpSession(true);
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("Configuring DetailsService");
		auth.userDetailsService(userDetailService);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	@Bean
	public SessionRegistry sessionRegistry() {
	    return new SessionRegistryImpl();
	}
}
