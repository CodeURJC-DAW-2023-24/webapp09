package es.codeurjc.helloworldvscode.security;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.codeurjc.helloworldvscode.Entitys.User;
import es.codeurjc.helloworldvscode.enumerate.Role;
import es.codeurjc.helloworldvscode.repository.UserRepository;

@Service
public class RepositoryUserDetailService implements UserDetailsService{
	
    @Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		System.out.println("ESTE ES EL EMAIL"+email);

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		roles.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
		roles.add(new SimpleGrantedAuthority("ROLE_TEACHER"));

		return new org.springframework.security.core.userdetails.User(user.getEmail(), 
				user.getPassword(), roles);
	}
    
}
