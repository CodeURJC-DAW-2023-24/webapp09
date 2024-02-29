package es.codeurjc.helloworldvscode.security;

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

					//PRIVATE PAGES
					.requestMatchers("/subjects_registereduser").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
					.requestMatchers("/subjectUser").hasAnyRole("STUDENT", "TEACHER", "ADMIN") 
					.requestMatchers("/logout").hasAnyRole("STUDENT", "TEACHER", "ADMIN") 
					.requestMatchers("/profile").hasAnyRole("STUDENT", "TEACHER")
					.requestMatchers("/editProfile").hasAnyRole("STUDENT", "TEACHER")
					.requestMatchers("/subject_onesubj_admin").hasAnyRole("ADMIN")
					.requestMatchers("/subject_onesubj_student").hasAnyRole("STUDENT")
					.requestMatchers("/subject_onesubj_teacher").hasAnyRole("STUDENT")


					//TEACHER 
					.requestMatchers("/teachers/subject/**/general-information").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/general-information/delete").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/add-teacher").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/add-student").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/type-exams").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/type-exams/download").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/type-exams/delete").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/add-exam").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/type-exams/**/correct-exams").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/type-exams/**/download").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/marks").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/forum").hasAnyRole("TEACHER")
					.requestMatchers("/teachers/subject/**/forum/delete").hasAnyRole("TEACHER")


					//ADMIN 
					.requestMatchers("/admins/subject/**/general-information").hasAnyRole("ADMIN")
					.requestMatchers("/admins/subject/**/general-information/delete").hasAnyRole("ADMIN")
					.requestMatchers("/admins/subject/**/add-teacher").hasAnyRole("ADMIN")
					.requestMatchers("/admins/subject/**/add-student").hasAnyRole("ADMIN")
					
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
