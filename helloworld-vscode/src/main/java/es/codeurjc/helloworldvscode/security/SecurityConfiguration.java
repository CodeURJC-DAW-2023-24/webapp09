package es.codeurjc.helloworldvscode.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.*;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
    public RepositoryUserDetailService userDetailService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
     public DaoAuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
         return authProvider;
     }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authenticationProvider(authenticationProvider());
		
		http
			.authorizeHttpRequests(authorize -> authorize
					// PUBLIC PAGES
					
					.requestMatchers("/").permitAll()
					.requestMatchers("/sign-up").permitAll()
					.requestMatchers("/css/**").permitAll()
					.requestMatchers("/error").permitAll()
					.requestMatchers("/images/**").permitAll()
					.requestMatchers("/js/**").permitAll()
					.requestMatchers("/subject/**").permitAll()
					.requestMatchers("/subjectInfo").permitAll()

					
			)
			.formLogin(formLogin -> formLogin
					.loginPage("/login")
					.failureUrl("/error")
					.defaultSuccessUrl("/subjects_registereduser")
					.permitAll()
			)
			.logout(logout -> logout
					.logoutUrl("/")
					.logoutSuccessUrl("/")
					.permitAll()
			);



        http.csrf(csrf -> csrf.ignoringRequestMatchers("/sendSolicitud"));

		return http.build();
		
	}

}
