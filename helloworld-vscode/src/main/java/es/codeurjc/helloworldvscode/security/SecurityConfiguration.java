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

					// STATIC RESOURCES
					.requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
					
					// PUBLIC PAGES
					.requestMatchers("/").permitAll()
					.requestMatchers("/sign-up").permitAll()
					.requestMatchers("/login").permitAll()
					.requestMatchers("/error").permitAll()
					.requestMatchers("/loginerror").permitAll()
					.requestMatchers("/subject/**").permitAll()
					.requestMatchers("/subjectInfo").permitAll()
					//.requestMatchers("/subjects_registereduser").permitAll()

					//PRIVATE PAGES

					.requestMatchers("/subjects_registereduser/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN") 
					.requestMatchers("/logout").hasAnyRole("STUDENT", "TEACHER", "ADMIN") 
					.requestMatchers("/profile").hasAnyRole("STUDENT", "TEACHER")
					.requestMatchers("/editProfile").hasAnyRole("STUDENT", "TEACHER")
					.requestMatchers("/subject_onesubj_admin").hasAnyRole("ADMIN")
					.requestMatchers("/subject_onesubj_student").hasAnyRole("STUDENT")
					.requestMatchers("/subject_onesubj_teacher").hasAnyRole("STUDENT")
					
			)
			.formLogin(formLogin -> formLogin
					.loginPage("/login")
					.failureUrl("/loginerror")
					.defaultSuccessUrl("/")
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
