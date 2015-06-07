package de.learny.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import de.learny.security.service.AccountToUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccountToUserDetailsService userDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

	@Autowired
	private RestLogoutSuccessHandler restLogoutSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);

	}

	@Bean
	public RestUsernamePasswordAuthenticationFilter restFilter() throws Exception {
		RestUsernamePasswordAuthenticationFilter myFilter = new RestUsernamePasswordAuthenticationFilter();
	    myFilter.setAuthenticationManager(authenticationManager());

	    return myFilter;
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(restFilter(),
				UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/accounts").permitAll()
			.antMatchers("/api/**", "/login").fullyAuthenticated();
		http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
		http.formLogin().loginProcessingUrl("/login")
				.successHandler(restAuthenticationSuccessHandler);

		http.csrf().disable();
		http.logout().logoutUrl("/logout").deleteCookies("JSESSIONID")
				.logoutSuccessHandler(restLogoutSuccessHandler);
	}
}