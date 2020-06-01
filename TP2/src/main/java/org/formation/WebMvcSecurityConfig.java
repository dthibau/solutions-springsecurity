package org.formation;

import org.formation.jwt.JWTFilter;
import org.formation.jwt.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
public class WebMvcSecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(WebMvcSecurityConfig.class);

	@Autowired
	UserDetailsService userDetailService;



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

	@Order(1)
	@Configuration
	public static class RestConfiguration extends WebSecurityConfigurerAdapter {
		
		@Autowired
		TokenProvider tokenProvider;
		
		@Override
        protected void configure(HttpSecurity http) throws Exception {
            http
            .antMatcher("/api/**")
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .addFilterBefore(new JWTFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
          .csrf()
          .disable();
        }

		@Override
		public void configure(WebSecurity web) throws Exception {
			super.configure(web);
		}
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("Configuring DetailsService");
		auth.userDetailsService(userDetailService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
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
