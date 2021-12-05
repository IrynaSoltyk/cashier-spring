package com.cashier.springboot.config;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cashier.springboot.service.CustomUserDetailsServiceImpl;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsServiceImpl userDetailsService;
	
    protected void configure(HttpSecurity http) throws Exception {
		http		 	
		.csrf().disable()
		 	.authorizeRequests()
		 	.antMatchers("/shifts/*", "/shift/*", "/cp/cancel", "/cheques/cancel","/report").hasRole("MANAGER")
		 	.antMatchers("/products/all", "/products/search").hasAnyRole("COMMODITYEXPERT", "CASHIER")
            .antMatchers("/products/*").hasRole("COMMODITYEXPERT")
            .antMatchers("/cheques/*", "/cp/addproduct", "/cp/delete", "/cp/editproduct").hasRole("CASHIER")
            
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().accessDeniedPage("/403.jsp")
            .and()
			.formLogin().permitAll()
				.loginPage("/login").permitAll()
				.successHandler(cashierAuthenticationSuccessHandler())
				.failureUrl("/login?error") 
				.and()
			.logout()
				.permitAll()
                .invalidateHttpSession(true);

	}
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
      throws Exception {
    	auth.userDetailsService(userDetailsService)
    		.passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Bean
    public AuthenticationSuccessHandler cashierAuthenticationSuccessHandler(){
        return new CashierUrlAuthenticationSuccessHandler();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CashierAccessDeniedHandler();
    }

}
