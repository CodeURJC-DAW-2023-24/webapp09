package es.codeurjc.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


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
					.requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
					.requestMatchers("/").permitAll()
					.requestMatchers("/sign-up").permitAll()
					.requestMatchers("/error").permitAll()
					.requestMatchers("/subject/**").permitAll()
					.requestMatchers("/subjectInfo").permitAll()
					.requestMatchers("/api/chart/**").permitAll()
					.requestMatchers("/user/photo").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
				
					//PRIVATE PAGES

					.requestMatchers("/subjects_registereduser").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
					.requestMatchers("/subjectUser").permitAll() 
					.requestMatchers("/profile").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
					.requestMatchers("/editProfile").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
					.requestMatchers("/moreSubjectsAdmin/**").hasAnyRole("ADMIN")
					.requestMatchers("/moreSubjectsMain/**").permitAll()
					.requestMatchers("/subject_onesubj_admin").hasAnyRole("ADMIN")
					.requestMatchers("/subject_onesubj_student/**").hasAnyRole("STUDENT", "TEACHER")
					.requestMatchers("/subject_onesubj_teacher").permitAll()
					.requestMatchers("/redirection/**").permitAll()
					.requestMatchers("/teachers/**").permitAll()
					.requestMatchers("/admins/**").permitAll()
					
			)
			.formLogin(formLogin -> formLogin
					.loginPage("/login")
					.usernameParameter("email")
					.failureUrl("/error")
					.defaultSuccessUrl("/")
					.permitAll()
			)
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login")
					.permitAll()
			);



        http.csrf(csrf -> csrf.ignoringRequestMatchers("/sendSolicitud"));

		return http.build();
		
	}

}
