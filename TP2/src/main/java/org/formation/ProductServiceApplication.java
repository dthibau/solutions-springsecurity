package org.formation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.FilterChainProxy;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ProductServiceApplication.class, args);
		
		FilterChainProxy filterChain = (FilterChainProxy) context.getBean("springSecurityFilterChain");
		
		filterChain.getFilterChains().forEach(System.out::println);

	}

}
