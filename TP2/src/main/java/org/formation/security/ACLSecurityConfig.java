package org.formation.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

//@Configuration
public class ACLSecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(ACLSecurityConfig.class);




	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**").antMatchers("/publics/**").antMatchers("/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.requestMatcher(new RegexRequestMatcher("^((?!api).)*$", null)).csrf().disable().authorizeRequests()
				.antMatchers("/fournisseurs*").hasRole("MANAGER").antMatchers("/produits*")
				.hasAnyRole("PRODUCT_MANAGER", "MANAGER")
				.antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs/**").permitAll()
				.antMatchers("/actuator/**").permitAll().antMatchers("/api/authenticate").permitAll().anyRequest()
				.authenticated().and().formLogin().and().logout().logoutUrl("/logout").invalidateHttpSession(true)
				.permitAll();

	}







}
